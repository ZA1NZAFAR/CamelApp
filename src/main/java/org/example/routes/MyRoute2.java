package org.example.routes;

import org.apache.camel.builder.RouteBuilder;

public class MyRoute2 extends ParrotRouteBuilder {
    int id;

    public MyRoute2() {
        id = (int) (Math.random() * ((10000 - 5) + 1)) + 5;
    }

    public void configure() {
        from("timer://simpleTimerR2?period=3000")
                .routeId(MyRoute2.class.getName())
                .setHeader("id", constant(id))
                .log("${header.id}")
                .process(exchange -> {
                    for (int j = 0; j < Integer.MAX_VALUE; j++) {
                        if (j % 1000000000 == 0)
                            System.out.println(id + " Working....");
                    }
                    System.out.println("Route " + id + " loop finished.");
                });
    }

}
