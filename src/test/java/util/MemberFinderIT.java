package util;

import algorithm.Dijkstra;
import algorithm.standard.DijkstraImpl;
import datastructure.Vertex;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static util.ImportFile.NY;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         19.11.15 - 17:31
 */
public class MemberFinderIT {

    public static final long MAX_N = 2000000000;
    public static final long THOUSAND_OCCURRENCE = 1000000;
    Dijkstra dijkstra;
    MemberFinder memberFinder;

    @Before
    public void setUp() {
        dijkstra = new DijkstraImpl();
        memberFinder = new MemberFinder();
    }

    @Test
    public void testFindMemberOfNyWithHundredOccurrence() {
        findMemberOfPlaceWithNOccurrence(NY, 100L);
    }

    @Test
    public void testFindMemberOfNyWithHundredThousandOccurrence() {
        findMemberOfPlaceWithNOccurrence(NY, THOUSAND_OCCURRENCE);
    }

    @Test
    public void testFindMemberOfNyWithMaxNOccurrence() {
        findMemberOfPlaceWithNOccurrence(NY, MAX_N);
    }

    private void findMemberOfPlaceWithNOccurrence(ImportFile file, Long n) {
        GraphImporter<GraphImpl> graphImporter = new GraphImporter<>(file);
        GraphImpl graph = graphImporter.importNVerticesAndGetSequentialGraph(n);
        DijkstraImpl algorithm = new DijkstraImpl();

        List<Integer> member = memberFinder.findMemberForHighestShortestPathFromTheBeginningOfTheGraph(algorithm, graph);
        Vertex first = graph.getElement(member.get(0));
        Vertex second = graph.getElement(member.get(1));
        List<Integer> shortestPath = algorithm.shortestPath(graph, first, second);

        assertFalse(shortestPath.isEmpty());
        printGraphFacts(member, shortestPath);
    }

    private void printGraphFacts(List<Integer> member, List<Integer> shortestPath) {
        System.out.println("Start: " + member.get(0) + "- End: " + member.get(1));
        System.out.println("Size: " + shortestPath.size());
    }
}
