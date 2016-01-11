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
                GraphImpl graph = graphImporter.importElementGraph(limit);
                int n = graph.getElements().size();
                int m = graph.getEdges().size();
                if (n < limit) {
                    limitReached = true;
                }
                refreshMeasureBuilder();
                tNWriteOfBoth(times, scaledN, graph, n, m);

                List<Long> stdMeasureList = stdBuilder.build().collect(Collectors.toList());
                double stdExp = calculateExpectancyValue(stdMeasureList, times);
                double stdStError = calculateStandardError(stdExp, expectancyWithXInPow(stdMeasureList, times));

                List<Long> fiboMeasureList = fiboBuilder.build().collect(Collectors.toList());
                double fiboExp = calculateExpectancyValue(fiboMeasureList, times);
                double fiboStError = calculateStandardError(fiboExp, expectancyWithXInPow(fiboMeasureList, times));

                List<Long> binMeasureList = binaryBuilder.build().collect(Collectors.toList());
                double binExp = calculateExpectancyValue(binMeasureList, times);
                double binStError = calculateStandardError(binExp, expectancyWithXInPow(binMeasureList, times));


                writeList(
                        scaleTimeValuesForPlot(
                                Stream.concat(
                                        Stream.concat(
                                                stdExpWithErrorStream(stdExp, stdStError)
                                                , stdExpWithErrorStream(fiboExp, fiboStError))
                                        , Stream.concat(
                                                stdExpWithErrorStream(binExp, binStError)
                                                , Stream.of(2 * n + 2 * n * Math.log(n) + m + m * n * Math.log(n) * 2))))
                                .collect(Collectors.toList()));
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stream<Double> stdExpWithErrorStream(double exp, double stError) {
        return Stream.of(exp, stError, exp - stError, exp + stError);
    }

    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n) std, T(n) fibo, T(n) binary" +
                ",Erwartungswert std" +
                ",Standardabweichung std" +
                ",Erw.-StdAbw. std" +
                ",Erw.+StdAbw. std" +
                ",Erwartungswert fibo" +
                ",Standardabweichung fibo" +
                ",Erw.-StdAbw. fibo" +
                ",Erw.+StdAbw. fibo" +
                ",Erwartungswert binary" +
                ",Standardabweichung binary" +
                ",Erw.-StdAbw. binary" +
                ",Erw.+StdAbw. binary" +
                ",theo. T(n)\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
