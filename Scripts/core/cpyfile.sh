#!/bin/bash

FILE=/users/vdeadmin/.iccs

for host in icam-stage-soa-0{1..3}
do
	echo "entering $host ... "
	scp $FILE vdeadmin@$host:/users/vdeadmin
done