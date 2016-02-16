setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
fib_name="t_n_fib_planar.csv"
fib_p<-read.csv(fib_name)

fibolabel='Laufzeitmessungen zum Dijkstra\n mit einem Fibonacci-Heap\n als Prioritätswarteschlange'

ylabel='T(n) in ms'
xlabel='|E|'
ymax=600

png(file="box_fib_p.png")
boxplot(fib_p$T.n..fibo~fib_p$E
        ,main=binlabel
        ,ylab=ylabel
        ,xlab=xlabel
        #,ylim = c(0,ymax), yaxs = "i"
)
dev.off()
