#! /bin/sh

. ./check_env.sh

. ./set_classpath.sh "$1" "$2"

java -cp $CLASSPATH org.libredemat.util.admin.EdemandeTracesSquasher
