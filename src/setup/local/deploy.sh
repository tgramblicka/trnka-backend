#!/bin/bash

payara=/e/payara/
#payara=/c/Users/tomas/dev
asadmin=${payara}/payara5/glassfish/bin/asadmin

${asadmin} start-domain domain1
