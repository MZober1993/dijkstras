package datastructure;

import com.google.common.truth.Truth;
import datastructure.fibo.FibonacciHeap;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static datastructure.fibo.FibonacciHeap.*;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 21.12.15 - 11:27
 */
public class FibonacciHeapTest {

    private FibonacciHeap<Vertex> heap;
    public static final VertexImpl MIN = new VertexImpl(1);

    @Before
    public void setUp() {
        heap = new FibonacciHeap<>();
    }

    @Test
    public void testInsert() {
        init();
        Truth.assertThat(heap.getMinimum().getValue()).isEqualTo(MIN);
        Truth.assertThat(heap.getSize()).isEqualTo(3);
    }

    @Test
    public void testNextPointer() {
        init();
        Stream.Builder<FibonacciHeap.Entry<Vertex>> builder = Stream.builder();
        FibonacciHeap.Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getNext();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(Entry::getPriority).collect(Collectors.toList()))
                .containsExactly(3.0,2.0,1.0,3.0);
    }

    @Test
    public void testPreviousPointer() {
        init();
        Stream.Builder<FibonacciHeap.Entry<Vertex>> builder = Stream.builder();
        FibonacciHeap.Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getPrevious();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(Entry::getPriority).collect(Collectors.toList()))
                .containsExactly(2.0,3.0,1.0,2.0);
    }

    @Test
    public void testExtractMinimum(){
        init();
        Entry<Vertex> child1 = new Entry<>(new VertexImpl(4), 4.0);
        Entry<Vertex> child2 = new Entry<>(new VertexImpl(5), 5.0);
        Entry<Vertex> nextToChild1 = new Entry<>(new VertexImpl(6), 6.0);
        heap.getMinimum().getNext().setChild(child1);
        heap.getMinimum().getNext().getNext().setChild(child2);
        Entry<Vertex> min1 = heap.extractMin();
        Truth.assertThat(min1.getValue()).isEqualTo(MIN);
        System.out.println(heap);
    }

    private void init() {
        heap.insert(MIN, 1.0);
        heap.insert(new VertexImpl(2), 2.0);
        heap.insert(new VertexImpl(3), 3.0);
    }
}