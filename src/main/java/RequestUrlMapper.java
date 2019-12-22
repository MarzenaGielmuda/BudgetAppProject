import controller.CarController;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.Method.GET;
import static fi.iki.elonen.NanoHTTPD.Method.POST;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;
import static fi.iki.elonen.NanoHTTPD.Method.DELETE;


public class RequestUrlMapper {

    private final static String ADD_SERVICE_URL = "/budget_of_car/addService";
    private final static String GET_SERVICE_URL = "/budget_of_car/getService";
    private final static String GET_ALL_SERVICE_URL = "/budget_of_car/getAllService";
    private final static String REMOVE_SERVICE_URL = "/budget_of_car/removeService";


    private CarController carController = new CarController();

    public NanoHTTPD.Response delegateRequest(NanoHTTPD.IHTTPSession session) {

        if (GET.equals(session.getMethod()) && GET_ALL_SERVICE_URL.equals(session.getUri())) {

            return carController.serveGetAllServicesRequest(session);
        }else if (GET.equals(session.getMethod()) && GET_SERVICE_URL.equals(session.getUri())) {

                return carController.serveGetServiceRequest(session);

        }else if (POST.equals(session.getMethod()) && ADD_SERVICE_URL.equals(session.getUri())) {

            return carController.serveAddServiceRequest(session);

        }else if(DELETE.equals(session.getMethod()) && REMOVE_SERVICE_URL.equals(session.getUri())){
        System.out.println();
        return carController.serveRemoveServiceRequest(session);

        }
        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not Found");

    }
}
