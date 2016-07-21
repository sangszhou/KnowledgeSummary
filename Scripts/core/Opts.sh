#!/bin/bash

usage() {
    echo "Usage: $0 [-s <45|90>] [-p <string>]" 1>&2;  // 1&2 是什么
    exit 1;
}

while getopts ":s:p:" o; do #":"表示该选项有值，第一个":"使得有未定义的参数的话不报错
    case "${o}" in
        s)
            s=${OPTARG} # OPTARG 是当前获得的参数的值
            ((s == 45 || s == 90)) || usage
            ;;
        p)
            p=${OPTARG}
            ;;
        *)
            usage
            ;;
    esac
done

shift $((OPTIND-1)) # $OPTIND 总是存储原始 $* 中下一个要处理的元素位置,  OPTIND 起始为1 ，然后递增

if [ -z "${s}" ] || [ -z "${p}" ]; then
    usage
fi

echo "s = ${s}"
echo "p = ${p}"

