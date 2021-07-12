package org.example.routes;

import org.apache.camel.builder.RouteBuilder;

public class MyRoute1 extends ParrotRouteBuilder {
    int id;

    public MyRoute1() {
        id = (int) (Math.random() * ((10000 - 5) + 1)) + 5;
    }

    public void configure() {
        from("timer://simpleTimerR1?period=3000")
                .routeId(MyRoute1.class.getName())
                .setHeader("id", constant(id))
                .log("${header.id}");
    }

}
