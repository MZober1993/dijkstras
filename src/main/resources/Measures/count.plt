set datafile separator ","
if (i==0) set ylabel 'Anzahl der Messungen zwischen |q_025,q_075|'; else set ylabel 'Anzahl der Messungen zwischen |a b|'
set xlabel 'n'
set yrange [0:50]
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 2 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red
set style line 3 lc rgb '#2B823A' lt 1 lw 2 pt 3 ps 1.5   # --- green
set style line 4 lc rgb '#FFD700' lt 1 lw 2 pt 3 ps 1.5   # --- yellow
# i==0->|q_025,q_075|, i==1->|a b|
plot 'measure_complete_all.csv' using 1:24+i*1 with lines title 'std ' ls 1, \
     '' using 1:31+i*1 with lines title 'fibo ' ls 2, \
     '' using 1:38+i*1 with lines title 'binary ' ls 3