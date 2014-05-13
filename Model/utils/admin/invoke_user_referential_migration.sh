#! /bin/sh

. ./check_env.sh

. ./set_classpath.sh "$1" "$2"

if [ -n "$LIBREDEMAT_I18N_PATH" ]; then
  CLASSPATH="$CLASSPATH:$LIBREDEMAT_I18N_PATH"
else
  echo "Environment variable LIBREDEMAT_I18N_PATH must be set !";
  exit;
fi

java -Xms512m -Xmx2000m -cp $CLASSPATH org.libredemat.util.admin.UserReferentialMigration
