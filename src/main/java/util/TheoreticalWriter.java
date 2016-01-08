package util;

import datastructure.Element;
import datastructure.standard.GraphImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static util.MathHelper.scaleTimeValuesForPlot;
import static util.Measures.THEORETICAL;
import static util.Measures.scaleMeasureSample;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         26.11.15 - 20:35
 */
public class TheoreticalWriter extends FileWriter {

    public static final Path DEFAULT_PATH = Paths.get(GraphImporter.PATH_TO_RESOURCE + THEORETICAL.name().toLowerCase()
            + ".csv");

    public TheoreticalWriter(Path path) {
        super(path);
    }

    public void writeRoutine() {
        GraphImporter<Element> graphImporter = new GraphImporter<>(ImportFile.CREATED);
        try {
            writeHeader();
            scaleMeasureSample(100).forEach(element -> {
                GraphImpl graph = graphImporter.importElementGraph(element);
                int m = graph.getEdges().size();
                int n = graph.getElements().size();
                writeTn(n, (m + n) * Math.log(n) * n, 2 * n + 2 * n * Math.log(n) + m + m * n * Math.log(n) * 2);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTn(int n, double firstTheoreticalTN, double secondTheoreticalTN) {
        try {
            writeInteger(n);
            writeComma();
            writeDouble(scaleTimeValuesForPlot(firstTheoreticalTN));
            writeComma();
            writeDouble(scaleTimeValuesForPlot(secondTheoreticalTN));
            writeNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeHeader() throws IOException {
        Files.write(getPath(), "n, T(n) first,T(n) second\n".getBytes());
    }
}
