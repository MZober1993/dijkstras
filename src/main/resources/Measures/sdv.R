setwd("/home/matthias/dev/sequential-dijkstra/src/main/resources/Measures/")
file_name_complete="plain_t_n_complete_all.csv"
table_complete<-read.csv(file_name_complete)

file_name_planar="plain_t_n_planar_all.csv"
table_planar<-read.csv(file_name_planar)

c_std_mean=tapply(table_complete$T.n..std, list(E=table_complete$E), mean) 
c_std_sd=tapply(table_complete$T.n..std, list(E=table_complete$E), sd) 

c_binary_mean=tapply(table_complete$T.n..binary, list(E=table_complete$E), mean) 
c_binary_sd=tapply(table_complete$T.n..binary, list(E=table_complete$E), sd) 

c_fibo_mean=tapply(table_complete$T.n..fibo, list(E=table_complete$E), mean) 
c_fibo_sd=tapply(table_complete$T.n..fibo, list(E=table_complete$E), sd) 

c_combined_mean=cbind(c_std_mean,c_std_sd,c_binary_mean,c_binary_sd,c_fibo_mean,c_fibo_sd)
print(c_combined_mean)

p_std_mean=tapply(table_planar$T.n..std, list(E=table_planar$E), mean) 
p_std_sd=tapply(table_planar$T.n..std, list(E=table_planar$E), sd) 

p_binary_mean=tapply(table_planar$T.n..binary, list(E=table_planar$E), mean) 
p_binary_sd=tapply(table_planar$T.n..binary, list(E=table_planar$E), sd) 

p_fibo_mean=tapply(table_planar$T.n..fibo, list(E=table_planar$E), mean) 
p_fibo_sd=tapply(table_planar$T.n..fibo, list(E=table_planar$E), sd) 

p_combined_mean=cbind(p_std_mean,p_std_sd,p_binary_mean,p_binary_sd,p_fibo_mean,p_fibo_sd)
print(p_combined_mean)

plot(c_combined_mean)
