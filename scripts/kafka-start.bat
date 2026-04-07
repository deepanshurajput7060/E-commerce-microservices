@echo off
title Kafka & Zookeeper Starter
color 0A

:: ================================
:: CONFIG (UPDATE THIS PATH)
:: ================================
set KAFKA_HOME=C:\kafka_2.13-3.4.0

echo Starting Zookeeper...
start "Zookeeper" cmd /k "%KAFKA_HOME%\bin\windows\zookeeper-server-start.bat %KAFKA_HOME%\config\zookeeper.properties"

timeout /t 5 > nul

echo Starting Kafka Broker...
start "Kafka Broker" cmd /k "%KAFKA_HOME%\bin\windows\kafka-server-start.bat %KAFKA_HOME%\config\server.properties"

echo.
echo =====================================
echo   Kafka & Zookeeper Started
echo =====================================
pause