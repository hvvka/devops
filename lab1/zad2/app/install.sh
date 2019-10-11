#!/bin/sh

apt-get update
apt-get -y upgrade

apt-get -y install openjdk-11-jdk

apt-get install -y xubuntu-desktop virtualbox-guest-dkms virtualbox-guest-utils virtualbox-guest-x11

java -jar *.jar
