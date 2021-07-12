package org.example.routes;

import org.example.MainApp;

public class ManagerRoute extends ParrotRouteBuilder {


    @Override
    public void configure() throws Exception {
            from("timer://simpleTimerRD?period=5000&delay=5000")
                    .routeId(ManagerRoute.class.getName())
                    //from("quartz2://myCron?cron=0+0/1+*+1/1+*+?+*")
                    //.log("===================================================================================Going to restart!")
                    .process(exchange -> MainApp.manageRoute());
    }
}
