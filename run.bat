echo off
set classpath=.;jce.jar;jns-1.7.jar;bcprov-jdk16-142.jar;%classpath%;
javac -d . *.java
pause
jar -cvf seckey.jar seckey pack* 
java seckey.Network
pause