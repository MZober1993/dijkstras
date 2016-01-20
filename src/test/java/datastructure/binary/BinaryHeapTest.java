package datastructure.binary;

import com.google.common.truth.Truth;
import datastructure.standard.Vertex;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

import static datastructure.binary.BinaryHeapHelper.structureIsAlright;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 12:10
 */
public class BinaryHeapTest {

    BinaryHeap heap;
    VertexBinary zero;
    VertexBinary one;
    VertexBinary two;
    VertexBinary three;
    VertexBinary four;
    VertexBinary five;
    VertexBinary six;
    VertexBinary seven;
    VertexBinary eight;
    VertexBinary nine;
    private Consumer<VertexBinary> insert = x -> heap.insert(x);

    @Before
    public void setUp() {
        heap = new BinaryHeap();
        zero = new VertexBinary(new Vertex(0), 0.0);
        one = new VertexBinary(new Vertex(1), 1.0);
        two = new VertexBinary(new Vertex(2), 2.0);
        three = new VertexBinary(new Vertex(3), 3.0);
        four = new VertexBinary(new Vertex(4), 4.0);
        five = new VertexBinary(new Vertex(5), 5.0);
        six = new VertexBinary(new Vertex(6), 6.0);
        seven = new VertexBinary(new Vertex(7), 7.0);
        eight = new VertexBinary(new Vertex(8), 8.0);
        nine = new VertexBinary(new Vertex(9), 9.0);
    }

    @Test
    public void testInsertOrdered() {
        all().forEach(insert);
        assert (structureIsAlright(heap));
    }

    @Test
    public void testInsertMixedOrdered() {
        allMixedOrdered().forEach(insert);
        assert (structureIsAlright(heap));
    }

    @Test
    public void testExtractMin() {
        allMixedOrdered().forEach(insert);
        extractAndAssertEquality(zero);
        extractAndAssertEquality(one);
        extractAndAssertEquality(two);
        extractAndAssertEquality(three);
        extractAndAssertEquality(four);
        extractAndAssertEquality(five);
        extractAndAssertEquality(six);
        extractAndAssertEquality(seven);
        extractAndAssertEquality(eight);
        extractAndAssertEquality(nine);
    }

    @Test
    public void testDecreaseKey() {
        allWithoutZeroOneAndTwo().forEach(insert);
        heap.decreaseKey(nine, 0.0);
        extractAndAssertEquality(nine);
    }

    private void extractAndAssertEquality(VertexBinary zero) {
        VertexBinary min = heap.extractMin();
        Truth.assertThat(min).isEqualTo(zero);
        assert (structureIsAlright(heap));
    }

    private Stream<VertexBinary> all() {
        return Stream.of(zero, one, two, three, four, five, six, seven, eight, nine);
    }

    private Stream<VertexBinary> allWithoutZeroOneAndTwo() {
        return Stream.of(three, four, five, six, seven, eight, nine);
    }

    private Stream<VertexBinary> allMixedOrdered() {
        return Stream.of(nine, eight, seven, zero, one, five, six, two, three, four);
    }
}
