package org.example.routes;

import org.apache.camel.builder.RouteBuilder;

public abstract class ParrotRouteBuilder extends RouteBuilder {
    private static String children = "";

    public ParrotRouteBuilder() {
        super();
        synchronized (this) {
            children = this.getClass().getName();
        }
    }

}