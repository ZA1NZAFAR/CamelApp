//package org.example.RouteManager;
//
//import org.apache.camel.main.Main;
//import org.example.routes.DuplicatorRoute;
//import org.example.routes.MyRoute1;
//import org.example.routes.MyRoute2;
//
//public class RouteManager extends Main {
//
//    public static void startMain() throws Exception {
//        main = new Main();
//        main.configure().addRoutesBuilder(new MyRoute1());
//        main.configure().addRoutesBuilder(new MyRoute2());
//        main.configure().addRoutesBuilder(new DuplicatorRoute());
//        main.run();
//    }
//
//
//    public static void killCurrent() {
//        System.out.println("Killing zone.");
//        main = null;
//    }
//
//}
