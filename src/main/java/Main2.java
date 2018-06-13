import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main2 {
    public static void main(String[] args) {
        int size = 10;
        List<List<Integer>> board =

                IntStream.range(0, size).boxed().filter(i -> i % 3 == 0)
                        .map(i -> IntStream.range(i, size).boxed()
                                .collect(Collectors.toList())).collect(Collectors.toList());

        System.out.println("board = " + board);
    }
}
