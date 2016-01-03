package datastructure;

import java.util.Optional;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         18.11.15 - 13:15
 */
public class EdgeFactory<E extends Edge<T>, T> {

    private Class<E> clazz;

    public EdgeFactory(Class<E> clazz) {
        this.clazz = clazz;
    }

    public E newInstance(T one, T two, Double distance) {
        E edge;
        try {
            System.out.println(clazz);
            //TODO: 10 times EdgeImplFibo then EdgeImpl, fix this
            edge = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("InstantiationException: The given clazz is not an Edge-Class");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException: The given clazz is not an Edge-Class");
        }
        edge.initEdge(one, two, distance);
        if (!Optional.ofNullable(edge).isPresent()) {
            throw new RuntimeException("Not instantiated Edge.");
        }
        return edge;
    }
}
