gnuplot -e "filename='measure_10'" -e "output_format='$1'" measure.plt && \
gnuplot -e "filename='measure_100'" -e "output_format='$1'" measure.plt  && \
gnuplot -e "filename='measure_1000'" -e "output_format='$1'" measure.plt && \
gnuplot -e "filename='measure_10000'" -e "output_format='$1'" measure.plt
