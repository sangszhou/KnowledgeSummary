#!/bin/zsh

## 读入文件
## 每行的 Word 添加到数据结构中
## 按照出现频率由高到低输出

## IFS internal field separator 可以用来划分单词

#IFS=" "
#
#declare -A data
#
#for key in `cat words.txt`
#    do
#        echo "$key"
#
#        if [[ -z "${data[$key]}" ]]; then
#            data[$key]=1
#        else
#            let data[$key]++
#        fi
#    done
#
#for key in ${!data[@]}; do
#    echo "$key occured ${data[$key]} times"
#done

declare -a ARY
declare -A MAP

MAP+=([a]=1 [b]=2)
ARY+=(a b c)

echo ${ARY[1]}
echo ${MAP[a]}
echo "${ARY[@]}"
echo "${MAP[@]}"
echo "${ARY[@]:0:1}"
echo ${#ARY[@]}
echo "${!MAP[@]}"
ARY[4]=a
echo ${ARY[@]}
echo ${ARY[3]}