package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 04.01.16 - 13:24
 */
public class GraphFactory<G extends Graph<T>, T> {

    private Class<G> clazz;

    public GraphFactory(Class<G> clazz) {
        this.clazz = clazz;
    }

    public G create() {
        return create(this.clazz);
    }

    public static <T, G extends Graph<T>> G create(Class<G> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Error with the Instantiation of Graph in Factory");
    }
}
