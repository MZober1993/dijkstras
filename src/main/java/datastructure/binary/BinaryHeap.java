package datastructure.binary;

import datastructure.Element;
import util.MathHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 11:34
 */
public class BinaryHeap<T extends Element> {

    private Integer size = 0;
    private List<BEntry<T>> elements = new ArrayList<>();

    public BEntry<T> insert(BEntry<T> entry) {
        elements.add(entry);
        heapify(size);

        size++;
        return entry;
    }

    public BEntry<T> extractMin() {
        //TODO: check this structure resetting algorithm
        BEntry<T> entry = elements.get(0);
        entry.setPosition(null);
        elements.set(0, elements.get(size - 1));

        size--;
        heapifyDown(0);
        return entry;
    }

    public void decreaseKey(BEntry<T> entry, Double key) {
        if (elements.get(entry.getPosition()).getKey() > key) {
            heapify(entry.getPosition());
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void heapifyDown(int position) {
        while (position < size) {
            int positionLeft = (position + 1) * 2 - 1;
            int positionRight = (position + 1) * 2;
            int positionSmallest = position;
            if (positionLeft <= size
                    && elements.get(positionLeft).getKey()
                    < elements.get(positionSmallest).getKey()) {
                positionSmallest = positionLeft;
            }
            if (positionRight <= size
                    && elements.get(positionRight).getKey()
                    < elements.get(positionSmallest).getKey()) {
                positionSmallest = positionRight;
            }
            if (elements.get(position).getKey()
                    > elements.get(positionSmallest).getKey()) {
                swapBEntry(position, positionSmallest);
                position = positionSmallest;
            } else {
                break;
            }
        }
    }

    private void heapify(Integer position) {
        while (position >= 0) {
            Integer positionParent = (position - 1) / 2;
            if (elements.get(position).getKey() < elements.get(positionParent).getKey()) {
                swapBEntry(position, positionParent);
                position = positionParent;
            } else {
                lazySetOfPosition(position);
                lazySetOfPosition(positionParent);
                break;
            }
        }
    }

    private void lazySetOfPosition(Integer position) {
        if (elements.get(position).getPosition() == null) {
            elements.get(position).setPosition(position);
        }
    }

    private void swapBEntry(Integer position, Integer positionParent) {
        elements.get(position).setPosition(positionParent);
        elements.get(positionParent).setPosition(position);

        BEntry<T> swap = elements.get(position);
        elements.set(position, elements.get(positionParent));
        elements.set(positionParent, swap);

    }

    public boolean structureIsAlright() {
        boolean check = true;
        for (int i = 0; i < size; i++) {
            int second = 2 * i + 2;
            int first = 2 * i + 1;
            int parent = (int) Math.floor((i - 1) / 2);
            check = calculateCheck(check, i, second, first, parent);
        }
        return check;
    }

    private boolean calculateCheck(boolean check, int i, int second, int first, int parent) {
        if (parent >= 0) {
            if (size > second) {
                check = checkStructure(i, first, second, parent);
            } else if (size > first) {
                check = checkStructure(i, first, parent);
            }
        }
        return check;
    }

    public boolean checkStructure(Integer index, Integer childIndexOne, Integer childIndexTwo, Integer parentIndex) {
        Double key = elements.get(index).getKey();
        if (checkChild(childIndexOne, key)
                && checkChild(childIndexTwo, key)
                && (index == parentIndex || checkParent(parentIndex, key))) {
            return true;
        } else {
            System.out.println("structure fail at:" + indexKeyPair(index) + ",with c1:" + indexKeyPair(childIndexOne)
                    + ",with c2:" + indexKeyPair(childIndexTwo) + " and with p:" + indexKeyPair(parentIndex));
            return false;
        }
    }

    public boolean checkStructure(Integer index, Integer childIndexOne, Integer parentIndex) {
        Double key = elements.get(index).getKey();
        if (checkChild(childIndexOne, key)
                && (index == parentIndex || checkParent(parentIndex, key))) {
            return true;
        } else {
            System.out.println("structure fail at:" + indexKeyPair(index) + ",with c1:" + indexKeyPair(childIndexOne)
                    + ",with no c2: and with p:" + indexKeyPair(parentIndex));
            return false;
        }
    }

    private boolean checkParent(Integer parentIndex, Double key) {
        return key > elements.get(parentIndex).getKey();
    }

    private boolean checkChild(Integer childIndexOne, Double key) {
        return key < elements.get(childIndexOne).getKey();
    }

    private String indexKeyPair(Integer index) {
        return "(" + index + "," + elements.get(index).getKey() + ")";
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("[realId:").append(i).append("]").append(elements.get(i).toString());
            if (MathHelper.log2(i + 2) == Math.floor(MathHelper.log2(i + 2))) {
                builder.append("\n");
            }
        }
        builder.append("\n");

        return "BinaryHeap{" +
                "size=" + size +
                ", elements=" + builder.toString() +
                '}';
    }
}
