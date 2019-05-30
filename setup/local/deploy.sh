#!/bin/bash
#ssh pi@192.168.1.15

#payara=/e/payara/
#project_location="/e/workspace_idea/trnka/trnka-backend"

payara=/c/Users/tomas/dev
project_location="/C/Users/tomas/dev/workspace/trnka/trnka-backend"

war_name="trnka-backend.war"



payara_location=${payara}/payara5/glassfish
asadmin=${payara}/payara5/glassfish/bin/asadmin
asadminpass="$asadmin --user admin --passwordfile=$project_location/setup/local/asadmin_password_file"
domain="domain1"


now=$(date +"%T")
echo $now

${asadmin} stop-domain ${domain}
rm -rf ${payara_location}/domains/${domain}/applications/*
rm -rf ${payara_location}/domains/${domain}/generated/*
rm -rf ${payara_location}/domains/${domain}/osgi-cache/*
rm -rf ${payara_location}/domains/${domain}/eclipseApps/*
rm -rf ${payara_location}/domains/${domain}/eclipseAppsTmp/*

> ${payara_location}/domains/${domain}/logs/server.log


${asadmin} start-domain --debug ${domain}
${asadminpass} deploy --force ${project_location}/target/${war_name}


#cp ${project_location}/setup/local/domain.xml ${payara_location}/domains/${domain}/config/domain.xml



#${asadmin} start-domain domain1
#asadmin undeploy trnka-backend

