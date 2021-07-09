package org.example.routes;

import org.apache.camel.builder.RouteBuilder;
import org.example.MainApp;

public class DuplicatorRoute extends ParrotRouteBuilder {
    int count;

    public DuplicatorRoute(int count) {
        this.count = count;
    }

    @Override
    public void configure() throws Exception {
        //if (count < 4)
            from("timer://simpleTimerRD?period=5000&delay=10000")
                    //from("quartz2://myCron?cron=0+0/1+*+1/1+*+?+*")
                    .log("===================================================================================Going to restart!")
                    .process(exchange -> MainApp.startMain());
    }
}
