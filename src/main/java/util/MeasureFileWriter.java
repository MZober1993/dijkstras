package util;

import datastructure.Element;
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

        try {
            writeHeader(scaledN);
            boolean limitReached = false;
            for (Long limit : limits) {
                if (limitReached) {
                    break;
                }
                Stream.Builder<Long> stdBuilder = Stream.builder();
                Stream.Builder<Long> fiboBuilder = Stream.builder();
                GraphImpl graph = graphImporter.importElementGraph(limit);
                int n = graph.getElements().size();
                int m = graph.getEdges().size();
                if (n < limit) {
                    limitReached = true;
                }
                tNWriteOfBoth(times, scaledN, stdBuilder, fiboBuilder, graph, n, m);

                List<Long> stdMeasureList = stdBuilder.build().collect(Collectors.toList());
                double stdExp = calculateExpectancyValue(stdMeasureList, times);
                double stdStError = calculateStandardError(stdExp, expectancyWithXInPow(stdMeasureList, times));

                List<Long> fiboMeasureList = fiboBuilder.build().collect(Collectors.toList());
                double fiboExp = calculateExpectancyValue(fiboMeasureList, times);
                double fiboStError = calculateStandardError(stdExp, expectancyWithXInPow(fiboMeasureList, times));

                writeList(
                        scaleTimeValuesForPlot(
                                Stream.of(stdExp
                                        , stdStError
                                        , stdExp - stdStError
                                        , stdExp + stdStError
                                        , fiboExp
                                        , fiboStError
                                        , fiboExp - fiboStError
                                        , fiboExp + fiboStError
                                        , 2 * n + 2 * n * Math.log(n) + m + m * n * Math.log(n) * 2
                                ))
                                .collect(Collectors.toList()));
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n) std, T(n) fibo" +
                ",Erwartungswert std" +
                ",Standardabweichung std" +
                ",Erw.-StdAbw. std" +
                ",Erw.+StdAbw. std" +
                ",Erwartungswert fibo" +
                ",Standardabweichung fibo" +
                ",Erw.-StdAbw. fibo" +
                ",Erw.+StdAbw. fibo" +
                ",theo. T(n)\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
