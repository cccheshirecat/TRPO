import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Computing implements Data {
    private static final int a0=10;
    private static final int  a1=40;
    private float X1=0.0f;
    private float X2=0.0f;
    private float X3=0.0f;
    private float Xn1=0.0f;
    private float Xn2=0.0f;
    private float Xn3=0.0f;
    private float X4=0.0f;
    private float Xn4=0.0f;
    private Collection x1coll =new ArrayList<Float>();
    private Collection x2coll=new ArrayList<Float>();
    private Collection x3coll=new ArrayList<Float>();
    private Collection x4coll=new ArrayList<Float>();
   // private float Mv;// возмущающий момент
    private float t=0.001f;
    private float omega=0.0f;
    private float omega1=0.0f;
    private float integralOmega=0;
    private void calcX3(){
            Xn3=a0*X1+a1*X2;
    }
    private void calcXn1(float t){
        Xn1=X1+X2*t;
    }
    private void integralOmega(float t){
        integralOmega=omega1*t;
    }
    private void calcOmega1(){
        if (omega<Data.omegaMax)
            calcX3();
        else if (omega>=Data.omegaMax) omega1=0.0f;
    }
  /*  private void calcMv(){
        Mv=Data.Jz*+Data.Jmx*
    }*/
    private void calcXn2(float t)
    {
        Xn2=X2+t*(-(Data.Jmx/Data.Jz)*X3+Data.M_v/Data.Jz);
    }
    private void calcXn4(float t){
        Xn4=X4+t*X3;
    }
    private void reverse(){
        X1=Xn1;
        Xn1=0.0f;
        X2=Xn2;
        Xn2=0.0f;
        X3=Xn3;
        Xn3=0.0f;
        X4=Xn4;
        Xn4=0.0f;
    }
    public XYDataset calculation() throws IOException {
       File file=new File("out.txt");
        XYSeries X1S=new XYSeries("X1");
        XYSeries X2S=new XYSeries("X2");
        XYSeries X3S=new XYSeries("X3");
        XYSeries X4S=new XYSeries("X4");
        double p=0.01;
        FileWriter fw =new FileWriter(file);//  Formatter formatter =new Formatter(file);
        for (int i = 0; i <450000;i++ ) {
           System.out.printf("X1: %.10f X2: %.10f X3: %.10f  X4: %.10f  t: %.5f  i:%4d \n",X1,X2,X3,X4, t, i);
       //    formatter.format("X1: %f X2: %f X3: %f  t: %.4f\n",X1,X2,X3,t);
         //  formatter.flush();
            fw.write("X1: "+String.format("%.6f",X1)+"\t X2: "+String.format("%.6f",X2)+"\t X3: "+String.format("%.6f",X3)+"\t X4: "+String.format("%.6f",X4)+"\t t:"+t+"\t i"+i+"\n");
            x1coll.add(X1);
            x2coll.add(X2);
            x3coll.add(X3);
            x4coll.add(X4);
            calcXn1(t);
            calcX3();
            calcXn2(t);
            calcXn4(t);
            X1S.add(t*i, X1);
            X2S.add(t*i,X2);
            X3S.add(t*i,X3);
            X4S.add(t*i,X4/249.7);
            reverse();

          //  t+=0.001;
        }
        float x33=X3;
        float xn33=0.0f;
        for (int i = 450000; i < 1350000; i++) {
            if (x33<=0){
                x33=a0*X1+a1*X2;
            }else {
                x33 = -a0 * X1 - a1 * X2;
            }
            x3coll.add(x33);
            X3S.add(t*i,x33);

        }
        XYSeriesCollection x1SC=new XYSeriesCollection();
        x1SC.addSeries(X1S);
        x1SC.addSeries(X2S);
        x1SC.addSeries(X3S);
        x1SC.addSeries(X4S);
        System.out.println(X4/249.7);
        System.out.println(((X4/249.7)*100)/0.02);
        return x1SC;
    }
}