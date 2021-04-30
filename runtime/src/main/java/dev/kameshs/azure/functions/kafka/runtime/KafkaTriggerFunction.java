package dev.kameshs.azure.functions.kafka.runtime;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.KafkaTrigger;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment;
import org.eclipse.microprofile.reactive.messaging.Acknowledgment.Strategy;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;

public class KafkaTriggerFunction extends BaseFunction {

  private static final Logger log = Logger.getLogger("dev.kameshs.azure");

  @Inject
  @Channel("func-in")
  Emitter<String> kafkaDataEmitter;

  //TODO handling Data Serialization
  public void run(
      @KafkaTrigger(name = "kafkaTrigger", topic = "", brokerList = "", consumerGroup = "functions") String kafkaData,
      final ExecutionContext executionContext) {
    log.log(Level.INFO, "Starting ... Quarkus Azure Functions Kafka");

    if (!started && !bootstrapError) {
      initQuarkus();
    }

    if (!bootstrapError) {
      kafkaDataEmitter.send(kafkaData);
    } else {
      log.log(Level.ERROR, "Error starting function " + deploymentStatus);
    }
  }

  @Incoming("func-in")
  @Outgoing("func-out")
  @Broadcast
  @Acknowledgment(Strategy.PRE_PROCESSING)
  public String kafkaDataHandler(String kafkaData) {
    //TODO is this suffice ??
    return kafkaData;
  }
}
