package datastructure.binary;

import datastructure.Element;
import datastructure.VertexImpl;
import org.junit.Before;
import org.junit.Test;

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
    public void testInsert() {
        heap.insert(nine);
        heap.insert(eight);
        heap.insert(seven);
        heap.insert(zero);
        heap.insert(one);
        heap.insert(five);
        heap.extractMin();
        System.out.println(heap.toString());
        assert (heap.structureIsAlright());
    }
}
