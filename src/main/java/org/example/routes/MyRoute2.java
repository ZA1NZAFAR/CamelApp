package org.example.routes;

public class MyRoute2 extends ParrotRouteBuilder {

    public void configure() {
        from("timer://simpleTimerR2?period=3000")
                .routeId(MyRoute2.class.getName())
                .process(exchange -> {
                    for (int j = 0; j < Integer.MAX_VALUE; j++) {
                        if (j % 1000000000 == 0)
                            System.out.println("MyRoute2 Working....");
                    }
                    System.out.println("MyRoute2 loop finished.");
                });
    }

}
