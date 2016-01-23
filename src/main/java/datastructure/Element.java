package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:24
 */
public interface Element {

    Boolean hasConnectionTo(Integer id);

    void isConnectedTo(Integer id);

    Integer getId();

    Element getAnchor();

    void setAnchor(Element element);

    boolean isClosed();

    void setClosed(boolean close);
}
