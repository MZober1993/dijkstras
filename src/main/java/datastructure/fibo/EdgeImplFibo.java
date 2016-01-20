package datastructure.fibo;

import datastructure.AbstractEdge;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 20:17
 */
public class EdgeImplFibo extends AbstractEdge<VertexFibo> {

    public EdgeImplFibo(VertexFibo connected, Double distance) {
        super(connected, distance);
    }
}
