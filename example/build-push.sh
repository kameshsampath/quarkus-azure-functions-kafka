#!/usr/bin/env bash

set -eu

set -o pipefail

IMAGE_NAME=${IMAGE_NAME:-quay.io/kameshsampath/azure-func-kafka-example}

docker build --rm -t "$IMAGE_NAME" .

docker push "$IMAGE_NAME"