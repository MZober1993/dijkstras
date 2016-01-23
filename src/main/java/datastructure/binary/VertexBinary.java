package datastructure.binary;

import datastructure.AbstractElement;


/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 11:43
 */
public class VertexBinary extends AbstractElement {

    private Integer position;
    private Double key;

    public VertexBinary(Integer id, Double key) {
        super(id);
        this.key = key;
    }

    public void setKey(Double key) {
        this.key = key;
    }

    public Double getKey() {
        return key;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}