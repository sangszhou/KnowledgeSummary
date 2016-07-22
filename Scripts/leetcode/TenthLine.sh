#!/usr/bin/env bash

#Solution1 using loop
#pass
lineNum=0
cat file.txt | while read line
do
    lineNum="$(( $lineNum + 1 ))"
    if [[ lineNum -eq 10 ]]; then
        echo $line
    fi
done


#Solution2
#