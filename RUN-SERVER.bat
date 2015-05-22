echo off
set classpath=.;sqlite.jar;bcprov-jdk16-142.jar;%classpath%;
javac -d . *.java
pause
java bank.BankMainForm
pause