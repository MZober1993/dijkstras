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
import static util.MathHelper.scaleTimeValuesForPlot;
import static util.Measures.BOXPLOT;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         24.11.15 - 19:25
 */
public class BoxPlotFileWriter extends FileWriter {

    public static final String DIR = PATH_TO_RESOURCE + "BoxPlots/";
    public static final String PLAIN_FILE_NAME = DIR + BOXPLOT.name().toLowerCase();
    public static final String DEFAULT_FILE_NAME = PLAIN_FILE_NAME + ".csv";
    public static final Path DEFAULT_PATH = Paths.get(DEFAULT_FILE_NAME);
    private int aQuarter;
    private int aHalf;
    private int aThreeQuarter;

    public BoxPlotFileWriter(Path path) {
        super(path);
    }

    public void writeRoutine(List<Long> limits, Integer times, GraphImporter<Element> graphImporter, boolean scaledN) {
        aQuarter = (int) Math.ceil(times / 4.0);
        aHalf = (int) Math.ceil(times / 2.0);
        aThreeQuarter = aQuarter * 3;

        try {
            writeHeader(scaledN);
            boolean limitReached = false;

            for (Long limit : limits) {
                if (limitReached) {
                    break;
                }

                refreshMeasureBuilder();
                GraphImpl graph = graphImporter.importElementGraph(limit);
                int n = graph.getElements().size();
                int m = graph.getEdges().size();
                if (n < limit) {
                    limitReached = true;
                }
                tNWriteOfBoth(times, scaledN, graph, n, m);

                List<Long> stdMeasureList = stdBuilder.build().sorted().collect(Collectors.toList());
                List<Long> fiboMeasureList = fiboBuilder.build().sorted().collect(Collectors.toList());
                List<Long> binMeasureList = binaryBuilder.build().sorted().collect(Collectors.toList());

                BoxPlotMeasure binMeasure;
                BoxPlotMeasure fiboMeasure;
                BoxPlotMeasure stdMeasure;

                if (limit % 2 == 0) {
                    stdMeasure = newStraightBoxPlotMeasure(stdMeasureList);
                    fiboMeasure = newStraightBoxPlotMeasure(fiboMeasureList);
                    binMeasure = newStraightBoxPlotMeasure(binMeasureList);
                } else {
                    stdMeasure = newOddBoxPlotMeasure(stdMeasureList);
                    fiboMeasure = newOddBoxPlotMeasure(fiboMeasureList);
                    binMeasure = newOddBoxPlotMeasure(binMeasureList);
                }

                writeBoxPlot(stdMeasure, stdMeasureList);
                writeComma();
                writeBoxPlot(fiboMeasure, fiboMeasureList);
                writeComma();
                writeBoxPlot(binMeasure, fiboMeasureList);
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BoxPlotMeasure newStraightBoxPlotMeasure(List<Long> stdMeasureList) {
        return new BoxPlotMeasure(valueForStraights(stdMeasureList, aQuarter),
                valueForStraights(stdMeasureList, aHalf),
                valueForStraights(stdMeasureList, aThreeQuarter));
    }

    private BoxPlotMeasure newOddBoxPlotMeasure(List<Long> stdMeasureList) {
        return new BoxPlotMeasure(valueForOdds(stdMeasureList, aQuarter),
                valueForOdds(stdMeasureList, aHalf),
                valueForOdds(stdMeasureList, aThreeQuarter));
    }

    private void writeBoxPlot(BoxPlotMeasure measure, List<Long> measureList) throws IOException {
        double a = measure.getQuartile0_25() - 1.5 * (measure.getQuartile0_75() - measure.getQuartile0_25());
        double b = measure.getQuartile0_25() + 1.5 * (measure.getQuartile0_75() - measure.getQuartile0_25());
        double countBetween0_25And0_75 = measureList.stream()
                .filter(elem -> measure.getQuartile0_25() <= elem && elem <= measure.getQuartile0_75()).count();
        double countBetweenAAndB = measureList.stream()
                .filter(elem -> a <= elem && elem <= b).count();

        writeList(
                scaleTimeValuesForPlot(
                        Stream.of(
                                measure.getQuartile0_25()
                                , measure.getMedian()
                                , measure.getQuartile0_75()
                                , Math.max(measureList.get(0), a)
                                , Math.min(measureList.get(measureList.size() - 1), b)
                        ))
                        .collect(Collectors.toList()));
        writeComma();
        writeList(
                Stream.<Double>of(countBetween0_25And0_75
                        , countBetweenAAndB
                ).collect(Collectors.toList()));
    }

    private double valueForOdds(List<Long> measureList, int aQuarter) {
        return (double) measureList.get(aQuarter - 1);
    }

    private double valueForStraights(List<Long> measureList, int aQuarter) {
        Double under = Double.valueOf(measureList.get(aQuarter - 1));
        Double upper = Double.valueOf(measureList.get(aQuarter));
        return (under + upper) / 2.0;
    }

    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n) std,T(n) fibo,T(n) binary" +
                headerString("std") +
                headerString("fibo") +
                headerString("binary") +
                "\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }

    private String headerString(String newImplName) {
        return "," + newImplName + " Q_.25" +
                "," + newImplName + " Q_.5" +
                "," + newImplName + " Q_.75" +
                "," + newImplName + " a" +
                "," + newImplName + " b" +
                "," + newImplName + " |[Q_.25 Q_.75]|" +
                "," + newImplName + " |[a b]|";
    }
}
