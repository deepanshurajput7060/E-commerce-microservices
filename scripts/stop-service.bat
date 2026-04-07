@echo off
title Stop All Microservices
color 0C

echo ============================================
echo   Stopping All Services
echo ============================================

:: -------------------------------
:: STOP MICROSERVICES
:: -------------------------------

echo Stopping Service Discovery (8761)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8761') do taskkill /PID %%a /F

echo Stopping Product Service (8081)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8081') do taskkill /PID %%a /F

echo Stopping Inventory Service (8082)...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8082') do taskkill /PID %%a /F

echo.
echo ============================================
echo   All Services Stopped Successfully
echo ============================================

pause