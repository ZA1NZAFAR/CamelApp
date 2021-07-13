package org.example.routes;

public class MyRoute1 extends ParrotRouteBuilder {
    public void configure() {
        from("timer://simpleTimerR1?period=3000")
                .routeId(MyRoute1.class.getName())
                .log("MyRoute1");
    }
}
