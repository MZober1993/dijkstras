package datastructure;

import datastructure.fibo.EdgeImplFibo;
import datastructure.standard.EdgeImpl;
import datastructure.standard.StandardEdge;

import java.util.Objects;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         18.11.15 - 13:15
 */
public class EdgeFactory<E extends Edge> {

    private Class<E> clazz;

    public EdgeFactory(Class<E> clazz) {
        this.clazz = clazz;
    }

    public E newInstance(Vertex one, Vertex two, Double distance) {
        if (Objects.equals(clazz.getName(), EdgeImpl.class.getName())) {
            return (E) new EdgeImpl(one, two, distance);
        } else if (Objects.equals(clazz.getName(), EdgeImplFibo.class.getName())) {
            return (E) new EdgeImplFibo(one, two, distance);
        } else {
            throw new RuntimeException("The given clazz is not an Edge-Class");
        }
    }
}
