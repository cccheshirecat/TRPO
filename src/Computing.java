import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Computing implements Data {
    private static final int a0 = 10;
    private static final int a1 = 40;
    private float X1 = 0.0f;
    private float X2 = 0.0f;
    private float X3 = 0.0f;
    private float Xn1 = 0.0f;
    private float Xn2 = 0.0f;
    private float Xn3 = 0.0f;
    private float X4 = 0.0f;
    private float Xn4 = 0.0f;
    //
    float secX1 = X1;
    float secX1n;
    float secX2 = X2;
    float secX2n;
    float secX3 = X3;
    float secX3n;
    //
    private Collection x1coll = new ArrayList<Float>();
    private Collection x2coll = new ArrayList<Float>();
    private Collection x3coll = new ArrayList<Float>();
    private Collection x4coll = new ArrayList<Float>();
    // private float Mv;// возмущающий момент
    private static float T = 0.001f;
    private float omega = 0.0f;
    private float omega1 = 0.0f;
    private float integralOmega = 0;

    private void calcX3() {
        Xn3 = a0 * X1 + a1 * X2;
    }

    private void calcXn1() {
        Xn1 = X1 + Xn2 * T;
    }

    private void integralOmega() {
        integralOmega = omega1 * T;
    }

    private void calcOmega1() {
        if (omega < Data.omegaMax)
            calcX3();
        else if (omega >= Data.omegaMax) omega1 = 0.0f;
    }

    /*  private void calcMv(){
          Mv=Data.Jz*+Data.Jmx*
      }*/
    private void calcXn2() {

        Xn2 = X2 + T * (-(Data.Jmx / Data.Jz) * X3 + Data.M_v / Data.Jz);
    }

    private void calcXn4() {
        Xn4 = X4 + T * X3;
    }

    private void reverse() {
        X1 = Xn1;
        Xn1 = 0.0f;
        X2 = Xn2;
        Xn2 = 0.0f;
        X3 = Xn3;
        Xn3 = 0.0f;
        X4 = Xn4;
        Xn4 = 0.0f;
    }

    private void reverse2() {
        secX1 = secX1n;
        secX1n = 0.0f;
        secX2 = secX2n;
        secX2n = 0.0f;
        secX3 = secX3n;
        secX3n = 0.0f;
    }

    private void calcSecX1n() {
        secX1n = secX1 + secX2n * T;
        // secX1n*=100;
    }

    private int f() {
        if (secX3 > 0) {
           // secX3n = 1;
            return 1;
        } else {
           // secX3n = -1;
            return -1;
        }
    }

    private void calcSecX2n() {
        secX2n = secX2 + T * ((Data.M_v / Data.Jz) - 0.001f - 0.004f * f());
        // secX2n = secX2 + T * (-(Data.Jmx / Data.Jz) * secX3 + 0.0001f);
        //secX2n*=100;
    }


    private void calcSecX3n2() {
        secX3n = 1 * secX1 + 2 * secX2;
        // secX3n*=100;
    }

    public XYDataset calculation() throws IOException {
        File file = new File("out.txt");
        XYSeries X1S = new XYSeries("X1");
        XYSeries X2S = new XYSeries("X2");
        XYSeries X3S = new XYSeries("X3");
        XYSeries X4S = new XYSeries("X4");
        double p = 0.01;
        FileWriter fw = new FileWriter(file);//  Formatter formatter =new Formatter(file);
        for (int i = 0; i < 450000; i++) {
            //    System.out.printf("X1: %.10f X2: %.10f X3: %.10f  X4: %.10f  T: %.5f  i:%4d \n", X1, X2, X3, X4, T, i);
            //    formatter.format("X1: %f X2: %f X3: %f  T: %.4f\n",X1,X2,X3,T);
            //  formatter.flush();
            //  fw.write("X1: " + String.format("%.6f", X1) + "\t X2: " + String.format("%.6f", X2) + "\t X3: " + String.format("%.6f", X3) + "\t X4: " + String.format("%.6f", X4) + "\t i" + i + "\n");

            calcX3();
            calcXn2();
            calcXn1();
            calcXn4();
            X1S.add(T * i, X1);
            X2S.add(T * i, X2);
            X3S.add(T * i, X3);
            X4S.add(T * i, X4 / 249.7);
            reverse();
            //  T+=0.001;
        }
        float x33 = X3;
        float xn33 = 0.0f;
       /* for (int i = 450000; i < 1350000; i++) {
            if (x33<=0){
                x33=a0*X1+a1*X2;
            }else {
                x33 = -a0 * X1 - a1 * X2;
            }
            x3coll.add(x33);
            X3S.add(T*i,x33);

        }*/
        ///second
        secX1 = X1;
        secX2 = X2;
        secX3 = X3;
        for (int i = 450000; i < 1350000; i++) {
            // if (secX3 != 0) {
            // System.out.printf("X1: %.10f X2: %.10f X3: %.10f  i:%4d \n", secX1, secX2, secX3, i);
            fw.write("X1: " + String.format("%.6f", secX1) + "\t X2: " + String.format("%.6f", secX2) + "\t X3: " + String.format("%.6f", secX3) + "\t i" + i + "\n");
            calcSecX3n2();
            calcSecX1n();

            calcSecX2n();
            reverse2();
            X1S.add(T * i, secX1);
            X2S.add(T * i, secX2);
            X3S.add(T * i, secX3);

          /*  } else {
                calcXn1();
                calcX3();
                calcXn2();
                calcXn4();
                X1S.add(T * i, X1);
                X2S.add(T * i, X2);
                X3S.add(T * i, X3);
                X4S.add(T * i, X4 / 249.7);
                reverse();
            }*/
        }
        XYSeriesCollection x1SC = new XYSeriesCollection();
        x1SC.addSeries(X1S);
        x1SC.addSeries(X2S);
        x1SC.addSeries(X3S);
        x1SC.addSeries(X4S);
        System.out.println(X4 / 249.7);
        System.out.println(((X4 / 249.7) * 100) / 0.02);
        return x1SC;
    }
}