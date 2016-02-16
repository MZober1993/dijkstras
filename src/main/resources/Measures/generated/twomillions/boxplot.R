setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
file_name_complete="plain_t_n_complete_all.csv"
table_complete<-read.csv(file_name_complete)

stdlabel='Laufzeitmessungen zum Dijkstra\n mit der Standard-Implementation der\n Prioritätswarteschlange in Java'
binlabel='Laufzeitmessungen zum Dijkstra\n mit einem Binären-Min-Heap\n als Prioritätswarteschlange'
fibolabel='Laufzeitmessungen zum Dijkstra\n mit einem Fibonacci-Heap\n als Prioritätswarteschlange'

ylabel='T(n) in ms'
xlabel='|E|'

file_name_planar="plain_t_n_planar_all.csv"
table_planar<-read.csv(file_name_planar)

png(file="boxplot_complete_std.png")
boxplot(table_complete$T.n..std~table_complete$E
        ,main=stdlabel, ylab=ylabel, xlab=xlabel
         ,ylim = c(0, 1000), yaxs = "i"
        )
dev.off()

png(file="boxplot_complete_fibo.png")
boxplot(table_complete$T.n..fibo~table_complete$E
        ,main=fibolabel
        ,ylab=ylabel
        ,xlab=xlabel
         ,ylim = c(0, 1000), yaxs = "i"
        )
dev.off()

png(file="boxplot_complete_binary.png")
boxplot(table_complete$T.n..binary~table_complete$E
        ,main=binlabel
        ,ylab=ylabel
        ,xlab=xlabel
         ,ylim = c(0,1000), yaxs = "i"
        )
dev.off()

png(file="boxplot_planar_std.png")
boxplot(table_planar$T.n..std~table_planar$E
        ,main=stdlabel
        ,ylab=ylabel
        ,xlab=xlabel
        ,ylim = c(0, 20), yaxs = "i")
dev.off()

png(file="boxplot_planar_fibo.png")
boxplot(table_planar$T.n..fibo~table_planar$E
        ,main=fibolabel
        ,ylab=ylabel
        ,xlab=xlabel
        ,ylim = c(0, 10), yaxs = "i")
dev.off()

png(file="boxplot_planar_binary.png")
boxplot(table_planar$T.n..binary~table_planar$E
        ,main=binlabel
        ,ylab=ylabel
        ,xlab=xlabel
        ,ylim = c(0, 10), yaxs = "i")
dev.off()
