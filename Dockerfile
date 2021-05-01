FROM mcr.microsoft.com/azure-functions/java:3.0-java11-core-tools

ARG WORK_DIR=/workspace
ARG TARGET_DIR=${WORK_DIR}/target/azure-functions/azr-greeter-funqy

# Copy Local File
RUN mkdir /workspace
COPY . /workspace
WORKDIR ${WORK_DIR}
# dotnet

ENV LD_LIBRARY_PATH=${TARGET_DIR}/bin/runtimes/linux-x64/native

# Install Quarkus Extensions
RUN mvn -v \
    &&  mvn --settings settings.xml clean install

# Build the example
RUN cd example \
    && mvn --settings ../settings.xml clean package

WORKDIR ${TARGET_DIR}
RUN dotnet nuget add source /workspace/temp --name Local && dotnet nuget list source
RUN func extensions install --package Microsoft.Azure.WebJobs.Extensions.Kafka --version 100.100.100-pre

WORKDIR ${WORK_DIR}/example

CMD [ "/usr/bin/mvn","--settings","../settings.xml", "azure-functions:run" ]