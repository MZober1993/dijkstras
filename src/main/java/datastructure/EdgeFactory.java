package datastructure;

import datastructure.standard.EdgeImpl;

import java.util.Objects;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 18.11.15 - 13:15
 */
public class EdgeFactory<E extends Edge> {

    private Class<E> clazz;

    public EdgeFactory(Class<E> clazz) {
        this.clazz = clazz;
    }

    public Edge newInstance(Vertex one, Vertex two, Double distance) {
        if (Objects.equals(clazz.getName(), EdgeImpl.class.getName())) {
            return new EdgeImpl(one, two, distance);
        } else {
            throw new RuntimeException("The given clazz is not an Edge-Class");
        }
    }
}
