#!/bin/sh


mariaDbClientJar="/c/Users/200000591/apps/mariadb-java-client-2.4.3.jar"
host="jdbc:mariadb://192.168.1.2:3307/vst"
username="vst"
pwd="VST-user-123"


# LIQUIBASE FOR PC
liquibase \
--changeLogFile="./src/main/resources/db/changelog/changelog-master.xml" \
--url=$host \
--driver="org.mariadb.jdbc.Driver" \
--classpath="$mariaDbClientJar" \
--username=$username \
--password=$pwd \
--logLevel="INFO" \
update
