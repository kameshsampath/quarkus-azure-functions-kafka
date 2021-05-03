package dev.kameshs.quarkus.azure.functions.kafka.deployment;

import org.jboss.logging.Logger;

import dev.kameshs.azure.functions.kafka.runtime.KafkaTriggerFunction;
import io.quarkus.arc.deployment.AdditionalBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import io.quarkus.deployment.pkg.builditem.UberJarRequiredBuildItem;

class QuarkusAzureFunctionsKafkaProcessor {

    private static final String FEATURE = "quarkus-azure-functions-kafka";
    private static final Logger log = Logger.getLogger(QuarkusAzureFunctionsKafkaProcessor.class);

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
    AdditionalBeanBuildItem registerKafkaFunctions() {
        log.info("Registering Function Beans");
        return AdditionalBeanBuildItem.builder()
                .addBeanClass(KafkaTriggerFunction.class)
                .setUnremovable()
                .build();
    }
}
