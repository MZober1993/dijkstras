package datastructure.binary;

import datastructure.Element;
import datastructure.VertexImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 12:10
 */
public class BinaryHeapTest {

    BinaryHeap<Element> heap;
    BEntry<Element> zero;
    BEntry<Element> one;
    BEntry<Element> two;
    BEntry<Element> three;
    BEntry<Element> four;
    BEntry<Element> five;
    BEntry<Element> six;
    BEntry<Element> seven;
    BEntry<Element> eight;
    BEntry<Element> nine;
    private Consumer<BEntry<Element>> insert = x -> heap.insert(x);

    @Before
    public void setUp() {
        heap = new BinaryHeap<>();
        zero = new BEntry<>(new VertexImpl(0), 0.0);
        one = new BEntry<>(new VertexImpl(1), 1.0);
        two = new BEntry<>(new VertexImpl(2), 2.0);
        three = new BEntry<>(new VertexImpl(3), 3.0);
        four = new BEntry<>(new VertexImpl(4), 4.0);
        five = new BEntry<>(new VertexImpl(5), 5.0);
        six = new BEntry<>(new VertexImpl(6), 6.0);
        seven = new BEntry<>(new VertexImpl(7), 7.0);
        eight = new BEntry<>(new VertexImpl(8), 8.0);
        nine = new BEntry<>(new VertexImpl(9), 9.0);
    }

    @Test
    public void testInsertOrdered() {
        all().forEach(insert);
        assert (heap.structureIsAlright());
    }

    @Test
    public void testInsertMixedOrdered() {
        allMixedOrdered().forEach(insert);
        assert (heap.structureIsAlright());
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

    private void extractAndAssertEquality(BEntry<Element> zero) {
        BEntry<Element> min = heap.extractMin();
        assert (min.equals(zero));
        assert (heap.structureIsAlright());
    }

    private Stream<BEntry<Element>> all() {
        return Stream.of(zero, one, two, three, four, five, six, seven, eight, nine);
    }

    private Stream<BEntry<Element>> allMixedOrdered() {
        return Stream.of(nine, eight, seven, zero, one, five, six, two, three, four);
    }
}
