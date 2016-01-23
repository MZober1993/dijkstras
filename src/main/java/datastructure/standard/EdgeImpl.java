package datastructure.standard;

import datastructure.AbstractEdge;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 20:17
 */
public class EdgeImpl extends AbstractEdge<Vertex> {

    public EdgeImpl(Vertex connected, Double distance) {
        super(connected, distance);
    }
}
