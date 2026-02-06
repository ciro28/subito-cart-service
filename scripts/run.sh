#!/bin/sh
docker build -t cart-service .
docker run -p 8080:8080 cart-service
