package dev.kameshs.quarkus.azure.functions.kafka.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.LaunchModeBuildItem;
import io.quarkus.deployment.pkg.builditem.UberJarRequiredBuildItem;
import io.quarkus.runtime.LaunchMode;
import io.quarkus.vertx.http.deployment.RequireVirtualHttpBuildItem;

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
  public RequireVirtualHttpBuildItem requestVirtualHttp(
      LaunchModeBuildItem launchMode) {
    return launchMode.getLaunchMode() == LaunchMode.NORMAL
        ? RequireVirtualHttpBuildItem.MARKER : null;
  }
}