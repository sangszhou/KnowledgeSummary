#!/usr/bin/env bash

# @Function
# echo each arguments on one line colorfully.
#
# @Usage
#   $ ./a2l arg1 arg2
#   $ ./a2l *.txt
#
# @author Jerry Lee


colorEcho() {
    local color="$1"

    #remove the first argument, $@ return result from $2
    shift

    if [ -c /dev/stdout ] ; then
        # if stdout is console, turn on color output.
        echo -ne "\033[1;${color}m"
        echo -n "$@"
        echo -e "\033[0m"
    else
        echo "$@"
    fi
}

readonly ECHO_COLORS=(37 31 32 34 33 35 56)
COUNT=0

for a in "$@"; do
    colorEcho "${ECHO_COLORS[$((COUNT++ % ${#ECHO_COLORS[@]}))]}" "$a"
done