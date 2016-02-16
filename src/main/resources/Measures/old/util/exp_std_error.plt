set datafile separator ","
set ylabel 'T(n) in ms'
set xlabel 'n'
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 2 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red
set style line 3 lc rgb '#2B823A' lt 1 lw 2 pt 3 ps 1.5   # --- green
set style line 4 lc rgb '#FFD700' lt 1 lw 2 pt 3 ps 1.5   # --- yellow
# i==1->std i==2->fibo i==3->bin
plot 'measure_complete_all.csv' using 1:2+i with points title 'alle Messwerte' ls 1, \
     '' using 1:7+(4*(i-1)) with lines title 'Erwartungswert' ls 2, \
     '' using 1:8+(4*(i-1)) with lines title 'Standardabweichung' ls 3, \
     '' using 1:9+(4*(i-1)) with lines title 'Erw.-StdAbw.' ls 4, \
     '' using 1:10+(4*(i-1)) with lines title 'Erw.+StdAbw.' ls 4, \