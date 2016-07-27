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

awk '{if (NR==10) print $0}' file.txt

##Solution 3
awk "NR==10" file.txt

### Solution 4
#sed is a stream editor under command line. It executes the
# instruction on the text stream and the following prints the 10-th line using sed.

sed -n '10p' file.txt


##Solution 5

NR=`cat file.txt | wc -l`

if [ $NR -ge 10 ]; then
    head -10 file.txt | tail -1
fi
