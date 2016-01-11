import datastructure.Element;
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
public class MeasureAlgorithm {

    public static final int REPUTATIONS = 40;
    public final GraphImporter<Element> NY_IMPORTER = new GraphImporter<>(ImportFile.NY);
    public final GraphImporter<Element> CREATED_IMPORTER = new GraphImporter<>(ImportFile.CREATED);
    public final GraphImporter<Element> COMPLETE_IMPORTER = new GraphImporter<>(ImportFile.COMPLETE);

    public MeasureAlgorithm() {
    }

    public MeasureAlgorithm(Long limit) {
        NY_IMPORTER.setLimit(limit);
        CREATED_IMPORTER.setLimit(limit);
        COMPLETE_IMPORTER.setLimit(limit);
    }

    public void record(GraphImporter<Element> importer) {
        expectStdErrorForNReputationsWithScaledN(importer, 10L, 100L);
        boxPlotForNReputationsWithScaledN(importer, 1L, 10L, 100L, 100L);
    }

    private static void boxPlotForNReputationsWithScaledN(GraphImporter<Element> importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> boxPlot(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(BoxPlotFileWriter.PLAIN_FILE_NAME, scale), true));
    }

    private static void boxPlotForNReputationsWithoutScaledN(GraphImporter<Element> importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> boxPlot(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(BoxPlotFileWriter.PLAIN_FILE_NAME, scale), false));
    }

    private static void boxPlot(List<Long> listOfN, GraphImporter<Element> importer
            , Path path, boolean scaledN) {
        BoxPlotFileWriter boxPlotFileWriter = new BoxPlotFileWriter(path);
        boxPlotFileWriter.writeRoutine(listOfN, REPUTATIONS, importer, scaledN);
    }

    private static void expectStdError(List<Long> listOfN, GraphImporter<Element> importer, Path path, boolean scaledN) {
        MeasureFileWriter measureFileWriter = new MeasureFileWriter(path);
        measureFileWriter.writeRoutine(listOfN, REPUTATIONS, importer, scaledN);
    }

    private static void expectStdErrorForNReputationsWithScaledN(GraphImporter<Element> importer, Long... scales) {
        //Arrays.asList(scales).stream().forEach(scale -> System.out.println(Measures.scaleMeasureSample(scale)));
        Arrays.asList(scales).stream().forEach(scale -> expectStdError(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(PLAIN_FILE_NAME, scale), true));
    }

    private static void expectStdErrorForNReputationsWithoutScaledN(GraphImporter<Element> importer, Long... scales) {
        Arrays.asList(scales).stream().forEach(scale -> expectStdError(Measures.scaleMeasureSample(scale)
                , importer, calcScaledPath(PLAIN_FILE_NAME, scale), false));
    }

    private static Path calcScaledPath(String plain, Long scale) {
        return Paths.get(plain + "_" + scale + ".csv");
    }
}
