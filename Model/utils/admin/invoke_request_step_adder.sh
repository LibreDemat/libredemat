#! /bin/sh

. ./check_env.sh

. ./set_classpath.sh "$1" "$2"

echo $3
echo $4
echo $5

java -Xms256m -Xmx256m -cp $CLASSPATH  org.libredemat.util.admin.RequestStepAdder "$3" "$4" "$5"
