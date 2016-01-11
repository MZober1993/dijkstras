package datastructure.binary;

import datastructure.Element;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 18:55
 */
public class BinaryHeapHelper {


    public static <T extends Element> boolean structureIsAlright(BinaryHeap<T> heap) {
        boolean check = true;
        for (int i = 0; i < heap.getSize(); i++) {
            int second = 2 * i + 2;
            int first = 2 * i + 1;
            int parent = (int) Math.floor((i - 1) / 2);
            if (parent >= 0) {
                if (heap.getSize() > second) {
                    check = checkStructure(heap, i, first, second, parent);
                } else if (heap.getSize() > first) {
                    check = checkStructure(heap, i, first, parent);
                }
            }
        }
        return check;
    }

    private static <T extends Element> boolean checkStructure(BinaryHeap<T> heap
            , Integer index, Integer childIndexOne, Integer childIndexTwo, Integer parentIndex) {
        Double key = heap.getElements().get(index).getKey();
        if (checkChild(heap.getElements(), childIndexOne, key)
                && checkChild(heap.getElements(), childIndexTwo, key)
                && (index == parentIndex || checkParent(heap.getElements(), parentIndex, key))) {
            return true;
        } else {
            System.out.println("structure fail at:" + indexKeyPair(heap.getElements(), index)
                    + ",with c1:" + indexKeyPair(heap.getElements(), childIndexOne)
                    + ",with c2:" + indexKeyPair(heap.getElements(), childIndexTwo) +
                    " and with p:" + indexKeyPair(heap.getElements(), parentIndex));
            return false;
        }
    }

    private static <T extends Element> boolean checkStructure(BinaryHeap<T> heap, Integer index,
                                                              Integer childIndexOne, Integer parentIndex) {
        Double key = heap.getElements().get(index).getKey();
        if (checkChild(heap.getElements(), childIndexOne, key)
                && (index.equals(parentIndex) || checkParent(heap.getElements(), parentIndex, key))) {
            return true;
        } else {
            System.out.println("structure fail at:" + indexKeyPair(heap.getElements(), index) +
                    ",with c1:" + indexKeyPair(heap.getElements(), childIndexOne)
                    + ",with no c2: and with p:" + indexKeyPair(heap.getElements(), parentIndex));
            return false;
        }
    }

    private static <T extends Element> boolean checkParent(List<BEntry<T>> elements,
                                                           Integer parentIndex, Double key) {
        return key > elements.get(parentIndex).getKey();
    }

    private static <T extends Element> boolean checkChild(List<BEntry<T>> elements,
                                                          Integer childIndexOne, Double key) {
        return key < elements.get(childIndexOne).getKey();
    }

    private static <T extends Element> String indexKeyPair(List<BEntry<T>> elements, Integer index) {
        return "(" + index + "," + elements.get(index).getKey() + ")";
    }
}