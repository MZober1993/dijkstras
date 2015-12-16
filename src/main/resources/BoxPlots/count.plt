set datafile separator ","
set ylabel 'Anzahl der Messungen die im Bereich vorliegen'
set xlabel 'n * [1000]'
set yrange [0:50]
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 2 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red
set style line 3 lc rgb '#2B823A' lt 1 lw 2 pt 3 ps 1.5   # --- green
set style line 4 lc rgb '#FFD700' lt 1 lw 2 pt 3 ps 1.5   # --- yellow

plot sprintf('%s.csv',filename) using 1:9 with lines title '|q_025,q_075|' ls 2, \
     '' using 1:10 with lines title '|a b|' ls 3