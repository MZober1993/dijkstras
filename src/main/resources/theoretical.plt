set datafile separator ","
set ylabel 'T(n) in ms'
set xlabel 'n'
unset key

set style line 1 lc rgb '#0060ad' lt 1 lw 2 pt 5 ps 1.5   # --- blue
set style line 2 lc rgb '#dd181f' lt 1 lw 2 pt 5 ps 1.5   # --- red

plot 'theoretical.csv' using 1:2 with points title 'first' ls 1, \
     '' using 1:3 with lines title 'second' ls 2