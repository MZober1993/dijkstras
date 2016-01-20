package datastructure.standard;

import com.google.common.truth.Truth;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 21:07
 */
public class GraphImplTest {

    @Test
    public void testAddConnection() {
        GraphImpl graph = new GraphImpl(1, 2, 3, 4, 5, 6, 7);
        graph.addConnection(1, 2, 3.0);
        graph.addConnection(1, 3, 3.0);
        graph.addConnection(1, 4, 3.0);
        graph.addConnection(1, 5, 3.0);
        graph.addConnection(1, 6, 3.0);
        graph.addConnection(1, 7, 3.0);
        graph.addConnection(1, 7, 3.0);

        Set<EdgeImpl> halfEdges = graph.getConnectedElements(graph.getElement(1));
        Truth.assertThat(halfEdges.stream().map(x -> x.getConnected().getId()).collect(Collectors.toSet()))
                .containsExactlyElementsIn(Stream.of(2, 3, 4, 5, 6, 7).collect(Collectors.toSet()));
    }
}
