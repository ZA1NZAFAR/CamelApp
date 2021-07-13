package org.example.routes;

import org.apache.commons.dbcp2.BasicDataSource;
import org.example.MainApp;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class ManagerRoute extends ParrotRouteBuilder {

    private static String USER = "???";
    private static String PASSWORD = "??";
    private static String CATALOG = "???";
    private static String SERVER = "???";

    @Override
    public void configure() {
        from("timer://simpleTimerRD?period=5000")
                //from("quartz2://myCron?cron=0+0/1+*+1/1+*+?+*")
                .routeId(ManagerRoute.class.getName())
                .process(exchange -> {
                    List<String> activeRoutes = retrieveRoute(true);

                    if (!MainApp.getRunningRoute().equals(activeRoutes)) {
                        MainApp.manageRoute(activeRoutes);
                    }
                });
    }


    public static List<String> retrieveRoute(Boolean active) {

        try (Connection connection = setupDataSource().getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * from middleware_route_activation where Active = " + (active ? 1 : 0) + " order by RouteName");
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
