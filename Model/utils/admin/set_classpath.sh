#!/bin/sh

config=$1
local_authority=$2

if [ "$config" = "deployment" ]; then
  # deployment configuration
  echo "Deployment configuration ..."
  CommonLibPath="$LIBREDEMAT_LIB_PATH"
  CLASSPATH="$LIBREDEMAT_HOME/conf:$LIBREDEMAT_HOME/conf/spring:$LIBREDEMAT_HOME/conf/log4j"
  CLASSPATH="$CLASSPATH:$LIBREDEMAT_HOME/conf/referential/$local_authority"
else
  # development configuration
  echo "Test configuration ..."
  CommonLibPath="$LIBREDEMAT_HOME/Libraries"
  libDir="$LIBREDEMAT_HOME/Model/lib"
  archivesDir="$LIBREDEMAT_HOME/Model/build/archives"
  CLASSPATH="$LIBREDEMAT_HOME/Model/build/test/"
  for lib in $(find $archivesDir -name "*.jar"); do
    CLASSPATH="${CLASSPATH}:$lib"
  done
  CLASSPATH="$CLASSPATH:$LIBREDEMAT_HOME/Model/conf/spring"
    for lib in $(find $libD  -name "*.jar"); do
      CLASSPATH="${CLASSPATH}:$lib"
    done
  CLASSPATH="$CLASSPATH:$LIBREDEMAT_HOME/BackOfficeNG/grails-app/conf:$LIBREDEMAT_HOME/BackOfficeNG/grails-app/conf/spring:$LIBREDEMAT_HOME/Model/test/conf/spring:$LIBREDEMAT_HOME/Libraries/conf/deployments"
fi

for dir in $(echo $CommonLibPath | sed 's/:/ /g'); do
  for lib in $(find $dir -name "*.jar"); do
    CLASSPATH="${CLASSPATH}:$lib"
  done
done

CLASSPATH="${CLASSPATH}:$LIBREDEMAT_WEBAPP"
#for lib in $(find $GRAILS_HOME/lib  -name *.jar); do
#  echo "Looking at $lib"
#  CLASSPATH="${CLASSPATH}:$lib"
#done


export CLASSPATH
