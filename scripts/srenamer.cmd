@echo off
setlocal

set SCRIPT_DIR=C:\x\Tools\scripts
set LIB_DIR=%SCRIPT_DIR%\scala-renamer-lib

rem --- We have copied Scala jars to LIB_DIR ---
rem set SCALA_LIB_DIR=C:\x\Tools\scala-2.9.1.final\lib
set SCALA_LIB_DIR=%LIB_DIR%

set SCALA_LIB_CP=%SCALA_LIB_DIR%\scala-library.jar;%SCALA_LIB_DIR%\scala-compiler.jar;%SCALA_LIB_DIR%\scala-dbc.jar
set LIB_CP=%LIB_DIR%\commons-lang-2.6.jar;%LIB_DIR%\commons-io-2.2.jar;%LIB_DIR%\ant.jar;%LIB_DIR%\scala-renamer.jar
set CP=%SCALA_LIB_CP%;%LIB_CP%
set CLASS=net.may.filerenamer.ScalaFileRenamer
set ARGS=%*

call java -classpath %CP% %CLASS% %ARGS%

endlocal
