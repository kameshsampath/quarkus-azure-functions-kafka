package dev.kameshs.azure.functions.kafka.runtime;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.KafkaTrigger;

import io.smallrye.reactive.messaging.annotations.Broadcast;

public class KafkaTriggerFunction {

    private static final Logger log = Logger.getLogger("dev.kameshs.azure");

    @Inject
    @Channel("func-source")
    Emitter<String> kafkaDataEmitter;

    @FunctionName("quarkus")
    public void run(
            @KafkaTrigger(name = "kafkaTrigger", topic = "foo", brokerList = "localhost:9092", consumerGroup = "bar") String kafkaData,
            final ExecutionContext executionContext) {
        log.log(Level.INFO, "Quarkus Azure Functions Kafka received data " + kafkaData);
        try {
            //TODO improve this with MutinyEmitter
            kafkaDataEmitter
                    .send(kafkaData)
                    .whenComplete((success, failure) -> {
                        if (failure != null) {
                            log.log(Level.ERROR, "Error sending message {0}", failure);
                        } else {
                            log.log(Level.INFO, "Message processed successfully");
                        }
                    });
        } catch (Exception e) {
            log.log(Level.ERROR, "Error with run ", e);
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
