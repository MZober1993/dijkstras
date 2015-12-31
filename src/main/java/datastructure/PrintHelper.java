package datastructure;

import datastructure.fibo.Entry;
import datastructure.fibo.FibonacciHeap;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 31.12.15 - 10:55
 */
public class PrintHelper {
    public static String transformDouble(Double number) {
        return String.valueOf((number == Double.MAX_VALUE) ? "MAX" : number);
    }

    public static <T> String elemString(Entry<T> currentElement) {
        if (currentElement == null) {
            return "null";
        } else {
            return "(" + transformDouble(currentElement.getKey()) + ")"
                    + "[" + currentElement.getValue() + "]";
        }
    }

    public static <T> String printFibonacciHeap(FibonacciHeap<T> heap, Entry<T> startElement) {
        StringBuilder builder = new StringBuilder();
        if (heap.getSize() > 0) {
            Entry<T> currentElement = startElement;
            builder.append("[").append(startElement.getValue()).append("]:").append("\n");
            do {
                builder.append(entryWithoutParentsAndChildren(currentElement));
                builder.append("\n");
                currentElement = currentElement.getNext();
            } while (currentElement != startElement);
            builder.append("\n");
            do {
                if (currentElement.getChild() != null) {
                    builder.append("{").append(currentElement.getValue()).append("}").append("\n");
                    builder.append(printFibonacciHeap(heap, currentElement.getChild()));
                }
                currentElement = currentElement.getNext();
            } while (currentElement != startElement);
        } else {
            builder.append("heap is empty!\n");
        }
        return builder.toString();
    }

    private static <T> String entryWithoutParentsAndChildren(Entry<T> currentElement) {
        return "(" +
                transformDouble(currentElement.getKey()) + "," +
                currentElement.isMarked() + "," +
                currentElement.getDeg() + ")" +
                currentElement.getValue() + "|" +
                "l=" + elemString(currentElement.getPrevious()) + "|" +
                "r=" + elemString(currentElement.getNext());
    }

    public static <T> String transformEntry(Entry<T> currentElement) {
        return "(" +
                transformDouble(currentElement.getKey()) + "," +
                currentElement.isMarked() + "," +
                currentElement.getDeg() + ")" +
                currentElement.getValue() + "|" +
                "l=" + elemString(currentElement.getPrevious()) + "|" +
                "r=" + elemString(currentElement.getNext()) + "|" +
                "c=" + elemString(currentElement.getChild()) + "|" +
                "p=" + elemString(currentElement.getParent());
    }
}
