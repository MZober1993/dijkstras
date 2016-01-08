import algorithm.Dijkstra;
import datastructure.Element;
import datastructure.Graph;
import util.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static util.MeasureFileWriter.PLAIN_FILE_NAME;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         16.11.15 - 23:53
 */
public class MeasureAlgorithm<G extends Graph<T>, A extends Dijkstra<T>, T extends Element> {

    public static final int REPUTATIONS = 40;
    public final GraphImporter<T> NY_IMPORTER = new GraphImporter<>(ImportFile.NY);
    public final GraphImporter<T> CREATED_IMPORTER = new GraphImporter<>(ImportFile.CREATED);

    public MeasureAlgorithm() {
    }

    public MeasureAlgorithm(Long limit) {
        CREATED_IMPORTER.setLimit(limit);
    }

    private void measuresForNY() {
        //todo:fix me
        //Measures.prepareMeasure(NY_IMPORTER, new DijkstraImpl());
        expectStdErrorForNReputationsWithScaledN(NY_IMPORTER, 10L, 100L, 1000L);
        boxPlotForNReputationsWithScaledN(NY_IMPORTER, 10L, 100L, 1000L);
    }

    private static void boxPlotForNReputationsWithScaledN(GraphImporter importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> boxPlot(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(BoxPlotFileWriter.PLAIN_FILE_NAME, scale), true));
    }

    private static void boxPlotForNReputationsWithoutScaledN(GraphImporter importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> boxPlot(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(BoxPlotFileWriter.PLAIN_FILE_NAME, scale), false));
    }

    private static void boxPlot(List<Long> listOfN, GraphImporter importer, Path path, boolean scaledN) {
        BoxPlotFileWriter boxPlotFileWriter = new BoxPlotFileWriter(path);
        boxPlotFileWriter.writeRoutine(listOfN, REPUTATIONS, importer, scaledN);
    }

    private static void expectStdError(List<Long> listOfN, GraphImporter importer, Path path, boolean scaledN) {
        MeasureFileWriter measureFileWriter = new MeasureFileWriter(path);
        measureFileWriter.writeRoutine(listOfN, REPUTATIONS, importer, scaledN);
    }

    private static void expectStdErrorForNReputationsWithScaledN(GraphImporter importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> expectStdError(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(PLAIN_FILE_NAME, scale), true));
    }

    private static void expectStdErrorForNReputationsWithoutScaledN(GraphImporter importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> expectStdError(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(PLAIN_FILE_NAME, scale), false));
    }

    private static Path calcScaledPath(String plain, Long scale) {
        return Paths.get(plain + "_" + scale + ".csv");
    }
}
