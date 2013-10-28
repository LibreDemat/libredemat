#! /bin/sh

# Takes at least three arguments
#  -> the configuration (test or deployment)
#  -> the local authority name
#  -> the folder or file path

. ./check_env.sh

. ./set_classpath.sh $1 $2

java -cp $CLASSPATH org.libredemat.plugins.externalservices.edemande.importer.AssoImporter $1 $2 $3

