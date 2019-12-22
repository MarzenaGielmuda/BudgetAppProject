package controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import storage.CarPostgresStorageImpl;
import storage.CarStorage;
import type.Service;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;
@Controller
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


    public NanoHTTPD.Response serveAddServiceRequest(NanoHTTPD.IHTTPSession session){
        ObjectMapper objectMapper = new ObjectMapper();
//        long randomBookId = System.currentTimeMillis();
        String lengthHeader = session.getHeaders().get("content-length");
        int contentLength = Integer.parseInt(lengthHeader);
        byte[] buffer = new byte[contentLength];
        Service requestService;
        try {
            session.getInputStream().read(buffer, 0, contentLength);
            String requestBody = new String(buffer).trim();
            requestService = objectMapper.readValue(requestBody, Service.class);
//            requestService.setId(randomBookId);
            List<Long> integerList = new ArrayList<Long>();
            carStorage.getAllService().forEach( x -> integerList.add( x.getId() ) );
            if(integerList.contains( requestService.getId() )){
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Service with selected id already exists");
            }
            carStorage.addService(requestService);
        } catch (IOException e) {
            System.err.println("Error during process request: \n" + e);
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error service hasn't been added");
        }
//        return newFixedLengthResponse(OK, "text/plain", "Book has been successfully added, id=" + randomBookId);
        return newFixedLengthResponse(OK, "text/plain", "Service has been successfully added, id=" + requestService.getId());
    }



    public ModelAndView serveAddServiceRequest(Service service){
        carStorage.addService(service);
        return null;
    }

    public NanoHTTPD.Response serveRemoveServiceRequest(NanoHTTPD.IHTTPSession session) {

        Map<String, List<String>> requestParameters = session.getParameters();
        if (requestParameters.containsKey(SERVICE_ID_PARAM_NAME)) {
            List<String> serviceIdParams = requestParameters.get(SERVICE_ID_PARAM_NAME); //pobieramy listę z podanego klcza i wrzucamy do nowej listy
            String serviceIdParam = serviceIdParams.get(0);//bieżemy tylko pierwszy element z listy
            long serviceId = 0;

            try {
                serviceId = Long.parseLong(serviceIdParam);
            } catch (NumberFormatException nfe) {
                System.err.println("Error during convert request param: \n" + nfe);
                return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Request param 'Id' have to be a number");
            }

            List<Long> integerList1 = new ArrayList<>(); //1 sposób sprawdzenia
            carStorage.getAllService().forEach(x -> integerList1.add(x.getId()));

            List<Long> integerList = new ArrayList<>();//2 sposób sprawdzenia
            for (Service service: carStorage.getAllService()){
                integerList.add(service.getId());
            }

            if(integerList1.contains(serviceId)){
                carStorage.removeService(serviceId);
                return newFixedLengthResponse(OK, "text/plain", "Service has been successfully deleted, id=" + serviceId );
            }
            return newFixedLengthResponse(NOT_FOUND, "application/json", "");//gdy nie ma ksiązki o takim ID
        }
        return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Unrecorded request params");
    }

    }



