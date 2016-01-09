package util;

import algorithm.Dijkstra;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.GraphImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

import static util.MathHelper.A_MILLION;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         19.11.15 - 15:07
 */
public class FileWriter {

    private Path path;

    FileWriter(Path path) {
        this.path = path;
    }

    public void writeNewLine() throws IOException {
        writeString("\n");
    }

    public void writeComma() throws IOException {
        writeString(", ");
    }

    public void writeString(String string) throws IOException {
        Files.write(path, String.format(string).getBytes(), StandardOpenOption.APPEND);
    }

    public void writeTimeWithScale(long time, Double timeScale) throws IOException {
        if (timeScale >= 1) {
            writeDouble(time / timeScale);
        } else {
            throw new RuntimeException("Error by scaling Time with timeScale:" + timeScale +
                    ", by using writeTimeWithLastRandomAndGetTime()");
        }
    }

    public <T> void tryToWrite(T value) throws IOException {
        if (value instanceof Double) {
            writeDouble((Double) value);
        } else if (value instanceof Integer) {
            writeInteger((Integer) value);
        } else if (value instanceof Long) {
            writeLong((Long) value);
        } else if (value instanceof String) {
            writeString((String) value);
        } else {
            writeString(value.toString());
        }
    }

    public void writeLong(long longValue) throws IOException {
        Files.write(path, ("" + longValue).getBytes(), StandardOpenOption.APPEND);
    }

    public void writeDouble(double doubleValue) throws IOException {
        Files.write(path, ("" + doubleValue).getBytes(), StandardOpenOption.APPEND);
    }

    public void writeInteger(Integer times) throws IOException {
        Files.write(path, ("" + times).getBytes(), StandardOpenOption.APPEND);
    }

    public <T> void writeList(List<T> list) throws IOException {
        list.stream().forEach(element -> {
            try {
                tryToWrite(element);
                boolean isLastElement = list.get(list.size() - 1) != element;
                if (isLastElement) {
                    writeComma();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    protected void writeGraphNumbers(int numberOfVertices, int numberOfEdges, boolean scaledN) throws IOException {
        if (scaledN) {
            writeDouble(MathHelper.scaleNForPlot(numberOfVertices));
            writeComma();
            writeDouble(MathHelper.scaleNForPlot(numberOfEdges));
            writeComma();
        } else {
            writeDouble(numberOfVertices);
            writeComma();
            writeDouble(numberOfEdges);
            writeComma();
        }
    }

    protected void tNWriteOfBoth(Integer times, boolean scaledN, Stream.Builder<Long> stdBuilder
            , Stream.Builder<Long> fiboBuilder, GraphImpl graph, int n, int m) throws IOException {
        GraphImplFibo fiboGraph = GraphHelper.transformGraphToEntryGraph(graph);
        DijkstraImpl stdAlgo = new DijkstraImpl();
        DijkstraImplFibo fiboAlgo = new DijkstraImplFibo();

        for (int i = 0; i < times - 1; i++) {
            writeGraphNumbers(n, m, scaledN);

            writingAndSaveTime(stdAlgo, stdBuilder, graph);
            writeComma();

            writingAndSaveTime(fiboAlgo, fiboBuilder, fiboGraph.refreshGraph());
            emptyEnd();
        }

        writeGraphNumbers(n, m, scaledN);

        writingAndSaveTime(stdAlgo, stdBuilder, graph);
        writeComma();

        writingAndSaveTime(fiboAlgo, fiboBuilder, fiboGraph.refreshGraph());
        writeComma();
    }

    protected void emptyEnd() throws IOException {
        writeComma();
        writeComma();
        writeComma();
        writeNewLine();
    }

    protected <T extends Element> void writingAndSaveTime(Dijkstra<T> algo, Stream.Builder<Long> builder
            , Graph<T> graph) throws IOException {
        long stdTime = GraphHelper.calculateTimeWithLastRandom(graph, algo);
        builder.add(stdTime);
        writeTimeWithScale(stdTime, A_MILLION);
    }

    protected void writeScaledGraphHeaderWithRest(boolean scaledN, String rest) throws IOException {
        if (scaledN) {
            Files.write(getPath(), ("|V|*[1000]" +
                    ",|E|*[1000]" +
                    rest)
                    .getBytes());
        } else {
            Files.write(getPath(), ("|V|" +
                    ",|E|" +
                    rest)
                    .getBytes());
        }
    }
}
