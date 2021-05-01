package dev.kameshs.quarkus.azure.functions.kafka.deployment;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.arc.deployment.UnremovableBeanBuildItem;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.builditem.IndexDependencyBuildItem;
import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationTarget;
import org.jboss.jandex.AnnotationValue;
import org.jboss.jandex.DotName;

import io.quarkus.arc.deployment.AnnotationsTransformerBuildItem;
import io.quarkus.arc.processor.AnnotationsTransformer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.pkg.builditem.UberJarRequiredBuildItem;

class QuarkusAzureFunctionsKafkaProcessor {

    private static final String FEATURE = "quarkus-azure-functions-kafka";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    public UberJarRequiredBuildItem forceUberJar() {
        // Azure Functions needs a single JAR inside a dedicated directory
        return new UberJarRequiredBuildItem();
    }

    @BuildStep
    public AnnotationsTransformerBuildItem configureKafkaTrigger(
            KafkaTriggerAnnotationConfiguration kafkaTriggerAnnotationConfig) {

        return new AnnotationsTransformerBuildItem(new AnnotationsTransformer() {
            @Override
            public boolean appliesTo(AnnotationTarget.Kind kind) {
                return kind == AnnotationTarget.Kind.METHOD_PARAMETER;
            }

            @Override
            public void transform(TransformationContext context) {
                if (context.getTarget().asMethodParameter().name().equals("kafkaData")) {
                    context.transform().add(addKafkaTriggerAnnotation(context.getTarget(), kafkaTriggerAnnotationConfig));
                }
            }
        });

    }
    @BuildStep
    UnremovableBeanBuildItem ensureKafkaTriggerFunction() {
        return UnremovableBeanBuildItem.beanClassNames(
            "dev.kameshs.azure.functions.kafka.runtime.KafkaTriggerFunction");
    }

    AnnotationInstance addKafkaTriggerAnnotation(AnnotationTarget target, KafkaTriggerAnnotationConfiguration config) {
        // e.g. @KafkaTrigger(name = "kafkaTrigger", topic = "myTopic", brokerList = "", consumerGroup = "functions")
        DotName kafkaTriggerDotName = DotName.createSimple("com.microsoft.azure.functions.annotation.KafkaTrigger");

        List<AnnotationValue> annotationValues = new ArrayList<>();

        //TODO expand and add validations
        annotationValues.add(AnnotationValue.createStringValue("name", config.name));
        annotationValues.add(AnnotationValue.createStringValue("topic", config.topic));
        annotationValues.add(AnnotationValue.createStringValue("brokerList", config.brokerList));
        annotationValues.add(AnnotationValue.createStringValue("consumerGroup", config.consumerGroup.orElse("functions")));

        return AnnotationInstance.create(kafkaTriggerDotName, target, annotationValues);
    }

}
