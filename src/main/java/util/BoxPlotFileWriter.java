package util;

import algorithm.standard.DijkstraImpl;
import datastructure.standard.GraphImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.GraphImporter.PATH_TO_RESOURCE;
import static util.MathHelper.A_MILLION;
import static util.MathHelper.scaleTimeValuesForPlot;
import static util.Measures.BOXPLOT;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 24.11.15 - 19:25
 */
public class BoxPlotFileWriter extends FileWriter {

    public static final String DIR = PATH_TO_RESOURCE + "BoxPlots/";
    public static final String PLAIN_FILE_NAME = DIR + BOXPLOT.name().toLowerCase();
    public static final String DEFAULT_FILE_NAME = PLAIN_FILE_NAME + ".csv";
    public static final Path DEFAULT_PATH = Paths.get(DEFAULT_FILE_NAME);

    public BoxPlotFileWriter(Path path) {
        super(path);
    }

    public void writeRoutine(List<Long> limits, Integer times, GraphImporter graphImporter, boolean scaledN) {
        DijkstraImpl algorithm = new DijkstraImpl();
        long time;
        int aQuarter = (int) Math.ceil(times / 4.0);
        int aHalf = (int) Math.ceil(times / 2.0);
        int aThreeQuarter = aQuarter * 3;

        try {
            writeHeader(scaledN);
            for (Long limit : limits) {
                Double quartile0_25;
                Double median;
                Double quartile0_75;

                Stream.Builder<Long> builder = Stream.builder();
                GraphImpl graph = graphImporter.importNVerticesAndGetSequentialGraph(limit);
                int numberOfVertices = graph.getVertices().size();
                int numberOfEdges = graph.getEdges().size();

                for (int i = 0; i < times - 1; i++) {
                    writeGraphNumbers(numberOfVertices, numberOfEdges, scaledN);
                    time = GraphHelper.calculateTimeWithLastRandom(graph, algorithm);
                    builder.add(time);
                    writeTimeWithScale(time, A_MILLION);
                    emptyEnd();
                }

                writeGraphNumbers(numberOfVertices, numberOfEdges, scaledN);
                time = GraphHelper.calculateTimeWithLastRandom(graph, algorithm);
                builder.add(time);
                writeTimeWithScale(time, A_MILLION);
                writeComma();

                List<Long> measureList = builder.build().sorted().collect(Collectors.toList());
                if (limit % 2 == 0) {
                    quartile0_25 = valueForStraights(measureList, aQuarter);
                    median = valueForStraights(measureList, aHalf);
                    quartile0_75 = valueForStraights(measureList, aThreeQuarter);

                } else {
                    quartile0_25 = valueForOdds(measureList, aQuarter);
                    median = valueForOdds(measureList, aHalf);
                    quartile0_75 = valueForOdds(measureList, aThreeQuarter);
                }

                double a = quartile0_25 - 1.5 * (quartile0_75 - quartile0_25);
                double b = quartile0_75 + 1.5 * (quartile0_75 - quartile0_25);
                double countBetween0_25And0_75 = measureList.stream()
                        .filter(elem -> quartile0_25 <= elem && elem <= quartile0_75).count();
                double countBetweenAAndB = measureList.stream()
                        .filter(elem -> a <= elem && elem <= b).count();

                writeList(
                        scaleTimeValuesForPlot(
                                Stream.of(quartile0_25
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
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double valueForOdds(List<Long> measureList, int aQuarter) {
        return (double) measureList.get(aQuarter - 1);
    }

    private double valueForStraights(List<Long> measureList, int aQuarter) {
        Double under = Double.valueOf(measureList.get(aQuarter - 1));
        Double upper = Double.valueOf(measureList.get(aQuarter));
        return (under + upper) / 2.0;
    }

    private void emptyEnd() throws IOException {
        writeComma();
        writeComma();
        writeComma();
        writeNewLine();
    }

    protected void writeHeader(boolean scaledN) throws IOException {
        String rest = ",T(n)" +
                ",Q_.25" +
                ",Q_.5" +
                ",Q_.75" +
                ",a" +
                ",b" +
                ",|[Q_.25 Q_.75]|" +
                ",|[a b]|\n";
        writeScaledGraphHeaderWithRest(scaledN, rest);
    }
}
