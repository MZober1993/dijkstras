import util.GraphFileCreator;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         25.11.15 - 13:25
 */
public class CreateGraph {


    public static void main(String[] args) {
        GraphFileCreator graphFileCreator = new GraphFileCreator(GraphFileCreator.DEFAULT_PATH);
        graphFileCreator.createSampleGraphFile(13100);
        graphFileCreator.createCompleteConnectedGraphFile(GraphFileCreator.COMPLETE_LIMIT, 1);
        graphFileCreator.createPlanarGraphFile(GraphFileCreator.PLANAR_LIMIT, 1);
    }
}
