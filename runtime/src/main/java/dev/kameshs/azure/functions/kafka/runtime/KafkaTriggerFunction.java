package dev.kameshs.azure.functions.kafka.runtime;

import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

import com.microsoft.azure.functions.ExecutionContext;

import io.smallrye.reactive.messaging.annotations.Broadcast;

public class KafkaTriggerFunction {

    private static final Logger log = Logger.getLogger("dev.kameshs.azure");

    @Inject
    @Channel("func-source")
    Emitter<String> kafkaDataEmitter;

    //TODO handling Data Serialization
    public void run(String kafkaData, final ExecutionContext executionContext) {
        log.log(Level.INFO, "Starting ... Quarkus Azure Functions Kafka");

        //TODO handle exception
        //TODO improve this with MutinyEmitter
        kafkaDataEmitter
                .send(kafkaData);
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
