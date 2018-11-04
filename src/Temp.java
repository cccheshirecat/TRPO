import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;


public class Temp extends ApplicationFrame {
    public Temp() throws IOException {
        super("XY Line Chart Example with JFreechart");
        Computing computing=new Computing();
        // System.out.println(0.0/0.0);
        //JFreeChart jFreeChart= ChartFactory.createXYLineChart("","","",computing.calculation());
        JFreeChart jFreeChart1=ChartFactory.createXYLineChart("","","",computing.calculation(), PlotOrientation.VERTICAL,true,true,false);
        ChartPanel chartPanel=new ChartPanel(jFreeChart1);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        setContentPane(chartPanel);
        this.pack();


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                   Temp temp=new Temp();
                   temp.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
