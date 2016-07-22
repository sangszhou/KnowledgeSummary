#!/bin/bash

cat file.txt | while read line
do
    #how to split line by separator?
    IFS=" "

    echo ${line[@]}

    arrIN=(${line//;/ })

    echo ${arrIn[@]}
    echo ${#arrIn[@]}

done


#IFS 在什么地方有效果?
#${!line[@]} 是什么意思?