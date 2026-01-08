@echo off
echo Compiling...
if not exist out mkdir out
javac -d out -sourcepath src src/Main.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b %errorlevel%
)
echo Running...
java -cp out Main
