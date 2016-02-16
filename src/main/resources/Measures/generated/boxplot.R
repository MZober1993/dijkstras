setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
file_name_complete="plain_t_n_complete_all.csv"
table_complete<-read.csv(file_name_complete)

file_name_planar="plain_t_n_planar_all.csv"
table_planar<-read.csv(file_name_planar)

png(file="boxplot_complete_std.png")
boxplot(table_complete$T.n..std~table_complete$V,
        ,main='Laufzeitmessungen zum Dijkstra\n mit der Standard-Implementation der\n Prioritätswarteschlange in Java'
        ,ylab='T(n) in ms'
        ,xlab='|V|'
        ,ylim = c(0, 80), yaxs = "i")
dev.off()

png(file="boxplot_complete_fibo.png")
boxplot(table_complete$T.n..fibo~table_complete$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit einem Fibonacci-Heap\n als Prioritätswarteschlange'
        ,ylab='T(n) in ms'
        ,xlab='|V|'
        ,ylim = c(0, 80), yaxs = "i")
dev.off()

png(file="boxplot_complete_binary.png")
boxplot(table_complete$T.n..binary~table_complete$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit einem Binären-Min-Heap\n als Prioritätswarteschlange'
        ,ylab='T(n) in ms'
        ,xlab='|V|'
        ,ylim = c(0, 80), yaxs = "i")
dev.off()

png(file="boxplot_planar_std.png")
boxplot(table_planar$T.n..std~table_planar$V,
        ,main='Laufzeitmessungen zum Dijkstra\n mit der Standard-Implementation der\n Prioritätswarteschlange in Java'
        ,ylab='T(n) in ms'
        ,xlab='|V|'
        ,ylim = c(0, 10), yaxs = "i")
dev.off()

png(file="boxplot_planar_fibo.png")
boxplot(table_planar$T.n..fibo~table_planar$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit einem Fibonacci-Heap\n als Prioritätswarteschlange'
        ,ylab='T(n) in ms'
        ,xlab='|V|'
        ,ylim = c(0, 10), yaxs = "i")
dev.off()

png(file="boxplot_planar_binary.png")
boxplot(table_planar$T.n..binary~table_planar$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit einem Binären-Min-Heap\n als Prioritätswarteschlange'
        ,ylab='T(n) in ms'
        ,xlab='|V|'
        ,ylim = c(0, 10), yaxs = "i")
dev.off()
