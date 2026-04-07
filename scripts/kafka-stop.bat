@echo off
title Kafka & Zookeeper Stopper
color 0C

echo Stopping Kafka (port 9092)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :9092') do taskkill /PID %%a /F

echo Stopping Zookeeper (port 2181)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :2181') do taskkill /PID %%a /F

echo.
echo =====================================
echo   Kafka & Zookeeper Stopped
echo =====================================
pause