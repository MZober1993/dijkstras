package datastructure;

import com.google.common.truth.Truth;
import datastructure.fibo.FibonacciHeap;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import datastructure.fibo.Entry;
import util.LoggingHelper;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 21.12.15 - 11:27
 */
public class FibonacciHeapTest {

    public static final Entry<Vertex> MIN = new Entry<>(new VertexImpl(1), 1.0);
    public static final Entry<Vertex> NEW_MINIMUM = new Entry<>(new VertexImpl(2), 2.0);
    public static final Entry<Vertex> NEIGHBOR_ENTRY_THREE = new Entry<>(new VertexImpl(3), 3.0);
    public static final Entry<Vertex> NEIGHBOR_ENTRY_SIX = new Entry<>(new VertexImpl(6), 6.0);
    public static final Entry<Vertex> NEIGHBOR_ENTRY_SEVEN = new Entry<>(new VertexImpl(7), 7.0);
    public static final Entry<Vertex> CHILD_1 = new Entry<>(new VertexImpl(4), 4.0);
    public static final Entry<Vertex> CHILD_2 = new Entry<>(new VertexImpl(5), 5.0);
    public static final Entry<Vertex> CHILD_3 = new Entry<>(new VertexImpl(8), 8.0);
    public static final Entry<Vertex> CHILD_4 = new Entry<>(new VertexImpl(9), 9.0);
    private FibonacciHeap<Vertex> heap;

    @Before
    public void setUp() {
        heap = new FibonacciHeap<>();
    }

    @Test
    public void testInsert() {
        miniSample();
        Truth.assertThat(heap.getMinimum().getValue()).isEqualTo(new VertexImpl(1));
        Truth.assertThat(heap.getSize()).isEqualTo(3);
    }

    @Test
    public void testNextPointer() {
        miniSample();
        Stream.Builder<Entry<Vertex>> builder = Stream.builder();
        Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getNext();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(Entry::getPriority).collect(Collectors.toList()))
                .containsExactly(3.0, 2.0, 1.0, 3.0);
    }

    @Test
    public void testPreviousPointer() {
        miniSample();
        Stream.Builder<Entry<Vertex>> builder = Stream.builder();
        Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getPrevious();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(Entry::getPriority).collect(Collectors.toList()))
                .containsExactly(2.0, 3.0, 1.0, 2.0);
    }

    @Test
    public void testCutConnection() {
        miniSample();
        Set<Entry<Vertex>> nextMemberSet = buildNextMemberSet(4);
        Set<Entry<Vertex>> previousMemberSet = buildPreviousMemberSet(4);
        Entry<Vertex> tmpNext = MIN.getNext();
        heap.cutConnection(tmpNext);

        Truth.assertThat(buildNextMemberSet(4)).isNotEqualTo(nextMemberSet);
        Truth.assertThat(buildPreviousMemberSet(4)).isNotEqualTo(previousMemberSet);
        Truth.assertThat(MIN.getNext()).isNotEqualTo(tmpNext);
    }

    @Test
    public void testExtractMinimumWithDegTwo() {
        mediumSample();

        Entry<Vertex> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(MIN);
        Truth.assertThat(heap.getMinimum()).isEqualTo(NEW_MINIMUM);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegTwo() {
        mediumSample();
        heap.extractMin();

        Truth.assertThat(NEW_MINIMUM.getChild()).isEqualTo(NEIGHBOR_ENTRY_THREE);
        Truth.assertThat(NEIGHBOR_ENTRY_THREE.getChild()).isEqualTo(CHILD_1);
        Truth.assertThat(NEIGHBOR_ENTRY_THREE.getNext()).isEqualTo(CHILD_2);
        Truth.assertThat(NEIGHBOR_ENTRY_THREE.getPrevious()).isEqualTo(CHILD_2);
        Truth.assertThat(CHILD_1.getParent()).isEqualTo(NEIGHBOR_ENTRY_THREE);
        Truth.assertThat(CHILD_2.getParent()).isEqualTo(NEW_MINIMUM);
    }

    @Test
    public void testExtractMinimumWithDegThree() {
        degThreeSample();
        Entry<Vertex> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(MIN);
        Truth.assertThat(heap.getMinimum()).isEqualTo(NEW_MINIMUM);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegThree() {
        degThreeSample();
        heap.extractMin();

        Truth.assertThat(NEW_MINIMUM.getChild()).isEqualTo(NEIGHBOR_ENTRY_THREE);
        Truth.assertThat(NEIGHBOR_ENTRY_THREE.getChild()).isEqualTo(CHILD_1);
        Truth.assertThat(NEIGHBOR_ENTRY_THREE.getNext()).isEqualTo(CHILD_2);
        Truth.assertThat(NEIGHBOR_ENTRY_THREE.getPrevious()).isEqualTo(CHILD_2);
        Truth.assertThat(CHILD_1.getParent()).isEqualTo(NEIGHBOR_ENTRY_THREE);
        Truth.assertThat(CHILD_2.getParent()).isEqualTo(NEW_MINIMUM);
    }

    private Set<Entry<Vertex>> buildNextMemberSet(Integer lengthsOfNext) {
        Stream.Builder<Entry<Vertex>> builder = Stream.builder();
        Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < lengthsOfNext; i++) {
            builder.add(tmp);
            tmp = tmp.getNext();
        }
        return builder.build().collect(Collectors.toSet());
    }

    private Set<Entry<Vertex>> buildPreviousMemberSet(Integer lengthsOfPrevious) {
        Stream.Builder<Entry<Vertex>> builder = Stream.builder();
        Entry<Vertex> tmp = heap.getMinimum();
        for (int i = 0; i < lengthsOfPrevious; i++) {
            builder.add(tmp);
            tmp = tmp.getPrevious();
        }
        return builder.build().collect(Collectors.toSet());
    }

    private void miniSample() {
        heap.insert(MIN);
        heap.insert(new VertexImpl(2), 2.0);
        heap.insert(new VertexImpl(3), 3.0);
    }

    private void mediumSample() {
        heap.insert(MIN);
        heap.insert(NEW_MINIMUM);
        heap.insert(NEIGHBOR_ENTRY_THREE);
        Entry<Vertex> next = heap.getMinimum().getNext();

        connectParent(next,CHILD_1);

        connectParent(next.getNext(),CHILD_2);
    }

    private void degThreeSample() {
        heap.insert(MIN);
        heap.insert(NEW_MINIMUM);
        heap.insert(NEIGHBOR_ENTRY_THREE);
        heap.insert(NEIGHBOR_ENTRY_SIX);
        heap.insert(NEIGHBOR_ENTRY_SEVEN);
        Entry<Vertex> next = heap.getMinimum().getNext();

        connectParent(next,CHILD_1);

        connectParent(next.getNext(),CHILD_2);

        connectParent(NEIGHBOR_ENTRY_SIX,CHILD_3);

        connectParent(NEIGHBOR_ENTRY_SEVEN,CHILD_4);

    }

    private void connectParent(Entry<Vertex> parent,Entry<Vertex> child) {
        parent.setChild(child);
        parent.setDeg(parent.getDeg() + 1);
        child.setParent(parent);
    }
}
