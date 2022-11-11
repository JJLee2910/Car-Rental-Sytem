package main.frontend.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

import java.text.DecimalFormat;

public class PieChartUtils {
    /**
     * functions generating new pie chart*/
    public static JFreeChart generateNewPieChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, true, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSimpleLabels(true);

        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0.00"), new DecimalFormat("0.00%"));
        plot.setLabelGenerator(gen);
        return chart;
    }
}
