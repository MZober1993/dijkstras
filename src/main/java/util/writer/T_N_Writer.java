package util.writer;

import datastructure.Element;
import datastructure.standard.GraphImpl;
import util.GraphImporter;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static util.GraphImporter.PATH_TO_RESOURCE;
import static util.Measures.PLAIN_T_N;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 19.01.16 - 18:30
 */
public class T_N_Writer extends FileWriter {

    public static final String DIR = PATH_TO_RESOURCE + "Measures/";
    public static final String PLAIN_FILE_NAME = DIR + PLAIN_T_N.name().toLowerCase();
    public static final String DEFAULT_FILE_NAME = PLAIN_FILE_NAME + ".csv";
    public static final Path DEFAULT_FILE_PATH = Paths.get(DEFAULT_FILE_NAME);

    public T_N_Writer(Path path) {
        super(path);
    }

    public void writeRoutine(List<Long> limits, Integer times, GraphImporter<Element> graphImporter, boolean scaledN) {
        try {
            writeHeader(scaledN);
            boolean limitReached = false;
            for (Long limit : limits) {
                if (limitReached) {
                    break;
                }
                GraphImpl graph = graphImporter.importElementGraph(limit);
                int n = graph.getElements().size();
                int m = graph.getEdges().size();
                if (n < limit) {
                    limitReached = true;
                }
                refreshMeasureBuilder();
                tNWriteOfAll(times, scaledN, graph, n, m, 0);
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n) std,T(n) fibo,T(n) binary" +
                "\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
