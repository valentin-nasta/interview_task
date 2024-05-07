#!/bin/bash

rm -fr dist
mkdir dist

echo "Build docker image with tag interview-backend"
docker build -f src/main/docker/Dockerfile.jvm . -t interview-backend

echo "Save interview-backend docker image"
docker save interview-backend -o dist/docker-image

echo "Copy all relevant data into dist"
mkdir dist/openapi
cp -p openapi/* dist/openapi
cp README.md dist/

cd dist
echo "Zipping everything together into "
zip -r interview-task.zip *

echo "You can find the interview-task.zip within dist directory"