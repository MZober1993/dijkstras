#!/usr/bin/env bash
gnuplot -e "i=1" exp_std_error.plt - && \
gnuplot -e "i=2" exp_std_error.plt - && \
gnuplot -e "i=3" exp_std_error.plt -
