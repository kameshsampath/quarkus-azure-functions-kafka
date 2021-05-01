package dev.kameshs.quarkus.azure.functions.kafka.deployment;

import io.quarkus.runtime.annotations.ConfigItem;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;

import java.util.Optional;

@ConfigRoot(name = "azure-functions-kafka", phase = ConfigPhase.BUILD_TIME)
public class KafkaTriggerAnnotationConfiguration {

    /**
     * The Kafka Trigger Name
     */
    @ConfigItem
    String name;

    /**
     * The Kafka Brokers List
     */
    @ConfigItem
    String brokerList;

    /**
     * The Kafka Topic to Listen events
     */
    @ConfigItem
    String topic;

    /**
     * The Azure Kafka Trigger Cardinality
     * TODO {@link com.microsoft.azure.functions.annotation.Cardinality}
     */
    @ConfigItem
    Optional<String> cardinality;

    /**
     * The Kafka Event Data Type
     */
    @ConfigItem
    Optional<String> dataType;

    /**
     * The Kafka Event Consumer Group
     */
    @ConfigItem(defaultValue = "functions")
    Optional<String> consumerGroup;

    /**
     * The Kafka Authentication Mode
     * TODO {@link com.microsoft.azure.functions.BrokerAuthenticationMode}
     */
    @ConfigItem
    Optional<String> authenticationMode;

    /**
     * The Kafka Authentication username
     */
    @ConfigItem
    Optional<String> username;

    /**
     * The Kafka Authentication user password
     */
    @ConfigItem
    Optional<String> password;

    /**
     * The Kafka Broker Protocol {@link com.microsoft.azure.functions.BrokerProtocol}
     */
    @ConfigItem
    Optional<String> protocol;

    /**
     * The Kafka Authentication SSL Key Location
     */
    @ConfigItem
    Optional<String> sslKeyLocation;

    /**
     * The Kafka Authentication SSL CA Location
     */
    @ConfigItem
    Optional<String> sslCaLocation;

    /**
     * The Kafka Authentication SSL Certificate Location
     */
    @ConfigItem
    Optional<String> sslCertificateLocation;

    /**
     * The Kafka Authentication SSL Certificate Password
     */
    @ConfigItem
    Optional<String> sslKeyPassword;

}
