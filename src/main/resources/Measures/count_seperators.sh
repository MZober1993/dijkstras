#!/usr/bin/env bash

rm -f first_lines_of_$1
head -1 $1 > first_lines_of_$1
count_sign , first_lines_of_$1
rm -f first_lines_of_$1