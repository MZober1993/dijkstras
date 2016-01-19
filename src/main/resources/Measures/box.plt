set datafile separator ","
set ylabel 'T(n) in ms'
set xlabel 'n'
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 2 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red
set style line 3 lc rgb '#2B823A' lt 1 lw 2 pt 3 ps 1.5   # --- green
set style line 4 lc rgb '#FFD700' lt 1 lw 2 pt 3 ps 1.5   # --- yellow
# i==1->std i==2->fibo i==3->bin
plot 'measure_complete_all.csv' using 1:20 with lines title 'alle Messwerte std' ls 1, \
          '' using 1:27 with lines title 'alle Messwerte fibo' ls 2, \
          '' using 1:34 with lines title 'alle Messwerte binary' ls 3, \
          '' using 1:6 with lines title 'theoretical T' ls 4