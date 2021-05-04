package dev.kameshs.azure.functions.kafka.runtime;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.KafkaTrigger;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.Application;

public class KafkaTriggerFunction {

    private static final Logger log = Logger.getLogger(KafkaTriggerFunction.class.getName());
    private static final int BUFFER_SIZE = 8096;
    protected static String deploymentStatus;
    protected static boolean started = false;
    protected static boolean bootstrapError = false;
    KafkaTriggerHandler kafkaTriggerHandler;

    public KafkaTriggerFunction() {
        initQuarkus();

    }

    protected void initQuarkus() {
        StringWriter error = new StringWriter();
        PrintWriter errorWriter = new PrintWriter(error, true);
        if (Application.currentApplication() == null) { // were we already bootstrapped?
            try {
                Class<?> appClass = Class.forName("io.quarkus.runner.ApplicationImpl");
                String[] args = {};
                Application app = (Application) appClass.newInstance();
                app.start(args);
                errorWriter.println("Quarkus bootstrapped successfully.");
                started = true;
                this.kafkaTriggerHandler = Arc.container()
                        .instance(KafkaTriggerHandler.class).get();
            } catch (Throwable ex) {
                bootstrapError = true;
                errorWriter.println("Quarkus bootstrap failed.");
                ex.printStackTrace(errorWriter);
            }
        } else {
            errorWriter.println("Quarkus bootstrapped successfully.");
            if (this.kafkaTriggerHandler == null) {
                this.kafkaTriggerHandler = Arc.container()
                        .instance(KafkaTriggerHandler.class).get();
            }
            started = true;
        }
        deploymentStatus = error.toString();
    }

    @FunctionName("quarkus")
    public void run(
            @KafkaTrigger(name = "kafkaTrigger", topic = "foo", brokerList = "localhost:9092", consumerGroup = "bar") String kafkaData,
            final ExecutionContext executionContext) {
        log.log(Level.INFO, "Quarkus Azure Functions Kafka received data ", kafkaData);
        kafkaTriggerHandler.run(kafkaData, executionContext);
    }
}
