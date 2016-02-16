package util.writer;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import util.AlgoFlag;
import util.GraphImporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static util.GraphImporter.PATH_TO_RESOURCE;
import static util.Measures.PLAIN_ONE_T_N;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 19.01.16 - 18:30
 */
public class One_T_N_Writer extends FileWriter {

    public static final String DIR = PATH_TO_RESOURCE + "Measures/";
    public static final String PLAIN_ONE_FILE_NAME = DIR + PLAIN_ONE_T_N.name().toLowerCase();
    public static final String DEFAULT_FILE_NAME = PLAIN_ONE_FILE_NAME + ".csv";
    public static final Path DEFAULT_FILE_PATH = Paths.get(DEFAULT_FILE_NAME);

    public One_T_N_Writer(Path path) {
        super(path);
    }

    public <T extends Element> void writeRoutine(List<Long> limits, AlgoFlag flag, Integer times
            , GraphImporter<Element> graphImporter, boolean scaledN) {
        try {
            writeHeader(scaledN, flag);
            boolean limitReached = false;
            for (Long limit : limits) {
                if (limitReached) {
                    break;
                }
                Graph<T, ? extends Edge<T>> graph = graphImporter.importGraph(flag, limit);
                int n = graph.getVs().size();
                int m = graph.getEdgeSize();
                if (n < limit) {
                    limitReached = true;
                }
                Stream.Builder<Long> builder = Stream.builder();
                tNWriteOfOne(times, builder, scaledN, graph, graphImporter.convertAlgo(flag), n, m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T extends Element> void tNWriteOfOne(Integer times, Stream.Builder<Long> builder
            , boolean scaledN, Graph<T, ? extends Edge<T>> graph, Dijkstra<T> algo, int n, int m)
            throws IOException {
        for (int i = 0; i < times; i++) {
            writeGraphNumbers(n, m, scaledN);

            writingAndSaveTime(algo, builder, graph.refreshGraph());
            writeNewLine();
        }
    }

    protected void writeHeader(boolean scaledN, AlgoFlag flag) throws IOException {
        String rest = "";
        switch (flag) {
            case STD:
                rest = ",T(n) std";
                break;
            case BIN:
                rest = ",T(n) binary";
                break;
            case FIB:
                rest = ",T(n) fibo";
                break;
        }
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
