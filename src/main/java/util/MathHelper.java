package util;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         24.11.15 - 14:16
 */
public class MathHelper {

    public static final double A_MILLION = 1000000.0;
    public static final double ONE_THOUSAND = 1000;
    public static final Long TEN_THOUSAND = 10000L;
    public static final int LOW_LIMIT_FOR_RAND = 200;
    public static final int HIGH_LIMIT_FOR_RAND = 1000;

    public static double calculateExpectancyValue(List<Long> values, Integer countOfMeasures) {
        return values.stream().mapToDouble(x -> x / countOfMeasures).sum();
    }

    public static double calculateStandardError(double expectancy, double expectancyWithXInPow) {
        double expectancyResultInPow = Math.pow(expectancy, 2);
        return Math.sqrt(expectancyWithXInPow - expectancyResultInPow);
    }

    public static double expectancyWithXInPow(List<Long> values, Integer countOfMeasures) {
        return values.stream().mapToDouble(x -> Math.pow(x, 2) / countOfMeasures).sum();
    }

    public static Stream<Double> scaleTimeValuesForPlot(Stream<Double> values) {
        return values.map(value -> value / A_MILLION);
    }

    public static Double scaleTimeValuesForPlot(Double value) {
        return value / A_MILLION;
    }

    public static Stream<Double> scaleNForPlot(Stream<Double> values) {
        return values.map(value -> value / ONE_THOUSAND);
    }

    public static Double scaleNForPlot(Integer value) {
        return value / ONE_THOUSAND;
    }

    public static int calculateRandomDistance() {
        return (int) (Math.random() * (HIGH_LIMIT_FOR_RAND - LOW_LIMIT_FOR_RAND)) + LOW_LIMIT_FOR_RAND;
    }

    public static int calculateRandomNodeId(Integer low, Integer high) {
        return (int) (Math.random() * (high - low)) + low;
    }

    public static int log2(int x) {
        if (x==0){
            return 0;
        }
        return (int) Math.ceil(Math.log(x) * 1.000000000001 / Math.log(2));
    }
}
