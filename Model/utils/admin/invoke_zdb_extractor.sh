#! /bin/sh

. ./check_env.sh

. ./set_classpath.sh "$1"

java -Xms256m -Xmx512m -cp $CLASSPATH  org.libredemat.util.admin.DocumentToZDBExtractor "$1"
