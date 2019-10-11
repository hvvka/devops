#!/bin/sh

apt-get update
apt-get -y upgrade

apt-get install -y openjdk-11-jdk
apt-get install -y git

cd /vagrant

JAR=jhipster-sample-app/target/jhipster-sample-application-0.0.1-SNAPSHOT.jar
if [ ! $(ls $JAR 2> /dev/null) ]; then
    git clone https://github.com/jhipster/jhipster-sample-app.git
    cd jhipster-sample-app
    ./mvnw -Pprod clean verify
    cd ..
fi
java -jar "${JAR}"

# xdg-open http://localhost:8080
