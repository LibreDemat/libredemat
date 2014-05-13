#!/bin/sh

if [ -n "$LIBREDEMAT_HOME" ]; then
    echo "LIBREDEMAT_HOME is $LIBREDEMAT_HOME";
else
    echo "Environment variable LIBREDEMAT_HOME must be set !";
fi

if [ -n "$LIBREDEMAT_WEBAPP" ]; then
  echo "LIBREDEMAT_WEBAPP is $LIBREDEMAT_WEBAPP";
else
  echo "Environment variable LIBREDEMAT_WEBAPP must be set !";
fi

if [ "$1" = "deployment" ]; then
  if [ -n "$LIBREDEMAT_LIB_PATH" ]; then
      echo "LIBREDEMAT_LIB_PATH is $LIBREDEMAT_LIB_PATH";
  else
      echo "Environment variable LIBREDEMAT_LIB_PATH must be set !";
  fi
fi

if [ -z "$LIBREDEMAT_HOME" ]; then
  exit;
fi
if [ -z "$LIBREDEMAT_WEBAPP" ]; then
  exit;
fi
if [ "$1" = "deployment" -a -z "$LIBREDEMAT_LIB_PATH" ]; then
  exit;
fi
