package org.example.view;

import lombok.Getter;
import org.example.controller.RoomStatisticsController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;

import javax.swing.*;
import java.awt.*;

@Getter
public class RoomStatisticsView extends JPanel implements Observer {
    private RoomStatisticsController controller;

    public RoomStatisticsView() {
        this.controller = new RoomStatisticsController();
        initUI();
    }

    private void initUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        update(); // Initially draw the chart
    }

    public void update() {
        removeAll(); // Clear previous chart
        var dataset = controller.getRoomStatisticsData();
        JFreeChart chart = ChartFactory.createPieChart("Room Statistics", dataset, true, true, false);
        var plot = (PiePlot) chart.getPlot();
        plot.setSimpleLabels(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
        add(chartPanel);
        validate();
    }
}
