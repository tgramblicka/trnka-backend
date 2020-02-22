#!/bin/bash
if [ $(curl -LI localhost:8080/vst/monitoring/alive -o /dev/null -w '%{http_code}\n' -s) != "200" ]; then
        echo "not deployed, will run deployment";
        logFile="/volume1/homes/tomas/dev/trnka/vst/log-`date '+%Y-%m-%d-%H:%M:%S'`.log"
        touch $logFile;
        nohup /volume1/@appstore/Java8/j2sdk-image/jre/bin/java -Xmx256m -Xms256m -jar /volume1/homes/tomas/dev/trnka/vst/trnka-backend.jar >> $logFile 2>&1 &
fi
