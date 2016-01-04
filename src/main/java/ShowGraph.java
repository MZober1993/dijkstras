import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphFactory;
import datastructure.fibo.Entry;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.GraphImpl;
import util.GraphImporter;
import util.ImportFile;

import static util.ImportFile.NY;
import static util.ImportFile.SAMPLE;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         16.11.15 - 13:48
 */
public class ShowGraph {

    public static void main(String[] args) {
        GraphFactory<GraphImpl, Element> stdFactory = new GraphFactory<>(GraphImpl.class);
        createAndShow(NY, 20L, stdFactory);
        createAndShow(SAMPLE, 20L, stdFactory);

        GraphFactory<GraphImplFibo, Entry<Element>> fiboFactory = new GraphFactory<>(GraphImplFibo.class);
        createAndShow(NY, 20L, fiboFactory);
        createAndShow(SAMPLE, 20L, fiboFactory);
    }

    private static <G extends Graph<T>, T> void createAndShow(ImportFile file, Long limit, GraphFactory<G, T> factory) {
        GraphImporter<G> graphImporterSample = new GraphImporter<>(file);
        System.out.println(graphImporterSample.importNVerticesAndGetGraph(limit, factory.create()));
    }
}
