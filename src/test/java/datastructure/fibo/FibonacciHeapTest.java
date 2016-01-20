package datastructure.fibo;

import com.google.common.truth.Truth;
import datastructure.standard.Vertex;
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

    private VertexFibo one;
    private VertexFibo two;
    private VertexFibo three;
    private VertexFibo six;
    private VertexFibo seven;
    private VertexFibo four;
    private VertexFibo five;
    private VertexFibo eight;
    private VertexFibo nine;
    private FibonacciHeap heap;
    private Consumer<? super VertexFibo> insert = x -> heap.insert(x);

    @Before
    public void setUp() {
        one = new VertexFibo(new Vertex(1), 1.0);
        two = new VertexFibo(new Vertex(2), 2.0);
        three = new VertexFibo(new Vertex(3), 3.0);
        six = new VertexFibo(new Vertex(6), 6.0);
        seven = new VertexFibo(new Vertex(7), 7.0);
        four = new VertexFibo(new Vertex(4), 4.0);
        five = new VertexFibo(new Vertex(5), 5.0);
        eight = new VertexFibo(new Vertex(8), 8.0);
        nine = new VertexFibo(new Vertex(9), 9.0);
        heap = new FibonacciHeap();
    }

    @Test
    public void testInsert() {
        miniSample();
        Truth.assertThat(heap.getMin().getValue()).isEqualTo(new Vertex(1));
        Truth.assertThat(heap.getSize()).isEqualTo(3);
    }

    @Test
    public void testNextPointer() {
        miniSample();
        Stream.Builder<VertexFibo> builder = Stream.builder();
        VertexFibo tmp = heap.getMin();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getNext();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(VertexFibo::getKey).collect(Collectors.toList()))
                .containsExactly(2.0, 3.0, 1.0, 2.0);
    }

    @Test
    public void testPreviousPointer() {
        miniSample();
        Stream.Builder<VertexFibo> builder = Stream.builder();
        VertexFibo tmp = heap.getMin();
        for (int i = 0; i < 4; i++) {
            tmp = tmp.getPrevious();
            builder.add(tmp);
        }
        Truth.assertThat(builder.build().map(VertexFibo::getKey).collect(Collectors.toList()))
                .containsExactly(3.0, 2.0, 1.0, 3.0);
    }

    @Test
    public void testCutConnection() {
        miniSample();
        Set<VertexFibo> nextMemberSet = buildNextMemberSet(4);
        Set<VertexFibo> previousMemberSet = buildPreviousMemberSet(4);
        VertexFibo tmpNext = one.getNext();
        tmpNext.getNext().setPrevious(tmpNext.getPrevious());
        tmpNext.getPrevious().setNext(tmpNext.getNext());

        Truth.assertThat(buildNextMemberSet(4)).isNotEqualTo(nextMemberSet);
        Truth.assertThat(buildPreviousMemberSet(4)).isNotEqualTo(previousMemberSet);
        Truth.assertThat(one.getNext()).isNotEqualTo(tmpNext);
    }

    @Test
    public void testExtractMinimumWithDegTwo() {
        degTwoSample();

        VertexFibo actualMin = heap.extractMin();

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
        VertexFibo min = new VertexFibo(new Vertex(1), 0.0);
        VertexFibo entry1 = new VertexFibo(new Vertex(2), Double.MAX_VALUE);
        VertexFibo entry2 = new VertexFibo(new Vertex(3), Double.MAX_VALUE);
        VertexFibo entry3 = new VertexFibo(new Vertex(4), Double.MAX_VALUE);
        VertexFibo entry4 = new VertexFibo(new Vertex(5), Double.MAX_VALUE);
        Stream.of(min
                , entry1
                , entry2
                , entry3
                , entry4)
                .forEach(insert);

        VertexFibo entry = heap.extractMin();
        Truth.assertThat(entry).isEqualTo(min);

        heap.decreaseKey(entry1, 0.0);

        VertexFibo newMin = heap.extractMin();
        Truth.assertThat(newMin).isEqualTo(entry1);

        heap.decreaseKey(entry3, 0.0);
    }

    private void checkSizeOfEntriesWithDeg(Stream<VertexFibo> stream, Integer deg, Long countOfEntries) {
        Truth.assertThat(stream.filter(x -> x.getDeg() == deg).count()).isEqualTo(countOfEntries);
    }

    @Test
    public void testExtractMinimumWithDegThree() {
        degThreeSample();
        VertexFibo actualMin = heap.extractMin();

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

    private void parentAssert(Stream<VertexFibo> childs, VertexFibo parent) {
        childs.forEach(x -> Truth.assertThat(x.getParent()).isEqualTo(parent));
    }

    private void childAssert(VertexFibo parent, VertexFibo child) {
        Truth.assertThat(parent.getChild()).isEqualTo(child);
    }

    private void notAParentAssert(Stream<VertexFibo> childs, VertexFibo parent) {
        childs.forEach(x -> Truth.assertThat(x.getParent()).isNotEqualTo(parent));
    }

    private void notAChildAssert(VertexFibo parent, VertexFibo child) {
        Truth.assertThat(parent.getChild()).isNotEqualTo(child);
    }

    private Set<VertexFibo> buildNextMemberSet(Integer lengthsOfNext) {
        Stream.Builder<VertexFibo> builder = Stream.builder();
        VertexFibo tmp = heap.getMin();
        for (int i = 0; i < lengthsOfNext; i++) {
            builder.add(tmp);
            tmp = tmp.getNext();
        }
        return builder.build().collect(Collectors.toSet());
    }

    private Set<VertexFibo> buildPreviousMemberSet(Integer lengthsOfPrevious) {
        Stream.Builder<VertexFibo> builder = Stream.builder();
        VertexFibo tmp = heap.getMin();
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

    private Stream<VertexFibo> degTwoAsStreamWithOutMin() {
        return Stream.of(two, three, four, five);
    }

    private void degThreeSample() {
        Stream.of(one, two, three, four, five, six, seven, eight, nine).forEach(insert);
    }

    private Stream<VertexFibo> degThreeAsStreamWithOutMin() {
        return Stream.of(two, four, three, five
                , six, eight, seven, nine);
    }

    private void printAll(Stream<VertexFibo> stream) {
        stream.forEach(System.out::println);
    }

    private static void checkRootList(VertexFibo begin) {
        if (begin != null) {
            VertexFibo element = begin;
            VertexFibo next;
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
