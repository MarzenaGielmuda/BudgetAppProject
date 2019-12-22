import controller.CarController;
import fi.iki.elonen.NanoHTTPD;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import type.Service;

import static fi.iki.elonen.NanoHTTPD.Method.GET;
import static fi.iki.elonen.NanoHTTPD.Method.POST;
import static fi.iki.elonen.NanoHTTPD.Response.Status.NOT_FOUND;
import static fi.iki.elonen.NanoHTTPD.Method.DELETE;


public class RequestUrlMapper {

    private final static String ADD_SERVICE_URL = "/budget_of_car/addService";
    private final static String GET_SERVICE_URL = "/budget_of_car/getService";
    private final static String GET_ALL_SERVICE_URL = "/budget_of_car/getAllService";
    private final static String REMOVE_SERVICE_URL = "/budget_of_car/removeService";
    private final static String START_SERVICE_URL = "/";



    private CarController carController = new CarController();

    @RequestMapping("/")
    public String indexGet() {
        return "budget/start";
    }



    @RequestMapping(value ="/budget_of_car/addService", method = RequestMethod.POST)
    public ModelAndView saveService(@ModelAttribute(value = "serv") Service service) {

        return carController.serveAddServiceRequest(service);
    }



//    public NanoHTTPD.Response delegateRequest(NanoHTTPD.IHTTPSession session) {
//
//        if (GET.equals(session.getMethod()) && GET_ALL_SERVICE_URL.equals(session.getUri())) {
//
//            return carController.serveGetAllServicesRequest(session);
//        }else if (GET.equals(session.getMethod()) && GET_SERVICE_URL.equals(session.getUri())) {
//
//                return carController.serveGetServiceRequest(session);
//
//        }else if (POST.equals(session.getMethod()) && ADD_SERVICE_URL.equals(session.getUri())) {
//
//            return carController.serveAddServiceRequest(session);
//
//        }else if(DELETE.equals(session.getMethod()) && REMOVE_SERVICE_URL.equals(session.getUri())){
//        System.out.println();
//        return carController.serveRemoveServiceRequest(session);
//
//        }
//        return NanoHTTPD.newFixedLengthResponse(NOT_FOUND, "text/plain", "Not Found");
//
//    }
}
