package dev.kameshs.azure.functions.kafka.runtime;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import com.microsoft.azure.functions.ExecutionContext;

import io.smallrye.reactive.messaging.annotations.Broadcast;

@Singleton
public class KafkaTriggerHandler {

    private static final Logger log = Logger.getLogger(KafkaTriggerHandler.class.getName());

    @Inject
    @Channel("func-source")
    Emitter<String> kafkaDataEmitter;

    public void run(String kafkaData,
            final ExecutionContext executionContext) {
        log.log(Level.INFO, "Handling data {0}", kafkaData);
        try {
            //TODO improve this with MutinyEmitter
            kafkaDataEmitter
                    .send(kafkaData)
                    .whenComplete((success, failure) -> {
                        if (failure != null) {
                            log.log(Level.SEVERE, "Error sending message {0}", failure);
                        } else {
                            log.log(Level.INFO, "Message processed successfully");
                        }
                    });
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error with run ", e);
        }
    }

    //TODO make it reactive
    @Incoming("func-source")
    @Outgoing("func-sink")
    @Broadcast
    public String kafkaDataHandler(String kafkaData) {
        //TODO is this suffice ??
        return kafkaData;
    }
}
