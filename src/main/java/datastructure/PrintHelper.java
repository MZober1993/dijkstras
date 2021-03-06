package datastructure;

import datastructure.fibo.FiboArrayHolder;
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

    public static String elemString(FibonacciHeap heap, VertexFibo currentElement) {
        if (currentElement == null) {
            return "       null        ";
        } else {
            return "(k:" + transformDouble(currentElement.getKey()) + ")" +
                    transformToFlatElement(currentElement);
        }
    }

    public static String currentElemString(VertexFibo currentElement, FibonacciHeap heap) {
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
        FiboArrayHolder holder = new FiboArrayHolder(heap);
        if (heap.getSize() > 0) {
            VertexFibo currentElement = startElement;
            builder.append("[").append(startElement).append("]:").append("\n");
            do {
                builder.append(entryWithoutParentsAndChildren(holder, heap, currentElement));
                builder.append("\n");
                currentElement = holder.getNext(currentElement);
            } while (currentElement != startElement);
            builder.append("\n");
            do {
                if (holder.getChild(currentElement) != null) {
                    builder.append("{").append(currentElement).append("}").append("\n");
                    builder.append(printFibonacciHeap(heap, holder.getChild(currentElement)));
                }
                currentElement = holder.getNext(currentElement);
            } while (currentElement != startElement);
        } else {
            builder.append("heap is empty!\n");
        }
        return builder.toString();
    }

    private static String entryWithoutParentsAndChildren(FiboArrayHolder holder, FibonacciHeap heap
            , VertexFibo currentElement) {
        return "(" +
                transformDouble(currentElement.getKey()) + "," +
                currentElement.isMarked() + "," +
                currentElement.getDeg() + ")" +
                currentElement + "|" +
                "l=" + elemString(heap, holder.getPrevious(currentElement)) + "|" +
                "r=" + elemString(heap, holder.getNext(currentElement));
    }

    public static String transformEntry(VertexFibo currentElement, FibonacciHeap heap) {
        FiboArrayHolder holder = new FiboArrayHolder(heap);
        return elemString(heap, holder.getPrevious(currentElement)) + "<-" +
                currentElemString(currentElement, heap) + "->" +
                elemString(heap, holder.getNext(currentElement)) + "|" +
                "c=" + elemString(heap, holder.getChild(currentElement)) + "|" +
                "p=" + elemString(heap, holder.getParent(currentElement));
    }
}
