package util;


import com.google.common.collect.ImmutableList;
import datastructure.Graph;
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
 *         On 2015 - 16.11.15 - 13:47
 */
public class GraphImporter {

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

    public GraphImpl importLinesOfFileAndGetSequentialGraph(Long limit) {
        return (GraphImpl) importLinesOfFile(limit, new GraphImpl());
    }

    private Graph importLinesOfFile(Long limit, Graph graph) {
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

    public GraphImpl importNVerticesAndGetSequentialGraph(Long n) {
        return (GraphImpl) importNVertices(n, new GraphImpl());
    }

    private Graph importNVertices(Long n, Graph graph) {
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

    public List<Graph> calculateParallelGraphsWithNVertices(Long... n) {
        ImmutableList.Builder<Graph> builder = ImmutableList.builder();
        for (Long number : n) {
            builder.add(importNVerticesAndGetSequentialGraph(number));
        }
        return builder.build();
    }
}