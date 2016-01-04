package util;


import datastructure.Graph;

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
public class GraphImporter<T extends Graph> {

    public static final int FIRST_VERTEX = 1;
    public static final int SECOND_VERTEX = 2;
    public static final int DISTANCE = 3;
    private Path pathOfGraphFile;
    public static final String PATH_TO_RESOURCE = "./src/main/resources/";
    public static final Path PATH_TO_IMPORT_FILE_DIR = Paths.get("./src/main/resources/ImportFiles");
    public static final String PATH_TO_IMPORT_FILES = "./src/main/resources/ImportFiles/";

    public GraphImporter(ImportFile file) {
        this.pathOfGraphFile = Paths.get(PATH_TO_IMPORT_FILES + file.name().toLowerCase());
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
        fileCreator.createSampleGraphFile();
    }

    public T importLinesOfFileAndGetGraph(Long limit, T graph) {
        try {
            Stream<String> lines = Files.lines(pathOfGraphFile).filter(line -> line.contains("a "));
            List<List<String>> lineList = lines.map(line -> Arrays.asList(line.split(" "))).collect(Collectors.toList());

            lineList.stream().limit(limit).forEach(
                    list -> graph.addConnection(
                            Integer.valueOf(list.get(FIRST_VERTEX)), Integer.valueOf(list.get(SECOND_VERTEX)),
                            Double.valueOf(list.get(DISTANCE))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graph;
    }

    public T importNVerticesAndGetGraph(Long n, T graph) {
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

}