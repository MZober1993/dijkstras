package datastructure;

import com.google.common.truth.Truth;
import datastructure.fibo.FibonacciHeap;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import datastructure.fibo.Entry;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 21.12.15 - 11:27
 */
public class FibonacciHeapTest {

    private Entry<Vertex> min;
    private Entry<Vertex> newMinimum;
    private Entry<Vertex> neighborEntryThree;
    private Entry<Vertex> neighborEntrySix;
    private Entry<Vertex> neighborEntrySeven;
    private Entry<Vertex> child1;
    private Entry<Vertex> child2;
    private Entry<Vertex> child3;
    private Entry<Vertex> child4;
    private FibonacciHeap<Vertex> heap;

    @Before
    public void setUp() {
        min = new Entry<>(new VertexImpl(1), 1.0);
        newMinimum = new Entry<>(new VertexImpl(2), 2.0);
        neighborEntryThree = new Entry<>(new VertexImpl(3), 3.0);
        neighborEntrySix = new Entry<>(new VertexImpl(6), 6.0);
        neighborEntrySeven = new Entry<>(new VertexImpl(7), 7.0);
        child1 = new Entry<>(new VertexImpl(4), 4.0);
        child2 = new Entry<>(new VertexImpl(5), 5.0);
        child3 = new Entry<>(new VertexImpl(8), 8.0);
        child4 = new Entry<>(new VertexImpl(9), 9.0);
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
        Entry<Vertex> tmpNext = min.getNext();
        heap.cutConnection(tmpNext);

        Truth.assertThat(buildNextMemberSet(4)).isNotEqualTo(nextMemberSet);
        Truth.assertThat(buildPreviousMemberSet(4)).isNotEqualTo(previousMemberSet);
        Truth.assertThat(min.getNext()).isNotEqualTo(tmpNext);
    }

    @Test
    public void testExtractMinimumWithDegTwo() {
        mediumSample();

        Entry<Vertex> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(min);
        Truth.assertThat(heap.getMinimum()).isEqualTo(newMinimum);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegTwo() {
        mediumSample();
        heap.extractMin();

        Truth.assertThat(newMinimum.getChild()).isEqualTo(child2);
        Truth.assertThat(neighborEntryThree.getChild()).isEqualTo(child1);
        Truth.assertThat(neighborEntryThree.getNext()).isEqualTo(child2);
        Truth.assertThat(neighborEntryThree.getPrevious()).isEqualTo(child2);
        Truth.assertThat(child1.getParent()).isEqualTo(neighborEntryThree);
        Truth.assertThat(child2.getParent()).isEqualTo(newMinimum);
    }

    @Test
    public void testExtractMinimumWithDegThree() {
        degThreeSample();
        Entry<Vertex> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(min);
        Truth.assertThat(heap.getMinimum()).isEqualTo(newMinimum);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegThree() {
        degThreeSample();
        heap.extractMin();
        //Todo: clear this sample structure

        printAll(degThreeAsStreamWithOutMin());
        System.out.println(heap.getSize());
        Entry<Vertex> newNewMin = heap.extractMin();
        //System.out.println(newNewMin);
        //System.out.println(heap.getSize());
        printAll(degThreeAsStreamWithOutMin().filter(x->x.getPriority()!=2.0));
        Entry<Vertex> newNewNewMin = heap.extractMin();
        printAll(degThreeAsStreamWithOutMin().filter(x->x!=newMinimum&&x!=neighborEntryThree));
        heap.extractMin();

        /*Truth.assertThat(newMinimum.getChild()).isEqualTo(neighborEntryThree);
        Truth.assertThat(neighborEntryThree.getChild()).isEqualTo(child1);
        Truth.assertThat(neighborEntryThree.getNext()).isEqualTo(child2);
        Truth.assertThat(neighborEntryThree.getPrevious()).isEqualTo(child2);
        Truth.assertThat(child1.getParent()).isEqualTo(neighborEntryThree);
        Truth.assertThat(child2.getParent()).isEqualTo(newMinimum);*/
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
        heap.insert(min);
        heap.insert(new VertexImpl(2), 2.0);
        heap.insert(new VertexImpl(3), 3.0);
    }

    private void mediumSample() {
        heap.insert(min);
        heap.insert(newMinimum);
        heap.insert(neighborEntryThree);
        Entry<Vertex> next = heap.getMinimum().getNext();

        connectParent(next, child1);

        connectParent(next.getNext(), child2);
    }

    private void degThreeSample() {
        heap.insert(min);
        heap.insert(newMinimum);
        heap.insert(neighborEntryThree);
        heap.insert(neighborEntrySix);
        heap.insert(neighborEntrySeven);
        Entry<Vertex> next = heap.getMinimum().getNext();

        connectParent(next, child1);

        connectParent(next.getNext(), child2);

        connectParent(neighborEntrySix, child3);

        connectParent(neighborEntrySeven, child4);
    }

    private Stream<Entry<Vertex>> degThreeAsStreamWithOutMin(){
        return Stream.of(newMinimum, child1, neighborEntryThree, child2
                , neighborEntrySix, child3, neighborEntrySeven, child4);
    }

    private Stream<Entry<Vertex>> degTwoAsStreamWithOutMin(){
        return Stream.of(newMinimum, child1, neighborEntryThree, child2);
    }

    private void printAll(Stream<Entry<Vertex>> stream){
        stream.forEach(System.out::println);
    }

    private void connectParent(Entry<Vertex> parent,Entry<Vertex> child) {
        parent.setChild(child);
        parent.setDeg(parent.getDeg() + 1);
        child.setParent(parent);
    }
}
