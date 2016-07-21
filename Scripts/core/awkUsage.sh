#!/bin/bash

cd `dirname $0`/..

PROJECT_DIR=`pwd`

RESOURCE_DIR=$PROJECT_DIR/resource

CONFIG_FILE=$RESOURCE_DIR/run.conf

echo $PROJECT_DIR
echo $RESOURCE_DIR
echo $CONFIG_FILE


MYSQLDBHOME=`grep mysqldbhome $CONFIG_FILE | awk -F '=' '{print $2}' | xargs`
MYSQLDBSERVERNAME=`grep mysqldbservername $CONFIG_FILE | awk -F ':' '{print $2}' | xargs`

echo "|$MYSQLDBHOME|"
echo "|$MYSQLDBSERVERNAME|"