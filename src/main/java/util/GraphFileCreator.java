package util;

import util.writer.FileWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         25.11.15 - 13:04
 */
public class GraphFileCreator extends FileWriter {

    public static final int COMPLETE_LIMIT = 800;

    public static final Path DEFAULT_PATH = Paths.get(GraphImporter.PATH_TO_IMPORT_FILES +
            ImportFile.CREATED.name().toLowerCase());

    public static final Path COMPLETE_PATH = Paths.get(GraphImporter.PATH_TO_IMPORT_FILES +
            ImportFile.COMPLETE.name().toLowerCase());


    public GraphFileCreator(Path path) {
        super(path);
    }

    public void createSampleGraphFile(int limit) {
        createFile(limit, 5, 100);
    }

    public void createCompleteConnectedGraphFile(int limit, int begin) {
        Path path = getPath();
        try {
            setPath(COMPLETE_PATH);
            Files.write(COMPLETE_PATH, "".getBytes());
            for (int i = begin; i <= limit; i++) {
                for (int j = begin; j <= limit; j++) {
                    if (i != j && i < j) {
                        int r = MathHelper.calculateRandomDistance();
                        writeConnectionInOneDirection(i, j, r);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPath(path);
    }

    public void createFile(int n, int countOfSectors, int connectivity) {
        try {
            writeHeader();
            equalDistribution(n, countOfSectors, connectivity);
            //cumulativeDistribution(countOfVertices);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void equalDistribution(Integer n, Integer countOfSectors, Integer connectivity) throws IOException {
        int sectorLimit = n / countOfSectors;
        int tmpLimit = 0;
        int nextLimit;
        int start;

        for (int j = 1; j < countOfSectors + 1; j++) {
            start = sectorLimit * (j - 1) + 1;
            nextLimit = sectorLimit * j;

            for (int i = start; i < nextLimit; i++) {
                if (tmpLimit < nextLimit) {
                    if (i == start) {
                        tmpLimit = i + connectivity;
                        calculateConnectionsAndWrite(i, i + 1, tmpLimit);
                    } else {
                        if (tmpLimit + connectivity > nextLimit - 1) {
                            calculateConnectionsAndWrite(i, tmpLimit, nextLimit + 1);
                            tmpLimit += connectivity;
                        } else {
                            calculateConnectionsAndWrite(i, tmpLimit, tmpLimit + connectivity);
                            tmpLimit += connectivity;
                        }
                    }
                }
            }
            writeNewLine();

            if (j != countOfSectors) {
                writeConnectionBetweenSectors(start, nextLimit, sectorLimit * (j + 1));
                writeNewLine();
            }
        }
    }

    private void writeConnectionBetweenSectors(int start, int nextLimit, int nextNextLimit) throws IOException {
        writeConnection(
                MathHelper.calculateRandomNodeId(start, nextLimit),
                MathHelper.calculateRandomNodeId(nextLimit + 1, nextNextLimit),
                MathHelper.calculateRandomDistance()
        );
    }

    private void calculateConnectionsAndWrite(int i, int lastLimit, int limit) {
        IntStream.range(lastLimit, limit)
                .forEach(element -> {
                    int r = MathHelper.calculateRandomDistance();
                    try {
                        writeConnection(i, element, r);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private void writeConnection(int i, int element, int r) throws IOException {
        writeConnectionInOneDirection(i, element, r);
        //writeConnectionInOneDirection(element, i, r);
    }

    private void writeConnectionInOneDirection(int first, int second, int distance) throws IOException {
        writeString("a " + first + " " + second + " " + distance + "\n");
    }

    private void cumulativeDistribution(Integer countOfVertices, Integer countOfSectors, Integer scale) {
        double sectorLimit = countOfVertices / countOfSectors;
        for (int j = 1; j < countOfVertices + 1; j++) {
            System.out.println(j);
            if ((j % sectorLimit) == 0) {
                sectorLimit = Math.ceil(sectorLimit * 1.5);
                System.out.println();
            }
        }
    }

    protected void writeHeader() throws Exception {
        Files.write(DEFAULT_PATH, "".getBytes());
    }
}
