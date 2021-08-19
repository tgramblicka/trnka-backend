## How to start / restart VST on server ##

* ssh to server


## Shutdown aplikacie ##
* skontroluj ci aplikacia VST bezi pomocou prikazu
```
ps -aux | grep trnka-backend.jar
```
* ak VST bezi, tak na vystupe bude riadok podobny tomuto:
```
projekt+   14386  197  6.3 3484024 268000 pts/0  Sl   12:14   0:11 java -Xmx256m -Xms256m -jar trnka-backend.jar
```
* ak chces VST vypnut, treba zabit proces, ktory bezi s horeuvedenym PID pomocou prikazu:
```
kill 14386
```
* teraz po zadani prikazu by nemala uz bezat, mozes overit pomocou prikazu
```
ps -aux | grep trnka-backend.jar
```


## Start aplikacie ##
* najprv overit ci VST nahodou nebezi pomocou horeuvedeneho, ak nebezi tak: 
* Pre nastartovanie VST treba is do adresara
```
cd /home/projekttrnka/vst
```
* a spustit nasledovnym prikazom (VST nastartuje do cca 20 - 30sekund)
```
sh run.sh
```
## Logy aplikacie ##
* logy aplikacie VST sa nachadzaju v subore
```
/home/projekttrnka/vst/logs/output.log
```
* Po nastartovani VST je mozne si pozriet ako logy nabiehajuj pomocou prikazu: (vyskocenie z prezerania logov pomocou Ctrl + C)
```
tail -f -n 200 /home/projekttrnka/vst/logs/output.log
```

Aplikacia je nastartovana ked v logu uvidis
```
Trnka-backend is ready
```





