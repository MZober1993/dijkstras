#!/usr/bin/env bash
 gnuplot -e "i=0" count.plt - && \
 gnuplot -e "i=1" count.plt -
