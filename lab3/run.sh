#!/bin/bash

APP_DIR=jhipster-sample-app

[ ! -d "${APP_DIR}" ] && \
git pull git@github.com:jhipster/"${APP_DIR}".git
cp Dockerfile jhipster-sample-app
docker-compose -f app.yml up -d
