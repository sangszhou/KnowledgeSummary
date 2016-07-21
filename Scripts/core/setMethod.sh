#!/usr/bin/sh

die() {
    local error=${1:-Undefined error}
    echo "$0: $LINE $error"
}

die "File not found"

die