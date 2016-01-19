setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
file_name="plain_t_n_complete_all.csv"
table<-read.csv(file_name)

png(file="boxplot_std.png")
boxplot(table$T.n..std~table$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit der Standard-Implementation der\n Prioritätswarteschlange in Java'
        ,ylab='T(n) in ms'
        ,xlab='|V|')
dev.off()

png(file="boxplot_fibo.png")
boxplot(table$T.n..fibo~table$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit einem Fibonacci-Heap\n als Prioritätswarteschlange'
        ,ylab='T(n) in ms'
        ,xlab='|V|')
dev.off()

png(file="boxplot_binary.png")
boxplot(table$T.n..binary~table$V
        ,main='Laufzeitmessungen zum Dijkstra\n mit einem Binären-Min-Heap\n als Prioritätswarteschlange'
        ,ylab='T(n) in ms'
        ,xlab='|V|')
dev.off()
