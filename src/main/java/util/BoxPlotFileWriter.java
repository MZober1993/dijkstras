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

    public BoxPlotFileWriter(Path path) {
        super(path);
    }

    public void writeRoutine(List<Long> limits, Integer times, GraphImporter<Element> graphImporter, boolean scaledN) {
        int aQuarter = (int) Math.ceil(times / 4.0);
        int aHalf = (int) Math.ceil(times / 2.0);
        int aThreeQuarter = aQuarter * 3;

        try {
            writeHeader(scaledN);
            boolean limitReached = false;

            for (Long limit : limits) {
                if (limitReached) {
                    break;
                }
                Double stdQuartile0_25;
                Double stdMedian;
                Double stdQuartile0_75;

                Double fiboQuartile0_25;
                Double fiboMedian;
                Double fiboQuartile0_75;

                Stream.Builder<Long> stdBuilder = Stream.builder();
                Stream.Builder<Long> fiboBuilder = Stream.builder();
                GraphImpl graph = graphImporter.importElementGraph(limit);
                int n = graph.getElements().size();
                int m = graph.getEdges().size();
                if (n < limit) {
                    limitReached = true;
                }
                tNWriteOfBoth(times, scaledN, stdBuilder, fiboBuilder, graph, n, m);

                List<Long> stdMeasureList = stdBuilder.build().sorted().collect(Collectors.toList());
                List<Long> fiboMeasureList = fiboBuilder.build().sorted().collect(Collectors.toList());
                if (limit % 2 == 0) {
                    stdQuartile0_25 = valueForStraights(stdMeasureList, aQuarter);
                    stdMedian = valueForStraights(stdMeasureList, aHalf);
                    stdQuartile0_75 = valueForStraights(stdMeasureList, aThreeQuarter);

                    fiboQuartile0_25 = valueForStraights(fiboMeasureList, aQuarter);
                    fiboMedian = valueForStraights(fiboMeasureList, aHalf);
                    fiboQuartile0_75 = valueForStraights(fiboMeasureList, aThreeQuarter);

                } else {
                    stdQuartile0_25 = valueForOdds(stdMeasureList, aQuarter);
                    stdMedian = valueForOdds(stdMeasureList, aHalf);
                    stdQuartile0_75 = valueForOdds(stdMeasureList, aThreeQuarter);

                    fiboQuartile0_25 = valueForOdds(fiboMeasureList, aQuarter);
                    fiboMedian = valueForOdds(fiboMeasureList, aHalf);
                    fiboQuartile0_75 = valueForOdds(fiboMeasureList, aThreeQuarter);
                }

                writeBoxPlot(stdQuartile0_25, stdMedian, stdQuartile0_75, stdMeasureList);
                writeComma();
                writeBoxPlot(fiboQuartile0_25, fiboMedian, fiboQuartile0_75, fiboMeasureList);
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBoxPlot(Double quartile0_25, Double median, Double quartile0_75, List<Long> measureList) throws IOException {
        double a = quartile0_25 - 1.5 * (quartile0_75 - quartile0_25);
        double b = quartile0_25 + 1.5 * (quartile0_75 - quartile0_25);
        double countBetween0_25And0_75 = measureList.stream()
                .filter(elem -> quartile0_25 <= elem && elem <= quartile0_75).count();
        double countBetweenAAndB = measureList.stream()
                .filter(elem -> a <= elem && elem <= b).count();

        writeList(
                scaleTimeValuesForPlot(
                        Stream.of(
                                quartile0_25
                                , median
                                , quartile0_75
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
        String rest = ",T(n) std,T(n) fibo" +
                ",std Q_.25" +
                ",std Q_.5" +
                ",std Q_.75" +
                ",std a" +
                ",std b" +
                ",std |[Q_.25 Q_.75]|" +
                ",std |[a b]|" +
                ",fibo Q_.25" +
                ",fibo Q_.5" +
                ",fibo Q_.75" +
                ",fibo a" +
                ",fibo b" +
                ",fibo |[Q_.25 Q_.75]|" +
                ",fibo |[a b]|" +
                "\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
