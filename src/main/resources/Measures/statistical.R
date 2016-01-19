setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
file_name="plain_t_n_complete_all.csv"
table<-read.csv(file_name)

print(t.test(T.n..std,T.n..fibo,paired=TRUE))
print(t.test(T.n..fibo,T.n..binary,paired=TRUE))
print(t.test(T.n..binary,T.n..std,paired=TRUE))

print(pairwise.t.test(T.n..std,V,p.adjust.method = "holm"))
print(pairwise.t.test(T.n..fibo,V,p.adjust.method = "holm"))
print(pairwise.t.test(T.n..binary,V,p.adjust.method = "holm"))

print(aov(T.n..std~ V,data=table))
print(kruskal.test(T.n..std ~ V ,data = table))
print(aov(T.n..fibo~V,data=table))
print(kruskal.test(T.n..fibo~V,data = table))
print(aov(T.n..binary~V,data=table))
print(kruskal.test(T.n..binary~V,data = table))