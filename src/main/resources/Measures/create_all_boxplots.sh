gnuplot -e "filename='boxplot_10'" -e "output_format='$1'" boxplot.plt && \
gnuplot -e "filename='boxplot_100'" -e "output_format='$1'" boxplot.plt - && \
gnuplot -e "filename='boxplot_1000'" -e "output_format='$1'" boxplot.plt
