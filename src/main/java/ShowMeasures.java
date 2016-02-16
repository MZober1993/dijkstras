import util.GraphFileCreator;
import util.Measures;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm measure = new MeasureAlgorithm();
        long limit = GraphFileCreator.COMPLETE_LIMIT;
        int endBegin = GraphFileCreator.COMPLETE_LIMIT - 10;
        //GraphImporter<Element> importer = measure.CREATED_IMPORTER;

       /* Measures.prepareStd(measure.COMPLETE_IMPORTER.importElementGraph(limit), limit, endBegin);
        measure.tNRecordInOneFile(measure.COMPLETE_IMPORTER, "complete");
        */
        Measures.prepareStd(measure.PLANAR_IMPORTER.importElementGraph(limit), limit, endBegin);
        measure.tNRecordInOneFile(measure.PLANAR_IMPORTER, "planar", MeasureAlgorithm.PLANAR_CONFIG);
        measure.tNRecordInOneFile(measure.COMPLETE_IMPORTER, "complete", MeasureAlgorithm.COMPLETE_CONFIG);
    }
}
