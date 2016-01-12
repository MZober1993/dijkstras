import util.writer.TheoreticalWriter;

import static util.writer.TheoreticalWriter.DEFAULT_PATH;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         26.11.15 - 20:25
 */
public class TheoreticalTn {

    public static void main(String[] args) {
        TheoreticalWriter theoreticalWriter = new TheoreticalWriter(DEFAULT_PATH);
        theoreticalWriter.writeRoutine();
    }
}
