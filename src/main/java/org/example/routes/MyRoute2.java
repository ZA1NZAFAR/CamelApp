package org.example.routes;

import org.apache.camel.builder.RouteBuilder;

public class MyRoute2 extends ParrotRouteBuilder {
    int id;
    int count;

    public MyRoute2(int count) {
        id = (int) (Math.random() * ((10000 - 5) + 1)) + 5;
        this.count = count;
    }

    public void configure() {

        //if (count < 4)
            from("timer://simpleTimerR2?period=3000")
                    .setHeader("id", constant(id))
                    .log("${header.id}");
//                    .process(exchange -> {
//                        for (int j = 0; j < Integer.MAX_VALUE; j++) {
//                            if (j % 1000000000 == 0)
//                                System.out.println(id + " Working....");
//                        }
//                        System.out.println("Route " + id + " loop finished.");
//                    });
    }

}
