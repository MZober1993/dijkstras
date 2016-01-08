package util;

import algorithm.standard.DijkstraImpl;
import datastructure.Element;
import datastructure.GraphHelper;
import datastructure.standard.GraphImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.GraphImporter.PATH_TO_RESOURCE;
import static util.MathHelper.*;
import static util.Measures.MEASURE;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         21.11.15 - 13:47
 */
public class MeasureFileWriter extends FileWriter {

    public static final String DIR = PATH_TO_RESOURCE + "Measures/";
    public static final String PLAIN_FILE_NAME = DIR + MEASURE.name().toLowerCase();
    public static final String DEFAULT_FILE_NAME = PLAIN_FILE_NAME + ".csv";
    public static final Path DEFAULT_FILE_PATH = Paths.get(DEFAULT_FILE_NAME);

    public MeasureFileWriter(Path path) {
        super(path);
    }

    public void writeRoutine(List<Long> limits, Integer times, GraphImporter<Element> graphImporter, boolean scaledN) {
        DijkstraImpl algorithm = new DijkstraImpl();
        long time;

        try {
            writeHeader(scaledN);
            for (Long limit : limits) {
                Stream.Builder<Long> builder = Stream.builder();
                GraphImpl graph = graphImporter.importNVerticesAndGetGraph(limit, new GraphImpl());
                int n = graph.getElements().size();
                int m = graph.getEdges().size();

                for (int i = 0; i < times - 1; i++) {
                    writeGraphNumbers(n, m, scaledN);
                    time = GraphHelper.calculateTimeWithLastRandom(graph, algorithm);
                    builder.add(time);
                    writeTimeWithScale(time, A_MILLION);
                    emptyEnd();
                }

                writeGraphNumbers(n, m, scaledN);
                time = GraphHelper.calculateTimeWithLastRandom(graph, algorithm);
                builder.add(time);
                writeTimeWithScale(time, A_MILLION);
                writeComma();

                List<Long> measureList = builder.build().collect(Collectors.toList());
                double expectancy = calculateExpectancyValue(measureList, times);
                double standardError = calculateStandardError(expectancy, expectancyWithXInPow(measureList, times));
                writeList(
                        scaleTimeValuesForPlot(
                                Stream.of(expectancy
                                        , standardError
                                        , expectancy - standardError
                                        , expectancy + standardError
                                        , 2 * n + 2 * n * Math.log(n) + m + m * n * Math.log(n) * 2
                                ))
                                .collect(Collectors.toList()));
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void emptyEnd() throws IOException {
        writeComma();
        writeComma();
        writeComma();
        writeNewLine();
    }

    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n)" +
                ",Erwartungswert" +
                ",Standardabweichung" +
                ",Erw.-StdAbw." +
                ",Erw.+StdAbw." +
                ",theo. T(n)\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
