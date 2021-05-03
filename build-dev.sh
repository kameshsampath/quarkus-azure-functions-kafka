#!/usr/bin/env bash

set -eu

set -o pipefail

IMAGE_NAME=${IMAGE_NAME:-example/azure-func-kafka-example}
MAVEN_SETTINGS_XML=${MAVEN_SETTINGS_XML:-settings-docker-local.xml}

docker build \
   --build-arg settingsXml="$MAVEN_SETTINGS_XML" \
   --rm -t "$IMAGE_NAME" .