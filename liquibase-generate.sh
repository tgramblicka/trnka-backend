#!/bin/sh

mariaDbClientJar="/c/Users/200000591/apps/mariadb-java-client-2.4.3.jar"
host="jdbc:mariadb://localhost:3306/vst"
usr="root"
pwd="root"


## for generating changelog from existing DB
liquibase \
--changeLogFile="./src/main/resources/db/changelog/changelog.mariadb.sql" \
--url=$host \
--diffTypes="tables,columns,indexes,foreignkeys,primarykeys,uniqueconstraints" \
--driver="org.mariadb.jdbc.Driver" \
--classpath="$mariaDbClientJar" \
--username=$usr \
--password=$pwd \
--logLevel="INFO" \
generateChangeLog

