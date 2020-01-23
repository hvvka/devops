#!/bin/bash

minikube start --cpus 4 --memory 8192
kubectl apply -f app-deployment.yaml,app-service.yaml,mysql-claim0-persistentvolumeclaim.yaml,mysql-deployment.yaml,mysql-service.yaml
