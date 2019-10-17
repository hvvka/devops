#!/usr/bin/env bash

apt-get -y update

apt-get install -y apache2
apt-get install -y git

cd /vagrant

if ! [ -L /var/www ]; then
  rm -rf /var/www
  git clone https://github.com/mdn/beginner-html-site-styled.git
  ln -fs /vagrant /var/www
fi
