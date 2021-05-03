FROM mcr.microsoft.com/azure-functions/java:3.0-java11-core-tools

ARG settingsXml=settings.xml
ARG WORK_DIR=/workspace
ARG TARGET_DIR=${WORK_DIR}/target/azure-functions/azr-greeter-funqy

# Copy Local File
RUN mkdir /workspace
COPY . /workspace
WORKDIR ${WORK_DIR}
# dotnet

ENV LD_LIBRARY_PATH=${TARGET_DIR}/bin/runtimes/linux-x64/native

# Install Quarkus Extensions
RUN mvn --settings $settingsXml clean install

# Build the example
RUN cd example \
    && mvn --settings ../$settingsXml clean package

WORKDIR ${WORK_DIR}/example
ENV SETTINGS_XML="../$settingsXml"

CMD [ "sh","-c","/usr/bin/mvn --settings ${SETTINGS_XML} azure-functions:run" ]