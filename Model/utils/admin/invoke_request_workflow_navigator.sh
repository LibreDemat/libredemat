#! /bin/sh

. ./check_env.sh

. ./set_classpath.sh "$1" "$2"

java -Xms256m -Xmx256m -cp $CLASSPATH  org.libredemat.util.admin.RequestWorkflowNavigator "$1" "$2" "$3" "$4" "$5" "$6"
