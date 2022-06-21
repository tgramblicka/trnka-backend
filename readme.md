## How to redeploy on server ##

* ssh to server
* ps -aux | grep java 
* kill java process with trnka-backend.jar
* connect to mysql 
```
mysql -u tublet -p
``` 
* drop database with command
```
 drop database `vst`;
```
* recreate database 
```
 create database vst;
 GRANT ALL PRIVILEGES ON `vst`.* TO 'vst'@'localhost'; (not needed)
```
* copy the liquibase-run-on-server-mysql.sh to server using copy.sh (not mandatory)
* copy all the changelog files to the server using copy.sh
* run liquibase from location "/home/projekttrnka/vst/liquibase" with command: 
```
./liquibase-run-on-server-mysql.sh
```
* build the jar file locally 
```
mvn clean package -Dmaven.test.skip=true
```
* copy the jar file to server using copy.sh script
* copy the run.sh script using the copy.sh (not mandatory)
* empty the log with command ```> output.log``` in location /home/projekttrnka/vst/logs 
* run the run.sh script from location /home/projekttrnka/vst
```
sh run.sh &
``` 
* read the log file via command
```
tail -f -n 200 logs/output.log
```

 

