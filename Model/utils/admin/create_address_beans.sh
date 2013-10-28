#! /bin/sh

. ./check_env.sh

. ./set_classpath.sh $1 valdoise

java -cp $CLASSPATH  org.libredemat.util.admin.AddressBeanCreator $2 $3
