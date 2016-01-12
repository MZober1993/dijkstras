import datastructure.Element;
import util.GraphFileCreator;
import util.GraphImporter;
import util.ImportFile;
import util.Measures;
import util.writer.MeasureFileWriter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public void completeRecordInOneFile() {
        measure(Measures.measureLimits(GraphFileCreator.COMPLETE_LIMIT, 10, 2)
                , COMPLETE_IMPORTER, calcPath(MeasureFileWriter.PLAIN_FILE_NAME, "complete"), true);
    }

    private static void measure(List<Long> listOfN, GraphImporter<Element> importer, Path path, boolean scaledN) {
        MeasureFileWriter measureFileWriter = new MeasureFileWriter(path);
        measureFileWriter.writeRoutine(listOfN, REPUTATIONS, importer, scaledN);
    }

    private static Path calcPath(String plain, String mode) {
        return Paths.get(plain + "_" + mode + "_all.csv");
    }
}
