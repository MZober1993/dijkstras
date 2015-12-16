package util;

import algorithm.standard.DijkstraImpl;
import datastructure.standard.StandardGraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static util.Measures.MEMBER;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         21.11.15 - 13:54
 */
public class MemberFileWriter extends FileWriter {
    public static final String MEMBER_FILE = GraphImporter.PATH_TO_RESOURCE + MEMBER.name().toLowerCase();
    public static final Path MEMBER_FILE_PATH = Paths.get(MEMBER_FILE);

    public MemberFileWriter(Path path) {
        super(path);
    }

    public void writeMaximumMemberOfNOccurrenceInShortestPath(ImportFile file, Long... n) {
        MemberFinder memberFinder = new MemberFinder();
        GraphImporter graphImporter = new GraphImporter(file);
        DijkstraImpl algorithm = new DijkstraImpl();

        try {
            writeHeader();
            for (StandardGraph graph : graphImporter.calculateParallelGraphsWithNVertices(n)) {
                writeMember(memberFinder.findMemberForHighestShortestPathFromTheBeginningOfTheGraph(algorithm, graph)
                        , graph.getVertices().size());
                writeNewLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHeader() throws IOException {
        Files.write(MEMBER_FILE_PATH, ("\"n\",\"start\",\"end\"\n").getBytes());
    }

    public void writeMember(List<Integer> member, Integer size) throws IOException {
        writeList(Stream.of(size, member.get(0), member.get(1)).collect(Collectors.toList()));
    }
}
