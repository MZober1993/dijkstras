package datastructure;

import com.google.common.truth.Truth;
import datastructure.fibo.FibonacciHeap;
import org.junit.Before;
import org.junit.Test;

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
        FibonacciHeap.Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getNext();
            System.out.println(tmp);
        }
    }

    @Test
    public void testPreviousPointer() {
        init();
        FibonacciHeap.Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getPrevious();
            System.out.println(tmp);
        }
    }

    private void init() {
        heap.insert(MIN, 1.0);
        heap.insert(new VertexImpl(2), 2.0);
        heap.insert(new VertexImpl(3), 3.0);
    }
}
