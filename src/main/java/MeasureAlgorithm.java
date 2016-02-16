import datastructure.Element;
import util.*;
import util.writer.MeasureFileWriter;
import util.writer.One_T_N_Writer;
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
    public static final List<Long> COMPLETE_CONFIG = Measures.measureLimits(GraphFileCreator.COMPLETE_LIMIT,
            GraphFileCreator.COMPLETE_LIMIT / 20, GraphFileCreator.COMPLETE_LIMIT / 20);
    public static final List<Long> PLANAR_CONFIG = Measures.measureLimits(GraphFileCreator.PLANAR_LIMIT,
            GraphFileCreator.PLANAR_LIMIT / 20, GraphFileCreator.PLANAR_LIMIT / 20);
    public final GraphImporter<Element> NY_IMPORTER = new GraphImporter<>(ImportFile.NY);
    public final GraphImporter<Element> CREATED_IMPORTER = new GraphImporter<>(ImportFile.CREATED);
    public final GraphImporter<Element> COMPLETE_IMPORTER = new GraphImporter<>(ImportFile.COMPLETE);
    public final GraphImporter<Element> PLANAR_IMPORTER = new GraphImporter<>(ImportFile.PLANAR);

    public MeasureAlgorithm() {
    }

    public MeasureAlgorithm(Long limit) {
        NY_IMPORTER.setLimit(limit);
        CREATED_IMPORTER.setLimit(limit);
        COMPLETE_IMPORTER.setLimit(limit);
    }

    public void recordInOneFile(GraphImporter<Element> importer, String sign, List<Long> config) {
        MeasureFileWriter measureFileWriter = new MeasureFileWriter(calcPath(PLAIN_FILE_NAME, sign));
        measureFileWriter.writeRoutine(config, REPUTATIONS, importer, false);
    }

    public void tNRecordInOneFile(GraphImporter<Element> importer, String sign, List<Long> config) {
        T_N_Writer t_n_writer = new T_N_Writer(calcPath(T_N_Writer.PLAIN_FILE_NAME, sign));
        t_n_writer.writeRoutine(config, REPUTATIONS, importer, false);
    }

    public void tNRecordOneMeasureInOneFile(GraphImporter<Element> importer, AlgoFlag flag, String sign
            , List<Long> config) {
        Path path = calcPath(One_T_N_Writer.PLAIN_ONE_FILE_NAME, flag, sign);
        One_T_N_Writer t_n_writer = new One_T_N_Writer(path);
        t_n_writer.writeRoutine(config, flag, REPUTATIONS, importer, false);
        System.out.println("measure-file created under:" + path.toString());
    }

    private static Path calcPath(String plain, String mode) {
        return Paths.get(plain + "_" + mode + ".csv");
    }

    private static Path calcPath(String plain, AlgoFlag flag, String mode) {
        return Paths.get(plain + "_" + flag.toString().toLowerCase() + "_" + mode + ".csv");
    }
}
