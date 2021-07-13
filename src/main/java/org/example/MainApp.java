package org.example;

import org.apache.camel.Main;
import org.apache.camel.Route;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.routes.ManagerRoute;
import org.example.routes.ParrotRouteBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


public class MainApp {



    static Main main;

    public static void main(String[] args) throws Exception {
        main = new Main();
        main.addRouteBuilder(new ManagerRoute());
        main.run();
    }

    public static List<String> getRunningRoute() {
        return main.getCamelContexts().get(0).getRoutes()
                .stream()
                .map(entry -> entry.getId())
                .filter(route -> !route.equals(ManagerRoute.class.getName()))
                .sorted()
                .collect(Collectors.toList());
    }

    public static void manageRoute(List<String> activeRoute) throws Exception {

        System.out.println("=============== In manageRoute ===============");

        //activation route
        for (String activeRouteName : activeRoute) {
            if (main.getCamelContexts().get(0).getRoute(activeRouteName) == null) {
                System.out.println("add " + activeRouteName);
                main.getCamelContexts().get(0).addRoutes((ParrotRouteBuilder) Class.forName(activeRouteName).newInstance());
            }
        }

        //desactivation route
        for (String runningRoute : getRunningRoute()) {
            if (!activeRoute.contains(runningRoute) && main.getCamelContexts().get(0).getRoute(runningRoute) != null) {
                System.out.println("remove " + runningRoute);
                main.getCamelContexts().get(0).stopRoute(runningRoute);
                main.getCamelContexts().get(0).removeRoute(runningRoute);
            }
        }
    }
}

