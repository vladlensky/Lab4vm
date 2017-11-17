package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import static java.lang.Math.*;

public class Controller {
    Interface mainInterface = (double x,double y)->cos(x) + y;


    @FXML
    private Label label12;
    @FXML
    private RadioButton XY;
    @FXML
    private RadioButton cos;
    @FXML
    private TextField input;
    @FXML
    private TextField input1;
    @FXML
    private TextField input2;
    @FXML
    private TextField input21;
    @FXML private void drawCos(){
        mainInterface = (double x,double y)->cos(x) + y;
        draw();
    }
    @FXML private void drawPow2(){
        mainInterface = (double x,double y)->x+y;
        draw();
    }
    @FXML
    private NumberAxis xAxis = new NumberAxis();
    @FXML
    private NumberAxis yAxis = new NumberAxis();
    @FXML
    private LineChart<Number, Number> chart = new LineChart<Number, Number>(xAxis,yAxis);
    @FXML
    public void doCheck(){
        try {
            boolean b = false;
            double x0 = Double.valueOf(input.getText().trim().replace(',', '.'));
            double y0 = Double.valueOf(input1.getText().trim().replace(',', '.'));
            double x = Double.valueOf(input2.getText().trim().replace(',', '.'));
            double accuracy = Double.valueOf(input21.getText().trim().replace(',', '.'));
            if (Double.isNaN(mainInterface.calculate(x0,y0))||Double.isNaN(x)||Double.isNaN(accuracy)) {
                label12.setText("Invalid numbers!");
                return;
            }
            if(accuracy<=0){
                label12.setText("Invalid accuracy!");
                return;
            }
            label12.setText("");
        }catch (Exception e){
            label12.setText("Invalid numbers!");
        }

    }
    @FXML
    public void drawGraphics(){
        if(cos.isSelected()){
            drawCos();
        }else if(XY.isSelected()){
            drawPow2();
        }
    }
    public void draw() {
        try {
            double x0 = Double.valueOf(input.getText().trim().replace(',', '.'));
            double y0 = Double.valueOf(input1.getText().trim().replace(',', '.'));
            double x = Double.valueOf(input2.getText().trim().replace(',', '.'));
            double accuracy = Double.valueOf(input21.getText().trim().replace(',', '.'));
            if (Double.isNaN(mainInterface.calculate(x0, y0)) || Double.isNaN(x) || Double.isNaN(accuracy)) {
                label12.setText("Invalid numbers!");
                return;
            }
            if (accuracy <= 0) {
                label12.setText("Invalid accuracy!");
                return;
            }
            label12.setText("");
            Eiler eiler = new Eiler(x0, y0, x, accuracy, mainInterface);
            chart.getData().clear();
            XYChart.Series mainGraph = new XYChart.Series();
            ObservableList<XYChart.Data> mainPoints = FXCollections.observableArrayList();
            for (int i = 0; i < eiler.getH(); i++) {
                mainPoints.add(new XYChart.Data(eiler.getResX().get(i), eiler.getResY().get(i)));
            }
            mainGraph.setData(mainPoints);
            chart.getData().add(mainGraph);
        } catch (Exception e) {
            label12.setText("Invalid numbers!");
        }
    }
}