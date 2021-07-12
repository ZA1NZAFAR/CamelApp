package org.example;

import org.apache.camel.Main;
import org.apache.commons.dbcp2.BasicDataSource;
import org.example.routes.ManagerRoute;
import org.example.routes.MyRoute1;
import org.example.routes.MyRoute2;
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

    private static String USER = "???";
    private static String PASSWORD = "???";
    private static String CATALOG = "???";
    private static String SERVER = "???";

    static Main main;

    public static void main(String[] args) throws Exception {
        main = new Main();
        main.addRouteBuilder(new ManagerRoute());
        main.run();
    }

    public static void manageRoute() throws Exception {
        System.out.println("=============== In manageRoute ===============");

        for (String routeName : retrieveRoute(true)) {
            if (main.getCamelContexts().get(0).getRoute(routeName) == null) {
                System.out.println("add " + routeName);
                main.getCamelContexts().get(0).addRoutes((ParrotRouteBuilder) instanceClass(routeName));
            }
        }

        for (String routeName : retrieveRoute(false)) {
            if (main.getCamelContexts().get(0).getRoute(routeName) != null) {
                System.out.println("remove " + routeName);
                main.getCamelContexts().get(0).stopRoute(routeName);
                main.getCamelContexts().get(0).removeRoute(routeName);
            }
        }
    }

    public static Object instanceClass(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class c= Class.forName(className);
        return c.newInstance();
    }

    public static List<String> retrieveRoute(Boolean active) throws Exception {

        try (Connection connection = setupDataSource().getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from middleware_route_activation where Active = " + (active ? 1 : 0));
            return convertResultSetToList(resultSet).stream()
                    .map(entry -> entry.get("RouteName").toString())
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataSource setupDataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        bds.setUsername(USER);
        bds.setPassword(PASSWORD);
        bds.setDefaultCatalog(CATALOG);
        String url = String.format("jdbc:sqlserver://%s:1433;databaseName=%s;encrypt=%s;trustServerCertificate=%s;", SERVER, CATALOG, true, true);
        bds.setUrl(url);
        return bds;
    }

    public static List<Map> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map> list = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }
}

