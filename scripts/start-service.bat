@echo off
title E-commerce Microservices Starter
color 0A

set PROJECT_ROOT=%~dp0..

cd /d %PROJECT_ROOT%

echo ============================================
echo   Starting E-commerce Microservices
echo ============================================

echo Starting Service Discovery (Eureka)...
start "Service Discovery" cmd /k "cd /d %PROJECT_ROOT%\service-discovery && mvnw.cmd spring-boot:run"

timeout /t 10 > nul

echo Starting Inventory Service...
start "Inventory Service" cmd /k "cd /d %PROJECT_ROOT%\inventory-service && mvnw.cmd spring-boot:run"

timeout /t 5 > nul

echo Starting Product Service...
start "Product Service" cmd /k "cd /d %PROJECT_ROOT%\product-service && mvnw.cmd spring-boot:run"

echo.
echo ============================================
echo   All Services Started Successfully
echo ============================================
pause