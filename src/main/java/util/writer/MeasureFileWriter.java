package util.writer;

import datastructure.Element;
import datastructure.standard.GraphImpl;
import util.GraphImporter;

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
        BoxPlotMeasure basic = createBoxPlotBasicLimit(times);

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
                tNWriteOfAll(times, scaledN, graph, n, m);
                writeDouble(scaleTimeValuesForPlot(2 * n + 2 * n * Math.log(n) + m + m * n * Math.log(n) * 2));
                writeComma();

                List<Long> stdMeasureList = stdBuilder.build().collect(Collectors.toList());
                List<Long> fiboMeasureList = fiboBuilder.build().collect(Collectors.toList());
                List<Long> binMeasureList = binaryBuilder.build().collect(Collectors.toList());
                writeAllExpStdErr(times, stdMeasureList, fiboMeasureList, binMeasureList);
                writeComma();
                writeAllBoxPlots(limit, basic, stdMeasureList, fiboMeasureList, binMeasureList);
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeAllBoxPlots(Long limit, BoxPlotMeasure basic, List<Long> stdMeasureList
            , List<Long> fiboMeasureList, List<Long> binMeasureList) throws IOException {
        BoxPlotMeasure binMeasure;
        BoxPlotMeasure fiboMeasure;
        BoxPlotMeasure stdMeasure;

        if (limit % 2 == 0) {
            stdMeasure = newStraightBoxPlotMeasure(stdMeasureList, basic);
            fiboMeasure = newStraightBoxPlotMeasure(fiboMeasureList, basic);
            binMeasure = newStraightBoxPlotMeasure(binMeasureList, basic);
        } else {
            stdMeasure = newOddBoxPlotMeasure(stdMeasureList, basic);
            fiboMeasure = newOddBoxPlotMeasure(fiboMeasureList, basic);
            binMeasure = newOddBoxPlotMeasure(binMeasureList, basic);
        }

        writeBoxPlot(stdMeasure, stdMeasureList);
        writeComma();
        writeBoxPlot(fiboMeasure, fiboMeasureList);
        writeComma();
        writeBoxPlot(binMeasure, binMeasureList);
    }

    protected void writeAllExpStdErr(Integer times, List<Long> stdMeasureList, List<Long> fiboMeasureList
            , List<Long> binMeasureList) throws IOException {
        double stdExp = calculateExpectancyValue(stdMeasureList, times);
        double stdStError = calculateStandardError(stdExp, expectancyWithXInPow(stdMeasureList, times));

        double fiboExp = calculateExpectancyValue(fiboMeasureList, times);
        double fiboStError = calculateStandardError(fiboExp, expectancyWithXInPow(fiboMeasureList, times));

        double binExp = calculateExpectancyValue(binMeasureList, times);
        double binStError = calculateStandardError(binExp, expectancyWithXInPow(binMeasureList, times));


        writeList(
                scaleTimeValuesForPlot(
                        Stream.concat(
                                Stream.concat(
                                        stdExpWithErrorStream(stdExp, stdStError)
                                        , stdExpWithErrorStream(fiboExp, fiboStError))
                                , stdExpWithErrorStream(binExp, binStError)
                        )).collect(Collectors.toList()));
    }


    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n) std,T(n) fibo,T(n) binary" +
                ",Theo. T(n)" +
                headerString("std") +
                headerString("fibo") +
                headerString("binary") +
                "\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }

    private String headerString(String newImplName) {
        return "," + newImplName + "Erwartungswert" +
                "," + newImplName + "Standardabweichung" +
                "," + newImplName + "Erw.-StdAbw." +
                "," + newImplName + "Erw.+StdAbw." +
                "," + newImplName + " Q_.25" +
                "," + newImplName + " Q_.5" +
                "," + newImplName + " Q_.75" +
                "," + newImplName + " a" +
                "," + newImplName + " b" +
                "," + newImplName + " |[Q_.25 Q_.75]|" +
                "," + newImplName + " |[a b]|";
    }
}
