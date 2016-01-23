package datastructure.fibo;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 23.01.16 - 00:32
 */
public class FiboArrayHolder {

    private FibonacciHeap heap;

    public FiboArrayHolder(FibonacciHeap heap) {
        this.heap = heap;
    }

    public Integer getNext(int id) {
        return getFollowingId(id, heap.getNexts());
    }

    public void setNext(int current, int next) {
        setFollowingId(current, next, heap.getNexts());
    }

    public Integer getPrevious(int id) {
        return getFollowingId(id, heap.getPrevious());
    }

    public void setPrevious(int current, int next) {
        setFollowingId(current, next, heap.getPrevious());
    }

    public Integer getChild(int id) {
        return getFollowingId(id, heap.getChilds());
    }

    public void setChild(int current, int next) {
        setFollowingId(current, next, heap.getChilds());
    }

    public Integer getParent(int id) {
        return getFollowingId(id, heap.getParents());
    }

    public void setParent(int current, int next) {
        setFollowingId(current, next, heap.getParents());
    }

    public VertexFibo getNext(VertexFibo elem) {
        return getFollowingElem(heap.getGraph(), elem.getId(), heap.getNexts());
    }

    public void setNext(VertexFibo current, VertexFibo next) {
        setFollowingElem(current, next, heap.getNexts());
    }

    public VertexFibo getPrevious(VertexFibo elem) {
        return getFollowingElem(heap.getGraph(), elem.getId(), heap.getPrevious());
    }

    public void setPrevious(VertexFibo current, VertexFibo next) {
        setFollowingElem(current, next, heap.getPrevious());
    }

    public VertexFibo getChild(VertexFibo elem) {
        return getFollowingElem(heap.getGraph(), elem.getId(), heap.getChilds());
    }

    public void setChild(VertexFibo current, VertexFibo next) {
        setFollowingElem(current, next, heap.getChilds());
    }

    public VertexFibo getParent(VertexFibo elem) {
        return getFollowingElem(heap.getGraph(), elem.getId(), heap.getParents());
    }

    public void setParent(VertexFibo current, VertexFibo next) {
        setFollowingElem(current, next, heap.getParents());
    }

    public static VertexFibo getFollowingElem(GraphFibo graph, Integer id, Integer[] elemHolder) {
        Integer following = elemHolder[id];
        if (following == null) {
            return null;
        }
        return graph.getV(following);
    }

    public static void setFollowingElem(VertexFibo current, VertexFibo next, Integer[] elemHolder) {
        elemHolder[current.getId()] = next.getId();
    }

    public static <T> void setFollowingId(Integer current, T next, T[] elemHolder) {
        elemHolder[current] = next;
    }

    public static <T> T getFollowingId(Integer id, T[] elemHolder) {
        return elemHolder[id];
    }
}
