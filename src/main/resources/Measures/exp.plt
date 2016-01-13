set datafile separator ","
set ylabel 'T(n) in ms'
set xlabel 'n (|V|)'
# set xrange [0:140]
# set yrange [0:120]
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 5 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red
set style line 3 lc rgb '#2B823A' lt 1 lw 2 pt 5 ps 1.5   # --- green
set style line 4 lc rgb '#FFD700' lt 1 lw 2 pt 5 ps 1.5   # --- yellow

plot 'measure_complete_all.csv' using 1:7 with lines title 'alle Messwerte std' ls 1, \
     '' using 1:11 with lines title 'alle Messwerte fibo' ls 2, \
     '' using 1:15 with lines title 'alle Messwerte binary' ls 3, \
     '' using 1:6 with lines title 'theoretical T' ls 4, \