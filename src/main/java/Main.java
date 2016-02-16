import algorithm.Dijkstra;
import algorithm.binary.DijkstraImplBinary;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import datastructure.*;
import util.GraphFileCreator;
import util.GraphImporter;
import util.ImportFile;
import util.Measures;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static util.MathHelper.A_MILLION;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.02.16 - 11:41
 */
public class Main {
    public static void main(String[] args) {
        for (int currentArgument = 0; currentArgument < args.length; currentArgument++) {
            switch (args[currentArgument]) {
                case "-g":
                    currentArgument = generateGraph(args, currentArgument);
                    break;
                case "-s":
                    currentArgument = showShortestPathWithCalcTime(args, currentArgument);
                    break;
                case "-p":
                    currentArgument = prepareMeasure(args, currentArgument);
                    break;
                case "-m":
                    currentArgument = measure(args, currentArgument);
                    break;
                case "-h":
                    printHelp();
                    break;
                default:
                    printHelp();
            }
        }
    }

    private static int generateGraph(String[] args, int currentArgument) {
        GraphFileCreator graphFileCreator = new GraphFileCreator(GraphFileCreator.DEFAULT_PATH);
        int limit;
        switch (args[currentArgument + 1]) {
            case "c":
                limit = printGenerateInputAndParseSize(args, currentArgument);
                graphFileCreator.createCompleteConnectedGraphFile(limit, 1);
                currentArgument = currentArgument + 2;
                break;
            case "p":
                limit = printGenerateInputAndParseSize(args, currentArgument);
                graphFileCreator.createPlanarGraphFile(limit, 1);
                currentArgument = currentArgument + 2;
                break;
        }
        return currentArgument;
    }

    private static int showShortestPathWithCalcTime(String[] args, int currentArgument) {
        GraphImporter<Element> importer;
        switch (args[currentArgument + 1]) {
            case "c":
                importer = new GraphImporter<>(ImportFile.COMPLETE);
                chooseAlgo(args, currentArgument, importer);
                currentArgument = currentArgument + 5;
                break;
            case "p":
                importer = new GraphImporter<>(ImportFile.COMPLETE);
                chooseAlgo(args, currentArgument, importer);
                currentArgument = currentArgument + 5;
                break;
        }
        return currentArgument;
    }

    private static int measure(String[] args, int currentArgument) {
        return 0;
    }

    private static void chooseAlgo(String[] args, int currentArgument, GraphImporter<Element> importer) {
        long limit;
        int start;
        int target;
        Triple<Long, Integer, Integer> parsedArgs = printShowInputAndParseNumbers(args, currentArgument);
        limit = parsedArgs.getFirst();
        start = parsedArgs.getSecond();
        target = parsedArgs.getThird();
        switch (args[currentArgument + 3]) {
            case "std":
                printDijkstra(new DijkstraImpl()
                        , importer.importElementGraph(limit), limit, start, target);
                break;
            case "bin":
                printDijkstra(new DijkstraImplBinary(), importer.importBinaryGraph(limit), limit, start, target);
                break;
            case "fib":
                printDijkstra(new DijkstraImplFibo(), importer.importEntryGraph(limit), limit, start, target);
                break;
        }
    }

    private static <G extends Graph<T, ? extends Edge>, T extends Element> void printDijkstra(Dijkstra<T> dijkstra,
                                                                                              G graph, Long limit, Integer start, Integer target) {
        graph.refreshGraph();
        Pair<Long, List<Integer>> pair = GraphHelper.calculateTimeAndPath(graph, dijkstra, graph.getV(start), graph.getV(target));
        System.out.println("(" + start + ")->(" + target + ")" + ",T(" + limit + "):" + pair.getFirst() / A_MILLION + " ms\n" +
                ",Path:" + pair.getSecond());
    }


    private static int prepareMeasure(String[] args, int currentArgument) {
        MeasureAlgorithm measure = new MeasureAlgorithm();
        Pair<Long, Integer> parsedArgs = printPrepareAndParseNumbers(args, currentArgument);
        switch (args[currentArgument + 1]) {
            case "c":
                Measures.prepareStd(measure.COMPLETE_IMPORTER.importElementGraph(parsedArgs.getFirst())
                        , parsedArgs.getFirst(), parsedArgs.getSecond());
                break;
            case "p":
                Measures.prepareStd(measure.PLANAR_IMPORTER.importElementGraph(parsedArgs.getFirst())
                        , parsedArgs.getFirst(), parsedArgs.getSecond());
                break;
        }
        return currentArgument + 3;
    }

    private static int printGenerateInputAndParseSize(String[] args, int currentArgument) {
        checkNotNull(args[currentArgument]);
        checkNotNull(args[currentArgument + 1]);
        checkNotNull(args[currentArgument + 2]);
        System.out.println("generate a graph with following flag:" + args[currentArgument + 1] +
                ",\nand following count of vertices:" + args[currentArgument + 2]);
        return Integer.parseInt(args[currentArgument + 2]);
    }

    private static Triple<Long, Integer, Integer> printShowInputAndParseNumbers(String[] args, int currentArgument) {
        checkNotNull(args[currentArgument]);
        checkNotNull(args[currentArgument + 1]);
        checkNotNull(args[currentArgument + 2]);
        checkNotNull(args[currentArgument + 3]);
        checkNotNull(args[currentArgument + 4]);
        checkNotNull(args[currentArgument + 5]);
        System.out.println("show a shortest-path from the following graph:" + args[currentArgument + 1] +
                ",\nand following count of vertices:" + args[currentArgument + 2] +
                ",\nwith the algorithm:" + args[currentArgument + 3] +
                ",\nwith the start-vertex:" + args[currentArgument + 4] +
                "\nand with the target-vertex:" + args[currentArgument + 5]);
        return new Triple<>(Long.parseLong(args[currentArgument + 2])
                , Integer.parseInt(args[currentArgument + 4])
                , Integer.parseInt(args[currentArgument + 5]));
    }

    private static Pair<Long, Integer> printPrepareAndParseNumbers(String[] args, int currentArgument) {
        checkNotNull(args[currentArgument]);
        checkNotNull(args[currentArgument + 1]);
        checkNotNull(args[currentArgument + 2]);
        checkNotNull(args[currentArgument + 3]);
        System.out.println("prepare a measure of following graph:" + args[currentArgument + 1] +
                ",\nwith following count of vertices:" + args[currentArgument + 2] +
                "\nand with the target-vertex:" + args[currentArgument + 3]);
        return new Pair<>(Long.parseLong(args[currentArgument + 2])
                , Integer.parseInt(args[currentArgument + 3]));
    }

    private static void printHelp() {
        System.out.println("Options:\n\t" +
                "-g graph size:" +
                "\n\t\t[generates a graph-file in src/main/resources/ImportFiles]" +
                "\n\t\tthe graph could be 'c' for a complete or 'p' for a planar graph," +
                "\n\t\tthe size is the number of vertices in the graph must be a number (int)" +
                "\n\t-p graph size target_id: " +
                "\n\t\t[prepare a measure with the calculation of the shortest-paths" +
                "\n\t\tfrom the start until the target until the limit the target_id to the limit of the graph]" +
                "\n\t\tthe graph shows the kind of graph, like 'c' for complete and 'p' for planar" +
                "\n\t\tthe size is the number of vertices in the graph and must be a number (int)" +
                "\n\t\tthe target_id is the id of the target for our shortest-path-preparation" +
                "\n\t-s graph size algo start-id target-id: " +
                "\n\t\t[show the shortest-path with its calculation time]" +
                "\n\t\tthe graph shows the kind of graph, like 'c' for complete and 'p' for planar" +
                "\n\t\tthe size is the number of vertices in the graph and must be a number (int)" +
                "\n\t\tthe algorithm to calculate the shortest-path: 'std','bin' or 'fib'" +
                "\n\t\tthe vertex with the start-id must be a vertex of the marked graph" +
                "\n\t\tthe vertex with the target-id must be a vertex of the marked graph");
    }
}
