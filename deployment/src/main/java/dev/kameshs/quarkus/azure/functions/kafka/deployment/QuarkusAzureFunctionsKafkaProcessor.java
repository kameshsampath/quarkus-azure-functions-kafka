package dev.kameshs.quarkus.azure.functions.kafka.deployment;

import java.util.logging.Logger;

import com.microsoft.azure.functions.ExecutionContext;

import dev.kameshs.azure.functions.kafka.runtime.KafkaTriggerFunction;
import dev.kameshs.azure.functions.kafka.runtime.KafkaTriggerHandler;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.pkg.builditem.UberJarRequiredBuildItem;

class QuarkusAzureFunctionsKafkaProcessor {

    private static final String FEATURE = "quarkus-azure-functions-kafka";
    private static final Logger log = Logger.getLogger(QuarkusAzureFunctionsKafkaProcessor.class.getName());

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void addDependencies(BuildProducer<IndexDependencyBuildItem> indexDependency) {
        indexDependency.produce(new IndexDependencyBuildItem("com.microsoft.azure.functions", "azure-functions-java-library"));
    }

    @BuildStep
    public UberJarRequiredBuildItem forceUberJar() {
        // Azure Functions needs a single JAR inside a dedicated directory
        return new UberJarRequiredBuildItem();
    }

    @BuildStep
    void registerKafkaTriggerFunction(BuildProducer<AdditionalBeanBuildItem> additionalBeans) {
        log.info("Registering Function Beans");
        AdditionalBeanBuildItem.Builder builder = AdditionalBeanBuildItem.builder();
        builder.addBeanClass(KafkaTriggerFunction.class)
                .setUnremovable()
                .build();
        builder.addBeanClass(KafkaTriggerHandler.class)
                .setUnremovable()
                .build();
        builder.addBeanClass(ExecutionContext.class)
                .setUnremovable()
                .build();

        additionalBeans.produce(builder.build());
    }

}
