package PS31_KaleidoscopeApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.text.NumberFormat;
import java.util.Stack;


public class KaleidoscopeController {
    @FXML private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;
    @FXML
    private Slider thicknessSlider;
    @FXML
    private Rectangle colorRectangle;
    @FXML
    private Canvas drawingAreaCanvas;
    @FXML
    private RadioButton lineRadioButton;
    @FXML
    private RadioButton rectangleRadioButton;
    @FXML
    private RadioButton ovalRadioButton;
    @FXML
    private RadioButton eraserRadioButton;

    @FXML
    private TextField thicknessTextField;


    private int red = 0;
    private int green = 0;
    private int blue = 0;
    private GraphicsContext gc;
    Line line = new Line();
    Rectangle rect = new Rectangle();
    Ellipse oval = new Ellipse();
    Stack<Shape> history = new Stack();
    private int selected;
    private double selectedX;
    private double selectedY;
    public void initialize() {
        gc = drawingAreaCanvas.getGraphicsContext2D();

        thicknessTextField.textProperty().bindBidirectional(thicknessSlider.valueProperty(), NumberFormat.getNumberInstance());

        thicknessTextField.textProperty().addListener((args, oldValue, newValue )->{
            int val= (int)thicknessSlider.getValue();
            thicknessTextField.setText(String.valueOf(val));

        });

        redSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    red = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue));
                }
        );
        greenSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    green = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue));
                }
        );
        blueSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    blue = newValue.intValue();
                    colorRectangle.setFill(Color.rgb(red, green, blue));
                }
        );
        drawingAreaCanvas.setOnMousePressed(e -> {
            if (lineRadioButton.isSelected()) {
                line.setStartX(e.getX());
                line.setStartY(e.getY());
                line.setEndX(e.getX());
                line.setEndY(e.getY());
            } else if (rectangleRadioButton.isSelected()) {
                rect.setX(e.getX());
                rect.setY(e.getY());
            } else if (ovalRadioButton.isSelected()) {
                oval.setCenterX(e.getX());
                oval.setCenterY(e.getY());
            } else if (eraserRadioButton.isSelected()) erase(e.getX(), e.getY());

        });
        drawingAreaCanvas.setOnMouseDragged(e -> {
            if (eraserRadioButton.isSelected()) erase(e.getX(), e.getY());
        });
        drawingAreaCanvas.setOnMouseReleased(e -> {

            gc.setStroke(Color.rgb(red, green, blue));

            if (lineRadioButton.isSelected()) {
                double ox = drawingAreaCanvas.getWidth() / 2;
                double oy = drawingAreaCanvas.getHeight() / 2;
                line.setStrokeWidth(thicknessSlider.getValue());
                line.setFill(Color.rgb(red, green, blue));
                line.setEndX(e.getX());
                line.setEndY(e.getY());
                gc.setLineWidth(thicknessSlider.getValue());
                int p = 6;
                for (int i = 0; i < p; i++) {
                    double theta = Math.toRadians(i * 360 / p);

                    double x1 = Math.cos(theta) * (line.getStartX() - ox) - Math.sin(theta) * (line.getStartY() - oy) + ox;
                    double y1 = Math.sin(theta) * (line.getStartY() - ox) + Math.cos(theta) * (line.getStartY() - oy) + oy;
                    double x2 = Math.cos(theta) * (line.getEndX() - ox) - Math.sin(theta) * (line.getEndY() - oy) + ox;
                    double y2 = Math.sin(theta) * (line.getEndY() - ox) + Math.cos(theta) * (line.getEndY() - oy) + oy;
                    gc.strokeLine(x1, y1, x2, y2);
                    Line tempLine = new Line(x1, y1, x2, y2);
                    tempLine.setStroke(Color.rgb(red, green, blue));
                    tempLine.setStrokeWidth(line.getStrokeWidth());
                    history.push(tempLine);
                }

            }
            else if (rectangleRadioButton.isSelected()) {
                double ox = drawingAreaCanvas.getWidth() / 2;
                double oy = drawingAreaCanvas.getHeight() / 2;
                rect.setWidth(Math.abs((e.getX() - rect.getX())));
                rect.setHeight(Math.abs((e.getY() - rect.getY())));
                rect.setX(Math.min(rect.getX(), e.getX()));
                rect.setY(Math.min(rect.getY(), e.getY()));
                rect.setFill(Color.rgb(red, green, blue));
                gc.setFill(Color.rgb(red, green, blue));

                int p = 6;
                for (int i = 0; i < p; i++) {
                    double theta = Math.toRadians(i * 360 / p);

                    double x1 = Math.cos(theta) * (rect.getX() - ox) - Math.sin(theta) * (rect.getY() - oy) + ox;
                    double y1 = Math.sin(theta) * (rect.getY() - ox) + Math.cos(theta) * (rect.getY() - oy) + oy;


                    gc.fillRect(x1, y1, rect.getWidth(), rect.getHeight());
                    gc.strokeRect(x1, y1, rect.getWidth(), rect.getHeight());

                    Rectangle tempRect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
                    tempRect.setFill(Color.rgb(red, green, blue));
                    history.push(tempRect);}
            } else if (ovalRadioButton.isSelected()) {
                double ox = drawingAreaCanvas.getWidth() / 2;
                double oy = drawingAreaCanvas.getHeight() / 2;
                oval.setRadiusX(Math.abs(e.getX() - oval.getCenterX()));
                oval.setRadiusY(Math.abs(e.getY() - oval.getCenterY()));
                oval.setCenterX(Math.min(oval.getCenterX(), e.getX()));
                oval.setCenterY(Math.min(oval.getCenterY(), e.getY()));
                oval.setFill(Color.rgb(red, green, blue));
                gc.setFill(Color.rgb(red, green, blue));
                int p=6;
                for (int i = 0; i < p; i++) {
                    double theta = Math.toRadians(i * 360 / p);

                    double x1 = Math.cos(theta) * (oval.getCenterX() - ox) - Math.sin(theta) * (oval.getCenterY() - oy) + ox;
                    double y1 = Math.sin(theta) * (oval.getCenterY() - ox) + Math.cos(theta) * (oval.getCenterX() - oy) + oy;

                    gc.fillOval(oval.getCenterX(), oval.getCenterY(), oval.getRadiusX(), oval.getRadiusY());
                    gc.strokeOval(x1, y1, oval.getRadiusX(), oval.getRadiusY());
                    Ellipse tempOval = new Ellipse(oval.getCenterX(), oval.getCenterY(), oval.getRadiusX(), oval.getRadiusY());
                    tempOval.setFill(Color.rgb(red, green, blue));
                    history.push(tempOval);
                }
            } else if (eraserRadioButton.isSelected()) erase(e.getX(), e.getY());

        });
    }
    private void erase(double x, double y) {
        double sise = thicknessSlider.getValue();
        gc.clearRect(x - sise / 2, y - sise / 2, sise, sise);
    }
    @FXML
    private void undoButtonPressed(ActionEvent event) {
        if (!history.isEmpty()) {
            history.pop();
            gc.clearRect(0, 0, drawingAreaCanvas.getWidth(), drawingAreaCanvas.getHeight());
            drawShapes(history);
        }
    }
    private void drawShapes(Stack<Shape> shapes) {
        double ox = drawingAreaCanvas.getWidth() / 2;
        double oy = drawingAreaCanvas.getHeight() / 2;
        int n=10;
        for (Shape s: shapes) {
            if(s.getClass() == Line.class) {

                Line temp = (Line) s;
                gc.setLineWidth(temp.getStrokeWidth());
                gc.setStroke(temp.getStroke());
//                    gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                gc.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
            }


            else if(s.getClass() == Rectangle.class) {
                Rectangle temp = (Rectangle) s;
                gc.setLineWidth(temp.getStrokeWidth());
                gc.setFill(temp.getFill());
                gc.setStroke(temp.getStroke());
                gc.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                gc.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
            }
            else if(s.getClass() == Ellipse.class) {
                Ellipse temp = (Ellipse) s;
                gc.setStroke(temp.getStroke());
                gc.setLineWidth(temp.getStrokeWidth());
                gc.setFill(temp.getFill());
                gc.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                gc.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
            }
        }
    }
    private boolean isInShapeArea(Shape s, double x, double y) {
        if(s.getClass() == Line.class) {
            line = (Line) s;
            if (Math.abs(Math.pow(Math.pow(line.getStartX() - x, 2) + Math.pow(line.getStartY() - y, 2), .5) +
                    Math.pow(Math.pow(line.getEndX() - x, 2) + Math.pow(line.getEndY() - y, 2), .5) -
                    Math.pow(Math.pow(line.getEndX() - line.getStartX(), 2) + Math.pow(line.getEndY() - line.getStartY(), 2), .5)) < 5)
                return true;
        } else if (s.getClass() == Rectangle.class) {
            rect = (Rectangle) s;
            if ((x >= rect.getX()) && (x <= (rect.getX() + rect.getWidth()))
                    && (y >= rect.getY()) && (y <= (rect.getY() + rect.getHeight())))
                return true;
        } else if (s.getClass() == Ellipse.class) {
            oval = (Ellipse) s;
            if (((Math.pow((x - oval.getCenterX() - oval.getRadiusX() / 2), 2) / Math.pow(oval.getRadiusX() / 2, 2)) +
                    (Math.pow((y - oval.getCenterY() - oval.getRadiusY() / 2), 2) / Math.pow(oval.getRadiusY() / 2, 2))) <= 1)
                return true;
        }
        return false;
    }
    @FXML
    private void clearButtonPressed(ActionEvent event) {
        gc.clearRect(0, 0, drawingAreaCanvas.getWidth(), drawingAreaCanvas.getHeight());
        history.clear();
    }
}
