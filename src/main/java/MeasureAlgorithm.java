import datastructure.Element;
import util.GraphFileCreator;
import util.GraphImporter;
import util.ImportFile;
import util.Measures;
import util.writer.MeasureFileWriter;
import util.writer.T_N_Writer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static util.writer.MeasureFileWriter.PLAIN_FILE_NAME;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         16.11.15 - 23:53
 */
public class MeasureAlgorithm {

    public static final int REPUTATIONS = 40;
    public static final List<Long> MEASURE_CONFIG = Measures.measureLimits(GraphFileCreator.COMPLETE_LIMIT, 10, 2);
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

    public void recordInOneFile() {
        MeasureFileWriter measureFileWriter = new MeasureFileWriter(calcPath(PLAIN_FILE_NAME, "complete"));
        measureFileWriter.writeRoutine(MEASURE_CONFIG, REPUTATIONS, COMPLETE_IMPORTER, false);
    }

    public void tNRecordInOneFile() {
        T_N_Writer t_n_writer = new T_N_Writer(calcPath(T_N_Writer.PLAIN_FILE_NAME, "complete"));
        t_n_writer.writeRoutine(MEASURE_CONFIG, REPUTATIONS, COMPLETE_IMPORTER, false);
    }

    private static Path calcPath(String plain, String mode) {
        return Paths.get(plain + "_" + mode + "_all.csv");
    }
}
