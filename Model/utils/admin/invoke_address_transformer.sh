#! /bin/sh

# Takes at least tow arguments
#  -> the configuration (test or deployment)
#  -> the action name
#
# Example
#    ./invoke_address_transformer.sh deployment

. check_env.sh

. set_classpath.sh $1 valdoise

#java -Dhttp.proxyHost=172.16.249.4 -Dhttp.proxyPort=8080 -cp $CLASSPATH  org.libredemat.util.admin.AddressTransformer $1 $2
java -cp $CLASSPATH  org.libredemat.util.admin.AddressTransformer $1



