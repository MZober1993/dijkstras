package datastructure;

import com.google.common.truth.Truth;
import datastructure.fibo.Entry;
import datastructure.fibo.FiboHelper;
import datastructure.fibo.FibonacciHeap;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 21.12.15 - 11:27
 */
public class FibonacciHeapTest {

    private Entry<Vertex> one;
    private Entry<Vertex> two;
    private Entry<Vertex> three;
    private Entry<Vertex> six;
    private Entry<Vertex> seven;
    private Entry<Vertex> four;
    private Entry<Vertex> five;
    private Entry<Vertex> eight;
    private Entry<Vertex> nine;
    private FibonacciHeap<Vertex> heap;
    private Consumer<? super Entry<Vertex>> insert = x -> heap.insert(x);

    @Before
    public void setUp() {
        one = new Entry<>(new VertexImpl(1), 1.0);
        two = new Entry<>(new VertexImpl(2), 2.0);
        three = new Entry<>(new VertexImpl(3), 3.0);
        six = new Entry<>(new VertexImpl(6), 6.0);
        seven = new Entry<>(new VertexImpl(7), 7.0);
        four = new Entry<>(new VertexImpl(4), 4.0);
        five = new Entry<>(new VertexImpl(5), 5.0);
        eight = new Entry<>(new VertexImpl(8), 8.0);
        nine = new Entry<>(new VertexImpl(9), 9.0);
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
        Truth.assertThat(builder.build().map(Entry::getKey).collect(Collectors.toList()))
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
        Truth.assertThat(builder.build().map(Entry::getKey).collect(Collectors.toList()))
                .containsExactly(2.0, 3.0, 1.0, 2.0);
    }

    @Test
    public void testCutConnection() {
        miniSample();
        Set<Entry<Vertex>> nextMemberSet = buildNextMemberSet(4);
        Set<Entry<Vertex>> previousMemberSet = buildPreviousMemberSet(4);
        Entry<Vertex> tmpNext = one.getNext();
        FiboHelper.cutConnection(tmpNext);

        Truth.assertThat(buildNextMemberSet(4)).isNotEqualTo(nextMemberSet);
        Truth.assertThat(buildPreviousMemberSet(4)).isNotEqualTo(previousMemberSet);
        Truth.assertThat(one.getNext()).isNotEqualTo(tmpNext);
    }

    @Test
    public void testExtractMinimumWithDegTwo() {
        degTwoSample();

        Entry<Vertex> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(one);
        Truth.assertThat(heap.getMinimum()).isEqualTo(two);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegTwo() {
        degTwoSample();
        heap.extractMin();

        Truth.assertThat(heap.getMinimum()).isEqualTo(two);
        checkSizeOfEntriesWithDeg(degTwoAsStreamWithOutMin(), 2, 1L);
        checkSizeOfEntriesWithDeg(degTwoAsStreamWithOutMin(), 1, 1L);
        parentAssert(Stream.of(four, three), two);
        childAssert(two, three);
        parentAssert(Stream.of(five), four);
        childAssert(four, five);
    }

    @Test
    public void testExtractMinWithMaxValues() {
        Entry<Vertex> min = new Entry<>(new VertexImpl(1), 0.0);
        Entry<Vertex> entry1 = new Entry<>(new VertexImpl(2), Double.MAX_VALUE);
        Entry<Vertex> entry2 = new Entry<>(new VertexImpl(3), Double.MAX_VALUE);
        Entry<Vertex> entry3 = new Entry<>(new VertexImpl(4), Double.MAX_VALUE);
        Entry<Vertex> entry4 = new Entry<>(new VertexImpl(5), Double.MAX_VALUE);
        Stream.of(min
                , entry1
                , entry2
                , entry3
                , entry4)
                .forEach(insert);

        Entry<Vertex> entry = heap.extractMin();
        Truth.assertThat(entry).isEqualTo(min);
        System.out.println(heap);

        System.out.println();
        heap.decreaseKey(entry1, 0.0);
        System.out.println(heap);

        Entry<Vertex> newMin = heap.extractMin();
        Truth.assertThat(newMin).isEqualTo(entry1);
        System.out.println(heap);

        System.out.println();
        heap.decreaseKey(entry3, 0.0);
        System.out.println(heap);
    }

    private void checkSizeOfEntriesWithDeg(Stream<Entry<Vertex>> stream, Integer deg, Long countOfEntries) {
        Truth.assertThat(stream.filter(x -> x.getDeg() == deg).count()).isEqualTo(countOfEntries);
    }

    @Test
    public void testExtractMinimumWithDegThree() {
        degThreeSample();
        Entry<Vertex> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(one);
        Truth.assertThat(heap.getMinimum()).isEqualTo(two);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegThree() {
        degThreeSample();
        heap.extractMin();

        Truth.assertThat(heap.getMinimum()).isEqualTo(two);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 3, 1L);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 2, 1L);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 1, 2L);
        parentAssert(Stream.of(four, three, six), two);
        childAssert(two, three);
        parentAssert(Stream.of(five), four);
        childAssert(four, five);
        parentAssert(Stream.of(seven, eight), six);
        childAssert(six, seven);
        parentAssert(Stream.of(nine), eight);
        childAssert(eight, nine);
    }

    @Test
    public void testDecreaseKeyWithDegThree() {
        prepareDegThreeHeapWithExtractMin();
        double minimumKey = 1.0;
        heap.decreaseKey(nine, minimumKey);

        Truth.assertThat(heap.getMinimum()).isEqualTo(nine);
    }

    @Test
    public void testStructureAfterDecreaseKeyWithDegThreeAndEntryNine() {
        prepareDegThreeHeapWithExtractMin();
        double minimumKey = 1.0;
        heap.decreaseKey(nine, minimumKey);

        Truth.assertThat(heap.getMinimum()).isEqualTo(nine);
        Truth.assertThat(nine.getKey()).isEqualTo(minimumKey);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 3, 1L);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 2, 1L);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 1, 1L);
        Truth.assertThat(eight.getDeg()).isEqualTo(0);
        checkParentChildPointerOfDegThreeWithoutNineAndEight();
        notAParentAssert(Stream.of(nine), eight);
        notAChildAssert(eight, nine);
    }

    @Test
    public void testStructureAfterDecreaseKeyWithDegThreeAndEntrySix() {
        prepareDegThreeHeapWithExtractMin();
        double minimumKey = 1.0;
        heap.decreaseKey(six, minimumKey);

        Truth.assertThat(heap.getMinimum()).isEqualTo(six);
        Truth.assertThat(six.getKey()).isEqualTo(minimumKey);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 3, 0L);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 2, 2L);
        checkSizeOfEntriesWithDeg(degThreeAsStreamWithOutMin(), 1, 2L);
        parentAssert(Stream.of(four, three), two);
        childAssert(two, three);
        parentAssert(Stream.of(eight, seven), six);
        childAssert(six, seven);
        parentAssert(Stream.of(five), four);
        childAssert(four, five);
        parentAssert(Stream.of(nine), eight);
        childAssert(eight, nine);
    }

    private void checkParentChildPointerOfDegThreeWithoutNineAndEight() {
        parentAssert(Stream.of(four, three, six), two);
        childAssert(two, three);
        parentAssert(Stream.of(five), four);
        childAssert(four, five);
        parentAssert(Stream.of(seven, eight), six);
        childAssert(six, seven);
    }

    private void prepareDegThreeHeapWithExtractMin() {
        degThreeSample();
        heap.extractMin();
    }

    private void parentAssert(Stream<Entry<Vertex>> childs, Entry<Vertex> parent) {
        childs.forEach(x -> Truth.assertThat(x.getParent()).isEqualTo(parent));
    }

    private void childAssert(Entry<Vertex> parent, Entry<Vertex> child) {
        Truth.assertThat(parent.getChild()).isEqualTo(child);
    }

    private void notAParentAssert(Stream<Entry<Vertex>> childs, Entry<Vertex> parent) {
        childs.forEach(x -> Truth.assertThat(x.getParent()).isNotEqualTo(parent));
    }

    private void notAChildAssert(Entry<Vertex> parent, Entry<Vertex> child) {
        Truth.assertThat(parent.getChild()).isNotEqualTo(child);
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
        Stream.of(one, two, three).forEach(insert);
    }

    private void degTwoSample() {
        Stream.of(one, two, three, four, five).forEach(insert);
    }

    private Stream<Entry<Vertex>> degTwoAsStreamWithOutMin() {
        return Stream.of(two, three, four, five);
    }

    private void degThreeSample() {
        Stream.of(one, two, three, four, five, six, seven, eight, nine).forEach(insert);
    }

    private Stream<Entry<Vertex>> degThreeAsStreamWithOutMin() {
        return Stream.of(two, four, three, five
                , six, eight, seven, nine);
    }

    private void printAll(Stream<Entry<Vertex>> stream) {
        stream.forEach(System.out::println);
    }
}
