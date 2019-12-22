import controller.CarController;
import fi.iki.elonen.NanoHTTPD;

import static fi.iki.elonen.NanoHTTPD.Method.GET;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;


public class RequestUrlMapper {

//    private final static String ADD_AUTO_URL = "/auto/add";
    private final static String GET_SERVICE_URL = "/budget_of_car/getService";
    private final static String GET_ALL_SERVICE_URL = "/budget_of_car/getAllService";
//    private final static String REMOVE_AUTO_URL = "/auto/remove";


    private CarController carController = new CarController();

    public NanoHTTPD.Response delegateRequest(NanoHTTPD.IHTTPSession session) {

        if (GET.equals(session.getMethod()) && GET_ALL_SERVICE_URL.equals(session.getUri())) {

            return carController.serveGetAllServicesRequest(session);
        }else if (GET.equals(session.getMethod()) && GET_SERVICE_URL.equals(session.getUri())) {

                return carController.serveGetServiceRequest(session);

        }
        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not Found");

    }
}
