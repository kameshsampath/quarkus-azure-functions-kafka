FROM registry.access.redhat.com/ubi8/ubi-minimal:8.3

ARG JAVA_PACKAGE=java-11-openjdk-headless
ARG RUN_JAVA_VERSION=1.3.8
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

# Install java and the run-java script
# Also set up permissions for user `1001`
RUN microdnf install dotnet-sdk-5.0 wget unzip gzip ca-certificates ${JAVA_PACKAGE} \
    && microdnf update \
    && microdnf clean all \
    && mkdir /deployments /.azurefunctions /.azure-functions-core-tools \
    && chown 1001 /deployments /.azurefunctions /.azure-functions-core-tools \
    && chmod "g+rwX" /deployments /.azurefunctions /.azure-functions-core-tools \
    && chown 1001:root /deployments /.azurefunctions /.azure-functions-core-tools \
    && curl https://repo1.maven.org/maven2/io/fabric8/run-java-sh/${RUN_JAVA_VERSION}/run-java-sh-${RUN_JAVA_VERSION}-sh.sh -o /deployments/run-java.sh \
    && chown 1001 /deployments/run-java.sh \
    && chmod 540 /deployments/run-java.sh \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/lib/security/java.security \
    && wget -O Azure.Functions.Cli.linux-x64.3.0.3442.zip https://github.com/Azure/azure-functions-core-tools/releases/download/3.0.3442/Azure.Functions.Cli.linux-x64.3.0.3442.zip \
    && unzip -d /deployments/azure-functions-cli Azure.Functions.Cli.linux-x64.3.0.3442.zip \
    && chmod +x /deployments/azure-functions-cli/func \
    && chmod +x /deployments/azure-functions-cli/gozip \
    && rm -rf Azure.Functions.Cli.linux-x64.3.0.3442.zip

EXPOSE 8080
USER 1001

COPY target/azure-functions /deployments/azure-functions

# need to load the Kafka Library and other .NET dependencies for Extension
ENV LD_LIBRARY_PATH=/deployments/azure-functions/azr-greeter-funqy/bin/runtimes/linux-x64/native
ENV JAVA_HOME=/usr
# Disable coloring
ENV TERM=xterm-mono

WORKDIR deployments/azure-functions/azr-greeter-funqy

CMD ["/deployments/azure-functions-cli/func","host","start","--java"]


