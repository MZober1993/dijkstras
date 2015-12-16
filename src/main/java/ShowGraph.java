import util.GraphImporter;
import util.ImportFile;

import java.nio.file.Paths;

import static util.ImportFile.NY;
import static util.ImportFile.SAMPLE;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 16.11.15 - 13:48
 */
public class ShowGraph {

    public static void main(String[] args) {
        createAndShow(NY, 20L);
        createAndShow(SAMPLE, 20L);
    }

    private static void createAndShow(ImportFile file, Long limit) {
        System.out.println(Paths.get(file.name().toLowerCase()).toAbsolutePath().normalize().toString());
        GraphImporter graphImporterSample = new GraphImporter(file);
        System.out.println(graphImporterSample.importLinesOfFileAndGetSequentialGraph(limit));
    }
}
