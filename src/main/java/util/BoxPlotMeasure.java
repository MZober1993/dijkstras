package util;

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

    public Double getQuartile0_25() {
        return quartile0_25;
    }

    public Double getMedian() {
        return median;
    }

    public Double getQuartile0_75() {
        return quartile0_75;
    }
}
