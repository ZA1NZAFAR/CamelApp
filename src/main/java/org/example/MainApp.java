package org.example;

import org.apache.camel.Main;
import org.apache.camel.Route;
import org.example.routes.DuplicatorRoute;
import org.example.routes.MyRoute1;
import org.example.routes.MyRoute2;

import java.util.List;


public class MainApp {
    static Main main;
    static int count = 0;


    public static void main(String[] args) throws Exception {
        startMain();

    }


    public static void startMain() throws Exception {
        System.out.println("In startMain");
        count++;
        if (null != main && main.isStarted()) {
            for (int i = 0; i < main.getRouteBuilders().size(); i++) {
                List<Route> routes = main.getCamelContexts().get(0).getRoutes();
                System.out.println("dsadasd");

                List<String> compNames = main.getCamelContexts().get(0).getComponentNames();
                System.out.println("compNames");
            }
        }
        main = new Main();
        main.addRouteBuilder(new MyRoute1());
        main.addRouteBuilder(new MyRoute2(count));
        main.addRouteBuilder(new DuplicatorRoute(count));
        main.run();
    }


}

