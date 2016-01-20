package datastructure.binary;

import datastructure.Element;
import datastructure.ElementContainer;


/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 11:43
 */
public class VertexBinary extends ElementContainer {

    private Double key;
    private Integer position;

    public VertexBinary(Element value, Double key) {
        super(value);
        this.key = key;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Double getKey() {
        return key;
    }

    public void setKey(Double key) {
        this.key = key;
    }
}