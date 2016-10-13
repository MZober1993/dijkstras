# Implementations of Dijkstra

> In this repository are three implementations of the dijkstra algorithm
> (Java-Dijkstra, Min-Heap-Dijkstra and Fibonacci-Heap-Dijkstra),
> which are used to find the shortest route from a source to a target in the given graph.

> The motivation of this work is the optimization 
> of a route-planner for the subject "algorithm engineering" 
> from the HTWK - Leipzig.

## Tested

The given algorithms are tested on real (usa: http://www.dis.uniroma1.it/challenge9/download.shtml) 
and generic data.

## Try it!

If you want you can use the Main to try all algorithms
(use -h to show the help - and all options).

### Generate Graphs

You can generate graph files like: -g p 500 (planar graph with 500 vertices)

### Measure the runtime of the algorithms
Measure the runtime for example with: 

|-m|  c|  fib|   200|
|---|---|---|---|
||graph   |algo|  limit|

(in a file called t_n_fib_complete.csv).

### Demonstrate shortest path

You can show a shortest path from a algorithm with a source and target with:

|-s| p|        500|     fib|     1|         200|
|---|---|---|---|---|---|
||graph|    limit|   algo|    source|    target|

*Result:*
(1)->(200),T(500):65.348878 ms
,Path:[1, 200]
