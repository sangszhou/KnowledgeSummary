#!/usr/bin/env bash

# Not working
# @Function
# convert to Absolute Path.
#
# @Usage
#   # print Absolute Path of current directory.
#   $ ./ap
#   # print Absolute Path of arguments.
#   $ ./ap a.txt ../dir1/b.txt
#
# @author Jerry Lee

[ $# -eq 0 ] && files=(.) || files=("$@")

for f in "${files[@]}" ; do
    ! [ -e $f ] && {
        echo "$f does not exists!"
        continue
    }
    readlink -f "$f"
done