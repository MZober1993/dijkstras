import datastructure.standard.StandardGraph;
import util.MemberFileWriter;

import static util.ImportFile.NY;
import static util.MemberFileWriter.MEMBER_FILE_PATH;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         21.11.15 - 09:44
 */
public class MaximumMemberOfN {

    public static void main(String[] args) {
        MemberFileWriter<StandardGraph> memberFileWriter = new MemberFileWriter(MEMBER_FILE_PATH);
        memberFileWriter.writeMaximumMemberOfNOccurrenceInShortestPath(NY, 100L, 1000L, 10000L);
    }
}
