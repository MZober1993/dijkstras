#!/usr/bin/env bash
gnuplot -e "i=1" boxplot.plt - && \
gnuplot -e "i=2" boxplot.plt - && \
gnuplot -e "i=3" boxplot.plt -
