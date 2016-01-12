import datastructure.Element;
import util.GraphImporter;
import util.Measures;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm measure = new MeasureAlgorithm();
        long limit = 500L;
        int endBegin = 450;
        //GraphImporter<Element> importer = measure.CREATED_IMPORTER;
        GraphImporter<Element> importer = measure.COMPLETE_IMPORTER;

        Measures.prepareStd(importer.importElementGraph(limit), limit, endBegin);
        measure.completeRecordInOneFile();
    }
}
