set datafile separator ","
set ylabel 'T(n) in ms'
set xlabel 'n'
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 2 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red
set style line 3 lc rgb '#2B823A' lt 1 lw 2 pt 3 ps 1.5   # --- green
set style line 4 lc rgb '#FFD700' lt 1 lw 2 pt 3 ps 1.5   # --- yellow

plot 'boxplot_100.csv' using 1:3 with points title 'alle Messwerte' ls 1, \
     '' using 1:4 with lines title 'q_0.25' ls 2, \
     '' using 1:5 with lines title 'q_0.5' ls 3, \
     '' using 1:6 with lines title 'q_0.75' ls 2, \
     '' using 1:7 with lines title 'a' ls 4, \
     '' using 1:8 with lines title 'b' ls 4