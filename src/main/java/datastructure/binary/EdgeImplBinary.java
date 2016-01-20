package datastructure.binary;

import datastructure.AbstractEdge;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 20:17
 */
public class EdgeImplBinary extends AbstractEdge<VertexBinary> {

    public EdgeImplBinary(VertexBinary connected, Double distance) {
        super(connected, distance);
    }
}
