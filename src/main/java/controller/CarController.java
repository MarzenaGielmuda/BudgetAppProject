package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import storage.CarPostgresStorageImpl;
import storage.CarStorage;
import type.Service;

import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class CarController {

    private CarStorage carStorage = new CarPostgresStorageImpl();

    private final static  String SERVICE_ID_PARAM_NAME= "id";

    public NanoHTTPD.Response serveGetAllServicesRequest(NanoHTTPD.IHTTPSession session) {

        ObjectMapper objectMapper = new ObjectMapper();
        String response = "";

        try {
            response = objectMapper.writeValueAsString(carStorage.getAllService());
        }catch (JsonProcessingException e ){
            System.err.println("Error during process request:\n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR,"text/plain", "Internal error can't read all car's payments");
        }
        System.out.println(response);

        return newFixedLengthResponse(OK, "application/json", response);
    }


    public NanoHTTPD.Response serveGetServiceRequest(NanoHTTPD.IHTTPSession session) {

        Map<String, List<String>> requestParameters = session.getParameters();

        if (requestParameters.containsKey(SERVICE_ID_PARAM_NAME)) {
            List<String> serviceIdParams = requestParameters.get(SERVICE_ID_PARAM_NAME); //pobieramy listę z podanego klcza i wrzucamy do nowej listy
            String serviceIdParam = serviceIdParams.get(0);//bieżemy tylko pierwszy element z listy
            long serviceId = 0;


            try {
                serviceId = Long.parseLong(serviceIdParam);
            } catch (NumberFormatException nfe) {
                System.err.println("Error during convert request param: \n" + nfe);
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Request param 'bookId' have to be a number");
            }


            Service service = carStorage.getService(serviceId);


            if (service != null) {// dla książki która istnieje z takim id

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    String response = objectMapper.writeValueAsString(service);
                    return newFixedLengthResponse(OK, "application/json", response);

                } catch (JsonProcessingException e) {
                    System.err.println("Error during process request: \n " + e);
                    return newFixedLengthResponse(INTERNAL_ERROR, "text/plane", "Internal error can't read all service");//błąd po stronie serwera

                }
            }
            return newFixedLengthResponse(NOT_FOUND, "application/json", "");//gdy nie ma aktora o takim ID
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Unrecorded request params");
    }
    }



