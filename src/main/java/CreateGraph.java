import util.GraphFileCreator;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 25.11.15 - 13:25
 */
public class CreateGraph {

    public static void main(String[] args) {
        GraphFileCreator graphFileCreator = new GraphFileCreator(GraphFileCreator.DEFAULT_PATH);
        graphFileCreator.createSampleGraphFile();
    }
}
