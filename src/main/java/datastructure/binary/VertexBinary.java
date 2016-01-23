package datastructure.binary;

import datastructure.Element;
import datastructure.ElementContainer;


/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 11:43
 */
public class VertexBinary extends ElementContainer {

    private Integer position;

    public VertexBinary(Element value, Double key) {
        super(value);
        value.setKey(key);
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}