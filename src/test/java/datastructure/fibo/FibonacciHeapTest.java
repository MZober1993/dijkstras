package datastructure.fibo;

import com.google.common.truth.Truth;
import datastructure.Element;
import datastructure.VertexImpl;
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

    private Entry<Element> one;
    private Entry<Element> two;
    private Entry<Element> three;
    private Entry<Element> six;
    private Entry<Element> seven;
    private Entry<Element> four;
    private Entry<Element> five;
    private Entry<Element> eight;
    private Entry<Element> nine;
    private FibonacciHeap<Element> heap;
    private Consumer<? super Entry<Element>> insert = x -> heap.insert(x);

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
        Truth.assertThat(heap.getMin().getValue()).isEqualTo(new VertexImpl(1));
        Truth.assertThat(heap.getSize()).isEqualTo(3);
    }

    @Test
    public void testNextPointer() {
        miniSample();
        Stream.Builder<Entry<Element>> builder = Stream.builder();
        Entry<Element> tmp = heap.getMin();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getNext();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(Entry::getKey).collect(Collectors.toList()))
                .containsExactly(2.0, 3.0, 1.0, 2.0);
    }

    @Test
    public void testPreviousPointer() {
        miniSample();
        Stream.Builder<Entry<Element>> builder = Stream.builder();
        Entry<Element> tmp = heap.getMin();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getPrevious();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(Entry::getKey).collect(Collectors.toList()))
                .containsExactly(3.0, 2.0, 1.0, 3.0);
    }

    @Test
    public void testCutConnection() {
        miniSample();
        Set<Entry<Element>> nextMemberSet = buildNextMemberSet(4);
        Set<Entry<Element>> previousMemberSet = buildPreviousMemberSet(4);
        Entry<Element> tmpNext = one.getNext();
        FiboHelper.cutConnection(tmpNext);

        Truth.assertThat(buildNextMemberSet(4)).isNotEqualTo(nextMemberSet);
        Truth.assertThat(buildPreviousMemberSet(4)).isNotEqualTo(previousMemberSet);
        Truth.assertThat(one.getNext()).isNotEqualTo(tmpNext);
    }

    @Test
    public void testExtractMinimumWithDegTwo() {
        degTwoSample();

        Entry<Element> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(one);
        Truth.assertThat(heap.getMin()).isEqualTo(two);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegTwo() {
        degTwoSample();
        heap.extractMin();

        Truth.assertThat(heap.getMin()).isEqualTo(two);
        checkSizeOfEntriesWithDeg(degTwoAsStreamWithOutMin(), 2, 1L);
        checkSizeOfEntriesWithDeg(degTwoAsStreamWithOutMin(), 1, 1L);
        parentAssert(Stream.of(four, three), two);
        childAssert(two, three);
        parentAssert(Stream.of(five), four);
        childAssert(four, five);
    }

    @Test
    public void testExtractMinWithMaxValues() {
        Entry<Element> min = new Entry<>(new VertexImpl(1), 0.0);
        Entry<Element> entry1 = new Entry<>(new VertexImpl(2), Double.MAX_VALUE);
        Entry<Element> entry2 = new Entry<>(new VertexImpl(3), Double.MAX_VALUE);
        Entry<Element> entry3 = new Entry<>(new VertexImpl(4), Double.MAX_VALUE);
        Entry<Element> entry4 = new Entry<>(new VertexImpl(5), Double.MAX_VALUE);
        Stream.of(min
                , entry1
                , entry2
                , entry3
                , entry4)
                .forEach(insert);

        Entry<Element> entry = heap.extractMin();
        Truth.assertThat(entry).isEqualTo(min);

        heap.decreaseKey(entry1, 0.0);

        Entry<Element> newMin = heap.extractMin();
        Truth.assertThat(newMin).isEqualTo(entry1);

        heap.decreaseKey(entry3, 0.0);
    }

    private void checkSizeOfEntriesWithDeg(Stream<Entry<Element>> stream, Integer deg, Long countOfEntries) {
        Truth.assertThat(stream.filter(x -> x.getDeg() == deg).count()).isEqualTo(countOfEntries);
    }

    @Test
    public void testExtractMinimumWithDegThree() {
        degThreeSample();
        Entry<Element> actualMin = heap.extractMin();

        Truth.assertThat(actualMin).isEqualTo(one);
        Truth.assertThat(heap.getMin()).isEqualTo(two);
    }

    @Test
    public void testStructureAfterExtractMinimumWithDegThree() {
        degThreeSample();
        heap.extractMin();

        Truth.assertThat(heap.getMin()).isEqualTo(two);
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

        Truth.assertThat(heap.getMin()).isEqualTo(nine);
    }

    @Test
    public void testStructureAfterDecreaseKeyWithDegThreeAndEntryNine() {
        prepareDegThreeHeapWithExtractMin();
        double minimumKey = 1.0;
        heap.decreaseKey(nine, minimumKey);

        Truth.assertThat(heap.getMin()).isEqualTo(nine);
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

        Truth.assertThat(heap.getMin()).isEqualTo(six);
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

    @Test
    public void testSimpleExtractMin() {
        miniSample();
        checkRootList(one);

        Truth.assertThat(heap.getMin()).isEqualTo(one);
        Truth.assertThat(one).isEqualTo(heap.extractMin());

        Truth.assertThat(heap.getMin()).isEqualTo(two);
        childAssert(two, three);
        parentAssert(Stream.of(three), two);
        Truth.assertThat(two).isEqualTo(heap.extractMin());

        Truth.assertThat(heap.getMin()).isEqualTo(three);
        Truth.assertThat(three).isEqualTo(heap.extractMin());
    }

    @Test
    public void testImprovedExtractMin() {
        Stream.of(one, two, three, four).forEach(insert);
        checkRootList(one);

        Truth.assertThat(heap.getMin()).isEqualTo(one);
        Truth.assertThat(one).isEqualTo(heap.extractMin());
        Truth.assertThat(heap.getMin()).isEqualTo(two);
        childAssert(two, three);
        parentAssert(Stream.of(three), two);
        Truth.assertThat(three.getNext()).isEqualTo(three);
        Truth.assertThat(four.getPrevious()).isEqualTo(two);

        heap.decreaseKey(four, 1.0);
        Truth.assertThat(heap.getMin()).isEqualTo(four);
        Truth.assertThat(four.getNext()).isEqualTo(two);
        Truth.assertThat(two.getPrevious()).isEqualTo(four);
        childAssert(two, three);
        parentAssert(Stream.of(three), two);
        Truth.assertThat(four).isEqualTo(heap.extractMin());

        Truth.assertThat(heap.getMin()).isEqualTo(two);
        childAssert(two, three);
        parentAssert(Stream.of(three), two);
        Truth.assertThat(two).isEqualTo(heap.extractMin());

        Truth.assertThat(heap.getMin()).isEqualTo(three);
        Truth.assertThat(three).isEqualTo(heap.extractMin());
        Truth.assertThat(heap.isEmpty()).isEqualTo(true);
    }

    private void prepareDegThreeHeapWithExtractMin() {
        degThreeSample();
        heap.extractMin();
    }

    private void parentAssert(Stream<Entry<Element>> childs, Entry<Element> parent) {
        childs.forEach(x -> Truth.assertThat(x.getParent()).isEqualTo(parent));
    }

    private void childAssert(Entry<Element> parent, Entry<Element> child) {
        Truth.assertThat(parent.getChild()).isEqualTo(child);
    }

    private void notAParentAssert(Stream<Entry<Element>> childs, Entry<Element> parent) {
        childs.forEach(x -> Truth.assertThat(x.getParent()).isNotEqualTo(parent));
    }

    private void notAChildAssert(Entry<Element> parent, Entry<Element> child) {
        Truth.assertThat(parent.getChild()).isNotEqualTo(child);
    }

    private Set<Entry<Element>> buildNextMemberSet(Integer lengthsOfNext) {
        Stream.Builder<Entry<Element>> builder = Stream.builder();
        Entry<Element> tmp = heap.getMin();
        for (int i = 0; i < lengthsOfNext; i++) {
            builder.add(tmp);
            tmp = tmp.getNext();
        }
        return builder.build().collect(Collectors.toSet());
    }

    private Set<Entry<Element>> buildPreviousMemberSet(Integer lengthsOfPrevious) {
        Stream.Builder<Entry<Element>> builder = Stream.builder();
        Entry<Element> tmp = heap.getMin();
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

    private Stream<Entry<Element>> degTwoAsStreamWithOutMin() {
        return Stream.of(two, three, four, five);
    }

    private void degThreeSample() {
        Stream.of(one, two, three, four, five, six, seven, eight, nine).forEach(insert);
    }

    private Stream<Entry<Element>> degThreeAsStreamWithOutMin() {
        return Stream.of(two, four, three, five
                , six, eight, seven, nine);
    }

    private void printAll(Stream<Entry<Element>> stream) {
        stream.forEach(System.out::println);
    }

    private static void checkRootList(Entry<Element> begin) {
        if (begin != null) {
            Entry<Element> element = begin;
            Entry<Element> next;
            do {
                if (element == element.getNext()) {
                    throw new RuntimeException("No rootList for checkRootList!");
                } else {
                    next = element.getNext();
                }
                Truth.assertThat(element.getNext()).isEqualTo(next);
                Truth.assertThat(next.getPrevious()).isEqualTo(element);
                element = next;
            } while (next != begin);
        }
    }
}
