package org.example.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class DisplayIdProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("---" + exchange.getIn().getHeader("id"));
    }
}
