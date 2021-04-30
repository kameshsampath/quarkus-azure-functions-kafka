package dev.kameshs.quarkus.azure.functions.kafka.deployment;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

@ConfigRoot(name = "azure-functions-kafka", phase = ConfigPhase.BUILD_TIME)
public class KafkaTriggerConfiguration {

  @ConfigItem(defaultValue = "")
  String name;

  @ConfigItem(defaultValue = "")
  String brokerList;

  @ConfigItem(defaultValue = "")
  String topic;

  //TODO Cardinality
  @ConfigItem(defaultValue = "one")
  String cardinality;

  @ConfigItem(defaultValue = "")
  String dataType;

  @ConfigItem(defaultValue = "functions")
  String consumerGroup;

  //TODO - BrokerAuthenticationMode
  @ConfigItem(defaultValue = "")
  String authenticationMode;

  @ConfigItem(defaultValue = "")
  String username;

  @ConfigItem(defaultValue = "")
  String password;

  //TODO - BrokerProtocol
  @ConfigItem(defaultValue = "-1")
  String protocol;

  @ConfigItem(defaultValue = "")
  String sslKeyLocation;

  @ConfigItem(defaultValue = "")
  String sslCaLocation;

  @ConfigItem(defaultValue = "")
  String sslCertificateLocation;

  @ConfigItem(defaultValue = "")
  String sslKeyPassword;

}
