#!/bin/bash
docker build -t cart-service-test -f Dockerfiletest .
docker run --rm cart-service-test ./mvnw test