package sample;

import java.util.ArrayList;
import java.util.List;

public class Eiler {
    private List<Double> X = null;
    private List<Double> Y = null;
    private int h;
    public Eiler( final double x0, final double y0,final  double x1, final double accuracy,final  Interface f){
        if(x1<=x0 || accuracy<=0 || f == null) throw new RuntimeException();
        double currAccuracy = -1;
        int n = 4;
        double h = (x1-x0)/n;
        X = new ArrayList<>();
        Y = new ArrayList<>();
        Y.add(y0);
        X.add(x0);
        List<Double> tempX = new ArrayList<>();
        List<Double> tempY = new ArrayList<>();
        tempX.add(y0);
        tempY.add(x0);
        tempX.add(x0 + h);
        tempY.add(y0 + h*f.calculate(x0 + h/2, y0 + h*f.calculate(x0, y0)));
        for(int i = 2;i < n;i++){
            tempX.add(tempX.get(i-1) + h);
            tempY.add(tempY.get(i-2) + h*f.calculate(tempX.get(i-1), tempY.get(i-1)));
        }
        do{
            h = h/2;
            n *=2;
            X.clear();
            Y.clear();
            Y.add(y0);
            X.add(x0);
            X.add(x0 + h);
            Y.add(y0 + h*f.calculate(x0 + h/2, y0 + h*f.calculate(x0, y0)));
            for(int i = 2;i < n;i++){
                X.add(X.get(i-1) + h);
                Y.add(Y.get(i-2) + h*f.calculate(X.get(i-1), Y.get(i-1)));
            }
            currAccuracy = -1;
            for(int i=0; i<tempX.size();i++){
                double res = Math.abs(tempY.get(i) - Y.get(2*i));
                if(res>currAccuracy) currAccuracy = res;
            }
            this.h = n;
            tempX.clear();
            tempY.clear();
            tempX.addAll(X);
            tempY.addAll(Y);
        }while (currAccuracy>accuracy);
        this.X = tempX;
        this.Y = tempY;
    }
    public List<Double> getResY(){
        return Y;
    }

    public double getH() {
        return h;
    }

    public List<Double> getResX(){
        return X;
    }
}
