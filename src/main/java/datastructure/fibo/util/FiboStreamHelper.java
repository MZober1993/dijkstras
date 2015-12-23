package datastructure.fibo.util;

import datastructure.fibo.FibonacciHeap;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 23.12.15 - 21:35
 */
public class FiboStreamHelper<T> {

    private Predicate<FibonacciHeap.Entry<T>> childExist = x -> x.getChild() != null;
    private Predicate<FibonacciHeap.Entry<T>> degreeNotZero = x -> x.getDeg() != 0;

    public void printMember(FibonacciHeap<T> heap) {
        calculateDepth(heap);
        Stream<FibonacciHeap.Entry<T>> member = addMemberToStream(Stream.builder()
                , heap.getMinimum()).build();
        member.filter(childExist)
                .forEach(x -> printMemberLine(
                        addMemberToStream(Stream.builder(), x.getChild()).build(), 2));
    }

    private void calculateDepth(FibonacciHeap<T> heap) {
        Stream<FibonacciHeap.Entry<T>> startMemberWithChild
                = addMemberToStream(Stream.builder(), heap.getMinimum()).build().filter(childExist);
        Stream<FibonacciHeap.Entry<T>> tmpMember = startMemberWithChild;
        //ToDo: bestMember? BestList-> what happens with equality?
        Stream.Builder<Boolean> childFoundStream = Stream.builder();
        boolean childFound = true;
        while (childFound) {
            for (FibonacciHeap.Entry<T> memberWithChild : tmpMember.collect(Collectors.toList())) {
                tmpMember = addMemberToStream(Stream.builder(), memberWithChild.getChild())
                        .build()
                        .filter(degreeNotZero);
                childFoundStream.add(tmpMember.count() != 0);
            }
            //TODO: MapStream?
            childFound = childFoundStream.build().filter(x -> x == true).count() != 0;
        }
    }

    public void printMemberLine(Stream<FibonacciHeap.Entry<T>> stream, Integer line) {
        stream.forEach(x -> System.out.println(line + " " + x.getPriority()));
    }

    private Stream.Builder<FibonacciHeap.Entry<T>> addMemberToStream(Stream.Builder<FibonacciHeap.Entry<T>> builder, FibonacciHeap.Entry<T> entry) {
        builder.add(entry);
        for (FibonacciHeap.Entry<T> current = entry.getNext(); current != entry; current.getNext()) {
            builder.add(current);
        }
        return builder;
    }

}
