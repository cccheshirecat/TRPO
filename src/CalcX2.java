import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;


public class CalcX2 {


    private static final int a0 = 10;
    private static final int a1 = 40;
    private double X1 = 0;
    private double X2 = 0;
    private double X3 = 0;
    private double X1n = 0;
    private double X2n = 0;
    private double X3n = 0;
    private double X4 = 0;
    private double X4n = 0;
    private double Murd;
    //
    double secX1 = 0;
    double secX1n;
    double secX2 = 0;
    double secX2n;
    double secX3 = 0;
    double secX3n;
    double secX4 = 0;
    double secX4n;
    //
    // private float Mv;// возмущающий момент
    private static double T = 0.001;
    private float omega = 0.0f;
    private float omega1 = 0.0f;
    private float integralOmega = 0;
    private int f = 0;
    private double x1th;

    private void calcOmega1() {
        if (Math.abs(X4) < Data.omegaMax)
            calcX3();
        else if (Math.abs(X4) >= Data.omegaMax) X3 = 0.0;
    }

    private void calcX1n() {
        X1n = X2 * T + X1;
    }

    private void calcX1sec() {
        secX1n = secX2 * T + secX1;
    }

    private void calcX1th(double X1, double X2) {
        x1th = X1 + X2 * T;
    }

    private void calcX3() {
        X3 = a0 * X1 + a1 * X2;
    }

    private void calcX3sec() {
        secX3 = secX1 * 1 + 2 * secX2;
    }

    private void calcX2n() {
        calcOmega1();
        X2n = X2 + T * (-(Data.Jmx / Data.Jz) * X3 + Data.M_v / Data.Jz);
    }

    private void calcX2sec() {
        calcX3sec();
        if (secX3 > 0) {
            f = 1;
        } else if (secX3 <= 0) {
            f = -1;
        }
        Murd = Data.M_yrd * f;
        secX2n = secX2 + T * ((Data.M_t / Data.Jz) + (Data.M_v / Data.Jz)- Murd);
    }

    private void calcX4n() {
        X4n = X4 + X3 * T;
    }

    private void calcX4sec() {
        secX4n = secX4 - T * Data.omega1T;
    }

    private void reverse() {
        X1 = X1n;
        X1n = 0f;
        X2 = X2n;
        X2n = 0f;
        X4 = X4n;
        X4n = 0f;
    }

    private void reverse2() {
        secX1 = secX1n;
        secX1n = 0f;
        secX2 = secX2n;
        secX2n = 0f;
        secX4 = secX4n;
        secX4n = 0f;
    }

    public XYDataset calc() throws IOException {
        int i = 0;
        BigDecimal temp;
        XYSeries X1S = new XYSeries("X1");
        XYSeries X2S = new XYSeries("X2");
        XYSeries X3S = new XYSeries("X3");
        XYSeries X4S = new XYSeries("X4");
        FileWriter fw = new FileWriter("out2.txt");
        //while (X4 <= 900f) {
        for (int j = 0; j < 1350000; ) {

            while (j < 450000) {
                // System.out.println(i);
                calcX2n();
                calcX1n();
                calcX4n();
                //X1S.add(T * j, X1);

                    // System.out.println(T*j+"\t"+j);
                    // System.out.println("A"+0);
                    //   X1S.add(T * j, X1*100);
                    X2S.add(T * j, X2);
                    //  X3S.add(T * j, X3);
                    //X4S.add(T * j, X4);

                reverse();
                //i++;
                //System.out.println(X4);
                //System.out.println(i);
                j++;
            }
            i=0;
            secX1=X1;
            secX2=X2;
            secX3=X3;
            secX4=X4;
            while (j>=450000 && j<460000){
                calcX2sec();
                calcX1sec();
                calcX4sec();
                if (i==5006) {
                    X2S.add(T * j, secX2);
                    i=0;
                }
                reverse2();
                j++;
                i++;
            }
            i=0;
            secX1=0;
            
            secX3=0;
            secX4=0;
            while (j>=460000 && j<1350000){
                calcX2sec();
                calcX1sec();
                calcX4sec();
                if (i==5006) {
                    X2S.add(T * j, secX2*10);
                    i=0;
                }
                reverse2();
                j++;
                i++;
            }



        }
        XYSeriesCollection x1SC = new XYSeriesCollection();
        // x1SC.addSeries(X1S);
        x1SC.addSeries(X2S);
        // x1SC.addSeries(X3S);
        // x1SC.addSeries(X4S);
        System.out.println(X4);
        // System.out.println(i);
        System.out.println((X4) * 0.02);
        return x1SC;
    }
}


