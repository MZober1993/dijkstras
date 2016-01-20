package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:24
 */
public interface Element extends Comparable<Element> {

    Boolean hasConnectionTo(Integer id);

    void isConnectionTo(Integer id);

    Integer getId();

    void setG(Double g);

    Double getG();

    Element getAnchor();

    void setAnchor(Element element);

    boolean isClosed();

    void setClosed(boolean close);

    @Override
    default int compareTo(Element element) {
        checkNotNull(element);
        return this.getG() < element.getG()
                ? -1 : this.getG() == element.getG()
                ? 0 : 1;
    }
}
