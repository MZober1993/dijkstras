package util.writer;

import algorithm.Dijkstra;
import algorithm.binary.DijkstraImplBinary;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.binary.GraphImplBinary;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.GraphImpl;
import util.MathHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static util.MathHelper.A_MILLION;
import static util.MathHelper.scaleTimeValuesForPlot;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         19.11.15 - 15:07
 */
public class FileWriter extends BasicFileWriter {


    protected Stream.Builder<Long> stdBuilder;
    protected Stream.Builder<Long> fiboBuilder;
    protected Stream.Builder<Long> binaryBuilder;

    public FileWriter(Path path) {
        super(path);
        refreshMeasureBuilder();
    }

    protected void writeGraphNumbers(int numberOfVertices, int numberOfEdges, boolean scaledN) throws IOException {
        if (scaledN) {
            writeDouble(MathHelper.scaleNForPlot(numberOfVertices));
            writeComma();
            writeDouble(MathHelper.scaleNForPlot(numberOfEdges));
            writeComma();
        } else {
            writeDouble(numberOfVertices);
            writeComma();
            writeDouble(numberOfEdges);
            writeComma();
        }
    }

    protected void tNWriteOfAll(Integer times, boolean scaledN, GraphImpl graph, int n, int m, int emptySpace)
            throws IOException {
        GraphImplFibo fiboGraph = GraphHelper.transformGraphToEntryGraph(graph);
        GraphImplBinary binaryGraph = GraphHelper.transformGraphToBinaryGraph(graph);
        DijkstraImpl stdAlgo = new DijkstraImpl();
        DijkstraImplFibo fiboAlgo = new DijkstraImplFibo();
        DijkstraImplBinary binAlgo = new DijkstraImplBinary();

        for (int i = 0; i < times - 1; i++) {
            writeGraphNumbers(n, m, scaledN);

            writingAndSaveTime(stdAlgo, stdBuilder, graph.refreshGraph());
            writeComma();

            writingAndSaveTime(fiboAlgo, fiboBuilder, fiboGraph.refreshGraph());
            writeComma();

            writingAndSaveTime(binAlgo, binaryBuilder, binaryGraph.refreshGraph());
            emptyEnd(emptySpace);
        }

        writeGraphNumbers(n, m, scaledN);

        writingAndSaveTime(stdAlgo, stdBuilder, graph);
        writeComma();

        writingAndSaveTime(fiboAlgo, fiboBuilder, fiboGraph.refreshGraph());
        writeComma();

        writingAndSaveTime(binAlgo, binaryBuilder, binaryGraph.refreshGraph());
    }

    protected void writeBoxPlot(BoxPlotMeasure measure, List<Long> measureList) throws IOException {
        writeList(
                scaleTimeValuesForPlot(
                        Stream.of(
                                measure.getAQuarter()
                                , measure.getMedian()
                                , measure.getAThreeQuarter()
                                , Math.max(measureList.get(0), measure.getA())
                                , Math.min(measureList.get(measureList.size() - 1), measure.getB())
                        ))
                        .collect(Collectors.toList()));
        writeComma();
        writeList(
                Stream.<Double>of(measure.getCountBetween0_25And0_75(measureList)
                        , measure.getCountBetweenAAndB(measureList)
                ).collect(Collectors.toList()));
    }

    protected BoxPlotMeasure createBoxPlotBasicLimit(Integer times) {
        return new BoxPlotMeasure(Math.ceil(times / 4.0), Math.ceil(times / 2.0), Math.ceil(times / 4.0) * 3);
    }

    protected BoxPlotMeasure newStraightBoxPlotMeasure(List<Long> measureList, BoxPlotMeasure basic) {
        return new BoxPlotMeasure(valueForStraights(measureList, basic.getAQuarter()),
                valueForStraights(measureList, basic.getMedian()),
                valueForStraights(measureList, basic.getAThreeQuarter()));
    }

    protected BoxPlotMeasure newOddBoxPlotMeasure(List<Long> measureList, BoxPlotMeasure basic) {
        return new BoxPlotMeasure(valueForOdds(measureList, basic.getAQuarter()),
                valueForOdds(measureList, basic.getMedian()),
                valueForOdds(measureList, basic.getAThreeQuarter()));
    }

    private double valueForOdds(List<Long> measureList, double aQuarter) {
        return (double) measureList.get((int) (aQuarter - 1.0));
    }

    private double valueForStraights(List<Long> measureList, double aQuarter) {
        Double under = Double.valueOf(measureList.get((int) (aQuarter - 1)));
        Double upper = Double.valueOf(measureList.get((int) aQuarter));
        return (under + upper) / 2.0;
    }

    protected Stream<Double> stdExpWithErrorStream(double exp, double stError) {
        return Stream.of(exp, stError, exp - stError, exp + stError);
    }

    protected void emptyEnd(int emptySpace) throws IOException {
        writeCommasNTimes(emptySpace);
        writeNewLine();
    }

    private void writeCommasNTimes(Integer n) {
        IntStream.range(0, n).forEach(x -> {
            try {
                writeComma();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    protected <T extends Element> void writingAndSaveTime(Dijkstra<T> algo, Stream.Builder<Long> builder
            , Graph<T> graph) throws IOException {
        long stdTime = GraphHelper.calculateTimeWithLastRandom(graph, algo);
        builder.add(stdTime);
        writeTimeWithScale(stdTime, A_MILLION);
    }

    protected void writeScaledGraphHeaderWithRest(boolean scaledN, String rest) throws IOException {
        if (scaledN) {
            Files.write(getPath(), ("V*[1000]" +
                    ",E*[1000]" +
                    rest)
                    .getBytes());
        } else {
            Files.write(getPath(), ("V" +
                    ",E" +
                    rest)
                    .getBytes());
        }
    }

    protected void refreshMeasureBuilder() {
        stdBuilder = Stream.builder();
        fiboBuilder = Stream.builder();
        binaryBuilder = Stream.builder();
    }
}
