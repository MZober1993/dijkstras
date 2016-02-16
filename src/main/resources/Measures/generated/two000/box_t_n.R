setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
bin_name="t_n_bin_complete.csv"
bin_c<-read.csv(bin_name)
std_name="t_n_std_complete.csv"
std_c<-read.csv(std_name)
fib_name="t_n_fib_complete.csv"
fib_c<-read.csv(fib_name)

stdlabel='Laufzeitmessungen zum Dijkstra\n mit der Standard-Implementation der\n Prioritätswarteschlange in Java'
binlabel='Laufzeitmessungen zum Dijkstra\n mit einem Binären-Min-Heap\n als Prioritätswarteschlange'
fibolabel='Laufzeitmessungen zum Dijkstra\n mit einem Fibonacci-Heap\n als Prioritätswarteschlange'

ylabel='T(n) in ms'
xlabel='|E|'
ymax=600

png(file="box_std_c.png")
boxplot(std_c$T.n..std~std_c$E
        ,main=stdlabel, ylab=ylabel, xlab=xlabel
        ,ylim = c(0, ymax), yaxs = "i"
)
dev.off()

png(file="box_fib_c.png")
boxplot(fib_c$T.n..fibo~fib_c$E
        ,main=fibolabel
        ,ylab=ylabel
        ,xlab=xlabel
        ,ylim = c(0, ymax), yaxs = "i"
)
dev.off()

png(file="box_bin_c.png")
boxplot(bin_c$T.n..binary~bin_c$E
        ,main=binlabel
        ,ylab=ylabel
        ,xlab=xlabel
        ,ylim = c(0,ymax), yaxs = "i"
)
dev.off()
