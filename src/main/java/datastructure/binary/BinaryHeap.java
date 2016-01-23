package datastructure.binary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 11:34
 */
public class BinaryHeap {

    private Integer size = 0;
    private List<VertexBinary> elements = new ArrayList<>();

    public VertexBinary insert(VertexBinary entry) {
        elements.add(entry);
        heapify(size);

        size++;
        return entry;
    }

    public VertexBinary extractMin() {
        VertexBinary entry = elements.get(0);
        entry.setPosition(null);
        elements.set(0, elements.get(size - 1));

        size--;
        heapifyDown(0);
        return entry;
    }

    public void decreaseKey(VertexBinary entry, Double key) {
        if (elements.get(entry.getPosition()).getKey() > key) {
            entry.setKey(key);
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

        VertexBinary swap = elements.get(position);
        elements.set(position, elements.get(positionParent));
        elements.set(positionParent, swap);
    }

    public Integer getSize() {
        return size;
    }

    public List<VertexBinary> getElements() {
        return elements;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append("[realId:").append(i).append("]").append(elements.get(i).toString()).append("\n");
        }
        return "BinaryHeap{" +
                "edgeSize=" + size +
                ", elements=" + builder.toString() +
                '}';
    }
}
