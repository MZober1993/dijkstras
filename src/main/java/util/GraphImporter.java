package util;


import datastructure.Element;
import datastructure.GraphHelper;
import datastructure.binary.GraphBinary;
import datastructure.fibo.GraphFibo;
import datastructure.standard.GraphImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         16.11.15 - 13:47
 */
public class GraphImporter<T extends Element> {

    public static final int FIRST_VERTEX = 1;
    public static final int SECOND_VERTEX = 2;
    public static final int DISTANCE = 3;
    private Path pathOfGraphFile;
    public static final String PATH_TO_RESOURCE = "./src/main/resources/";
    public static final Path PATH_TO_IMPORT_FILE_DIR = Paths.get("./src/main/resources/ImportFiles");
    public static final String PATH_TO_IMPORT_FILES = "./src/main/resources/ImportFiles/";
    public Long limit = 0L;

    public GraphImporter(ImportFile file) {
        this.pathOfGraphFile = Paths.get(PATH_TO_IMPORT_FILES + file.name().toLowerCase());
    }

    public GraphImporter(ImportFile file, Long limit) {
        this(file);
        this.limit = limit;
    }

    private void createDirAndPlainImportFile() {
        try {
            if (!Files.exists(PATH_TO_IMPORT_FILE_DIR)) {
                Files.createDirectory(PATH_TO_IMPORT_FILE_DIR);
            }
            Files.createFile(GraphFileCreator.DEFAULT_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphFileCreator fileCreator = new GraphFileCreator(GraphFileCreator.DEFAULT_PATH);
        fileCreator.createSampleGraphFile(13000);
    }


    public GraphImpl importElementGraph(Long n) {
        GraphImpl graph = new GraphImpl();
        graph.init(Math.toIntExact(n));
        if (!Files.exists(pathOfGraphFile)) {
            createDirAndPlainImportFile();
        }

        try {
            Stream<String> lines = Files.lines(pathOfGraphFile).filter(line -> line.contains("a "));
            List<List<String>> lineList = lines.map(line -> Arrays.asList(line.split(" "))).collect(Collectors.toList());

            lineList.stream().filter(list -> Integer.valueOf(list.get(FIRST_VERTEX)) <= n
                    && Integer.valueOf(list.get(SECOND_VERTEX)) <= n)
                    .forEach(list -> graph.addConnection(
                            Integer.valueOf(list.get(FIRST_VERTEX)), Integer.valueOf(list.get(SECOND_VERTEX)),
                            Double.valueOf(list.get(DISTANCE))));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public GraphFibo importEntryGraph(Long n) {
        return GraphHelper.transformGraphToFiboGraph(importElementGraph(n));
    }

    public GraphBinary importBinaryGraph(Long n) {
        return GraphHelper.transformGraphToBinaryGraph(importElementGraph(n));
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}