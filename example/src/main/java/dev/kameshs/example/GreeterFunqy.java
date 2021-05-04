package dev.kameshs.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Incoming;

public class GreeterFunqy {

    private Logger log = Logger.getLogger("dev.kameshs.example.greeter");

    @Incoming("func-sink")
    public void greeter(String data) {
        log.log(Level.INFO, "Processing Data:{0}", data);
    }
}
