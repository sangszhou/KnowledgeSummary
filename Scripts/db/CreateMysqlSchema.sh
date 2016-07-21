#!/bin/bash

#ssh to dest host and create mysql schema using root privilege

cd `dirname $0`/..
PROJECT_DIR=`pwd`

echo "PROJECT_DIR: $PROJECT_DIR"
echo "sourcing db schema..."
echo "DB_HOME: $DB_HOME"
echo "DB_SERVER_NAME: $DB_SERVER_NAME"

scp $PROJECT_DIR/config/*.sql $DB_SERVER_NAME:$DB_HOME/

ssh $DB_SERVER_NAME "
    $DB_HOME/bin/mysql.sh -e 'source $DB_HOME/icamapi-schema.sql';
    $DB_HOME/bin/mysql.sh -e 'source $DB_HOME/icamapi-data.sql';
    rm -f $DB_HOME/icamapi-*.sql
"