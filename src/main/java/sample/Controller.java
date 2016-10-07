package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Controller {
    @FXML
    RadioButton radioButton1, radioButton2;
    @FXML
    Button button2, button1, button3;
    @FXML
    Label label1;
    @FXML
    TextField textField, textField2;
    @FXML
    HBox hBox;
    @FXML
    TextArea textArea;
    @FXML
    Canvas canvas;
    private Data data = new Data();
    private int count = 0;
    private String stringTextFlow = "";

    public void sellectMethod1(ActionEvent event) {
        if (radioButton1.isSelected()) {
            radioButton1.setSelected(true);
            radioButton2.setSelected(false);
        }
    }

    public void sellectMethod2(ActionEvent event) {
        if (radioButton2.isSelected()) {
            radioButton2.setSelected(true);
            radioButton1.setSelected(false);
        }
    }

    public void setButton1(ActionEvent event) {
        int localCount;
        if (label1.getText().equals("Введите количество узлов N") || label1.getText().equals("Повторите ввод количества узлов")) {
            try {
                data.setN(Integer.parseInt(textField.getText()));
                if (data.getN() < 2) throw new Exception();
                count = 0;
                stringTextFlow = "N = " + data.getN() + "\n";
                textArea.setText(String.format("%s", stringTextFlow));
                localCount = count + 1;
                label1.setText("Введите " + localCount + " пару х - f(x)");
                textField.setText("");
                textField.setPromptText("x - f(x)");
                stringTextFlow = String.format(stringTextFlow + "%7s|%-7s\n", "X", "F(x)");
            } catch (Exception e) {
                label1.setText("Повторите ввод количества узлов");
                count = 0;
            }
        }
        if (textField.getText().contains(" - ") & count < data.getN() & !label1.getText().equals("Введите количество узлов N") & !label1.getText().equals("Повторите ввод количества узлов")) {
            try {
                String[] s = textField.getText().split(" - ");
                Double x = Double.parseDouble(s[0]);
                Double y = Double.parseDouble(s[1]);
                for (Double e : data.getVectorX()) {
                    if (e.equals(x)) {
                        throw new Exception();
                    }
                }
                data.getVectorX()[count] = x;
                data.getVectorY()[count] = y;
                stringTextFlow = String.format(stringTextFlow + "|%6.3f|%6.3f|%n", x, y);
                textArea.setText(stringTextFlow);
                count++;
                localCount = count + 1;
                label1.setText("Введите " + localCount + " пару х - f(x)");
                textField.setText("");
                textField.setPromptText("x - f(x)");
            } catch (Exception e) {
                localCount = count + 1;
                label1.setText("Повторите ввод " + localCount + " пары x - f(x)");
                textField.setText("");
                textField.setPromptText("x - f(x)");
            }
        }
        if (count == data.getN() & count > 0) {
            label1.setText("Введите х");
            textField.setText("");
            textField.setPromptText("");
            hBox.setDisable(true);
            button2.setDisable(false);
            textField2.setDisable(false);
        }
    }

    public void setButton2(ActionEvent event) {
        if (radioButton1.isSelected()) {
            label1.setText("Введите х");
            Lagran lagran = new Lagran();
            lagran.sort(data);
            double l;
            try {
                l = lagran.solution(data, Double.parseDouble(textField2.getText()));
            } catch (Exception e) {
                label1.setText("Повторите ввод x");
            }
            draw();
        } else if (radioButton2.isSelected()) {
            label1.setText("Введите х");
            Spline spline = new Spline();
            spline.sort(data);
            spline.BuildSpline(data.getVectorX(), data.getVectorY(), data.getN());
            try {
                double l = spline.f(Double.parseDouble(textField2.getText()));
            } catch (Exception e) {
                label1.setText("Повторите ввод x");
            }
            draw();
        } else {
            label1.setText("Выберите метод расчета и введите х");
        }
    }

    public void setButton3(ActionEvent event) {
        stringTextFlow = "";
        count = 0;
        textArea.setText(stringTextFlow);
        label1.setText("Введите количество узлов N");
        hBox.setDisable(false);
        button2.setDisable(true);
        textField.setPromptText("");
        GraphicsContext gr = canvas.getGraphicsContext2D();
        gr.clearRect(0, 0, 230, 260);
    }

    private void draw() {
        GraphicsContext gr = canvas.getGraphicsContext2D();
        double[] vecX = data.getVectorX();
        double[] vecY = data.getVectorY();
        int n = data.getN();
        gr.clearRect(0, 0, 230, 260);
        gr.setStroke(Color.BLACK);
        gr.strokeLine(0, 130, 230, 130);
        gr.strokeLine(115, 0, 115, 260);
        gr.setFont(new Font(8));
        gr.fillText("0", 116, 129);
        gr.fillText("x", 225, 135);
        gr.fillText("y", 116, 5);

        if (radioButton1.isSelected()) {
            Lagran lagran = new Lagran();
            lagran.sort(data);
            gr.setStroke(Color.RED);
            double prevX = vecX[0];
            double prevY = vecY[0];
            double scaleX = 110 / (Math.max(Math.abs(vecX[0]), Math.abs(vecX[n - 1])));
            double[] YY = new double[10 * (n - 1) + 1];
            for (int i = 0; i < n - 1; i++) {
                double dx = (vecX[i + 1] - vecX[i]) / 10;
                for (int j = 0; j < 11; j++) {
                    double x = vecX[i] + dx * j;
                    YY[j + i * 10] = lagran.solution(data, x);
                }
            }
            double maxY = Math.abs(YY[0]);
            for (int i = 0; i < YY.length; i++) {
                if (Math.abs(YY[i]) > maxY) {
                    maxY = Math.abs(YY[i]);
                }
            }
            double scaleY = 125 / maxY;
            for (int i = 0; i < n - 1; i++) {
                double dx = (vecX[i + 1] - vecX[i]) / 10;
                for (int j = 0; j < 11; j++) {
                    double x = vecX[i] + dx * j;
                    double y = lagran.solution(data, x);
                    gr.strokeLine(115 + prevX * scaleX, 130 - prevY * scaleY, 115 + x * scaleX, 130 - y * scaleY);
                    prevX = x;
                    prevY = y;
                }
            }
            try {
                if (!textField2.getText().equals("") & (Double.parseDouble(textField2.getText())) >= vecX[0] & (Double.parseDouble(textField2.getText())) <= vecX[n - 1]) {
                    try {
                        double x = Double.parseDouble(textField2.getText());
                        double y = lagran.solution(data, x);
                        gr.setStroke(Color.BROWN);
                        gr.strokeLine(115 + x * scaleX, 130, 115 + x * scaleX, 130 - y * scaleY);
                        gr.strokeLine(115, 130 - y * scaleY, 115 + x * scaleX, 130 - y * scaleY);
                        gr.setFont(new Font(6));
                        gr.setStroke(Color.DARKGRAY);
                        gr.fillText(String.format("%2.2f", x), 115 + x * scaleX, 129);
                        gr.fillText(String.format("%2.2f", y), 116, 128 - y * scaleY);
                        gr.setFont(new Font(10));
                        gr.fillText(String.format("х=%2.3f, F(x)=%2.3f", x, y), 5, 250);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        } else if (radioButton2.isSelected()) {
            Spline spline = new Spline();
            spline.BuildSpline(vecX, vecY, n);
            spline.sort(data);
            gr.setStroke(Color.RED);
            double prevX = vecX[0];
            double prevY = vecY[0];
            double scaleX = 110 / (Math.max(Math.abs(vecX[0]), Math.abs(vecX[n - 1])));
            double[] YY = new double[10 * (n - 1) + 1];
            for (int i = 0; i < n - 1; i++) {
                double dx = (vecX[i + 1] - vecX[i]) / 10;
                for (int j = 0; j < 11; j++) {
                    double x = vecX[i] + dx * j;
                    YY[j + i * 10] = spline.f(x);
                }
            }
            double maxY = Math.abs(YY[0]);
            for (int i = 0; i < YY.length; i++) {
                if (Math.abs(YY[i]) > maxY) {
                    maxY = Math.abs(YY[i]);
                }
            }
            double scaleY = 125 / maxY;

            for (int i = 0; i < n - 1; i++) {
                double dx = (vecX[i + 1] - vecX[i]) / 10;
                for (int j = 0; j < 11; j++) {
                    double x = vecX[i] + dx * j;
                    double y = spline.f(x);
                    gr.strokeLine(115 + prevX * scaleX, 130 - prevY * scaleY, 115 + x * scaleX, 130 - y * scaleY);
                    prevX = x;
                    prevY = y;
                }
            }
            try {
                if (!textField2.getText().equals("") & (Double.parseDouble(textField2.getText())) >= vecX[0] & (Double.parseDouble(textField2.getText())) <= vecX[n - 1]) {
                    try {
                        double x = Double.parseDouble(textField2.getText());
                        double y = spline.f(x);
                        gr.setStroke(Color.BROWN);
                        gr.strokeLine(115 + x * scaleX, 130, 115 + x * scaleX, 130 - y * scaleY);
                        gr.strokeLine(115, 130 - y * scaleY, 115 + x * scaleX, 130 - y * scaleY);
                        gr.setFont(new Font(6));
                        gr.setStroke(Color.DARKGRAY);
                        gr.fillText(String.format("%2.2f", x), 115 + x * scaleX, 129);
                        gr.fillText(String.format("%2.2f", y), 116, 128 - y * scaleY);
                        gr.setFont(new Font(10));
                        gr.fillText(String.format("х=%2.3f, F(x)=%2.3f", x, y), 5, 250);
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e) {
            }
        } else {
        }
    }

    private void draw2() {
    }
}
