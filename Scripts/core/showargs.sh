#!/bin/bash

exec 1>&2

echo "$0: $# parameters:"

## 这个什么文法, for 没有

for i in 1..$#
    do
#       echo "<$i>"
       echo $i
    done

exit 0