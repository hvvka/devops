#!/usr/bin/env bash

# sudo yum install -y wget
# wget https://dev.mysql.com/get/mysql57-community-release-el7-8.noarch.rpm
# sudo yum install -y mysql57-community-release-el7-8.noarch.rpm
sudo yum -y update

sudo yum -y install mysql-server

# sudo systemctl stop mysql
# sudo mkdir /var/run/mysqld
sudo chown mysql:mysql /var/run/mysqld
sudo /usr/sbin/mysqld --lower_case_table_names=1 --skip-ssl --character_set_server=utf8mb4 --explicit_defaults_for_timestamp --skip-grant-tables --skip-networking &
# mysql -u root


sudo systemctl start mysqld
# sudo systemctl enable mysqld
# MYSQL_TEMP_PWD=`sudo cat /var/log/mysqld.log | grep 'A temporary password is generated' | awk -F'root@localhost: ' '{print $2}'`
# mysqladmin -u root -p`echo $MYSQL_TEMP_PWD` password 'Passw0rd!'
# cat << EOF > .my.cnf
# [client]
# user=root
# password=Passw0rd!
# EOF

# mysqld --lower_case_table_names=1 --skip-ssl --character_set_server=utf8mb4 --explicit_defaults_for_timestamp