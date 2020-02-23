#!/bin/bash

if [ $(curl -LI localhost:8080/vst/monitoring/alive -o /dev/null -w '%{http_code}\n' -s) != "200" ]; then

        # trnka development
        MARIA_DB_USER=admin
        export MARIA_DB_USER

        MARIA_DB_PWD=VST-user-123
        export MARIA_DB_PWD

        MARIA_DB_DATASOURCE_URL='jdbc:mariadb://localhost:3307/vst?autoReconnect=true&relaxAutoCommit=true'
        export MARIA_DB_DATASOURCE_URL

        echo "not deployed, will run deployment";
        logFile="/volume1/homes/tomas/dev/trnka/vst/log-`date '+%Y-%m-%d-%H:%M:%S'`.log"
        touch $logFile;
        nohup /volume1/@appstore/Java8/j2sdk-image/jre/bin/java -Xmx256m -Xms256m -jar /volume1/homes/tomas/dev/trnka/vst/trnka-backend.jar >> $logFile 2>&1 &
fi
