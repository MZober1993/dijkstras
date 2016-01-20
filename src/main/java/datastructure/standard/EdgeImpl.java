package datastructure.standard;

import datastructure.AbstractEdge;
import datastructure.Element;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 20:17
 */
public class EdgeImpl extends AbstractEdge<Element> {

    public EdgeImpl(Element connected, Double distance) {
        super(connected, distance);
    }
}
