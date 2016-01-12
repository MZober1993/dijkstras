package util.writer;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 19:44
 */
public class BoxPlotMeasure {

    private Double quartile0_25;
    private Double median;
    private Double quartile0_75;

    public BoxPlotMeasure(Double quartile0_25, Double median, Double quartile0_75) {
        this.quartile0_25 = quartile0_25;
        this.median = median;
        this.quartile0_75 = quartile0_75;
    }

    public Double getAQuarter() {
        return quartile0_25;
    }

    public Double getMedian() {
        return median;
    }

    public Double getAThreeQuarter() {
        return quartile0_75;
    }

    public Double getA() {
        return getAQuarter() - 1.5 * (getAThreeQuarter() - getAQuarter());
    }

    public Double getB() {
        return getAThreeQuarter() + 1.5 * (getAThreeQuarter() - getAQuarter());
    }

    public Double getCountBetween0_25And0_75(List<Long> measureList) {
        return (double) measureList.stream()
                .filter(elem -> getAQuarter() <= elem && elem <= getAThreeQuarter()).count();
    }

    public Double getCountBetweenAAndB(List<Long> measureList) {
        return (double) measureList.stream()
                .filter(elem -> getA() <= elem && elem <= getB()).count();
    }
}
