package datastructure;

import datastructure.fibo.ArrayHolder;
import datastructure.fibo.FibonacciHeap;
import datastructure.fibo.VertexFibo;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 31.12.15 - 10:55
 */
public class PrintHelper {
    public static String transformDouble(Double number) {
        return String.valueOf((number == Double.MAX_VALUE) ? "MAX" : number);
    }

    public static String elemString(VertexFibo currentElement) {
        if (currentElement == null) {
            return "       null        ";
        } else {
            return "(k:" + transformDouble(currentElement.getKey()) + ")" +
                    transformToFlatElement(currentElement);
        }
    }

    public static String currentElemString(VertexFibo currentElement) {
        if (currentElement == null) {
            return "       null        ";
        } else {
            return "(k:" + transformDouble(currentElement.getKey()) + ",deg:" + currentElement.getDeg() + ")" +
                    transformToFlatElement(currentElement);
        }
    }

    private static String transformToFlatElement(VertexFibo currentElement) {
        return "[id:" + currentElement.getId() + "]";
    }

    public static String printFibonacciHeap(FibonacciHeap heap, VertexFibo startElement) {
        StringBuilder builder = new StringBuilder();
        ArrayHolder holder = new ArrayHolder(heap);
        if (heap.getSize() > 0) {
            VertexFibo currentElement = startElement;
            builder.append("[").append(startElement.getValue()).append("]:").append("\n");
            do {
                builder.append(entryWithoutParentsAndChildren(holder, currentElement));
                builder.append("\n");
                currentElement = holder.getNext(currentElement);
            } while (currentElement != startElement);
            builder.append("\n");
            do {
                if (holder.getChild(currentElement) != null) {
                    builder.append("{").append(currentElement.getValue()).append("}").append("\n");
                    builder.append(printFibonacciHeap(heap, holder.getChild(currentElement)));
                }
                currentElement = holder.getNext(currentElement);
            } while (currentElement != startElement);
        } else {
            builder.append("heap is empty!\n");
        }
        return builder.toString();
    }

    private static String entryWithoutParentsAndChildren(ArrayHolder holder, VertexFibo currentElement) {
        return "(" +
                transformDouble(currentElement.getKey()) + "," +
                currentElement.isMarked() + "," +
                currentElement.getDeg() + ")" +
                currentElement.getValue() + "|" +
                "l=" + elemString(holder.getPrevious(currentElement)) + "|" +
                "r=" + elemString(holder.getNext(currentElement));
    }

    public static String transformEntry(VertexFibo currentElement, FibonacciHeap heap) {
        ArrayHolder holder = new ArrayHolder(heap);
        return elemString(holder.getPrevious(currentElement)) + "<-" +
                currentElemString(currentElement) + "->" +
                elemString(holder.getNext(currentElement)) + "|" +
                "c=" + elemString(holder.getChild(currentElement)) + "|" +
                "p=" + elemString(holder.getParent(currentElement));
    }
}
