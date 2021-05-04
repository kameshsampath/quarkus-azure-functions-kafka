
COPY target/azure-functions /deployments/azure-functions

# need to load the Kafka Library and other .NET dependencies for Extension
ENV LD_LIBRARY_PATH=/deployments/azure-functions/azr-greeter-funqy/bin/runtimes/linux-x64/native
ENV JAVA_HOME=/usr
# Disable coloring
ENV TERM=xterm-mono

WORKDIR deployments/azure-functions/azr-greeter-funqy

CMD ["/deployments/azure-functions-cli/func","host","start","--java"]


