import fi.iki.elonen.NanoHTTPD;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

//public class BudgetApp extends NanoHTTPD {
//
//    RequestUrlMapper requestUrlMapper = new RequestUrlMapper();
//
//    public BudgetApp (int port) throws IOException {
//
//        super(port);
//        start(5000, false);
//        System.out.println("Server has been started.");
//    }
//
//    public static void main(String[] args) {
//        try{
//            new BudgetApp(8085);
//        }catch (IOException e){
//            System.out.println("Server can't started becouse of error: \n" + e);
//        }
//    }

//    @Override
//    public Response serve (IHTTPSession session){
//        System.out.println();
//        return requestUrlMapper.delegateRequest(session);
//    }

    @SpringBootApplication
    public class BudgetApp {

        public static void main(String[] args) {
            SpringApplication.run(BudgetApp.class, args);
        }
    }




