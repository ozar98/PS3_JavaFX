package PS34_ShadowsRectangle;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Shadow extends Application {
    @Override
    public void start(Stage stage) {

        stage.setTitle("Rectangle slider drop shadow");

        final int[] values = {5,3,3,0,0,0};

        VBox container = new VBox();
        container.setStyle("-fx-background-color: white;" +
                "-fx-spacing: 20; " +
                "-fx-padding: 50,50,50,50; " +
                "-fx-alignment: center");

        Label heading = new Label("DROP SHADOW");
        heading.setFont(new Font("Times New Roman", 18));

        VBox sliderContainer = new VBox();
        sliderContainer.setStyle("-fx-spacing: 8;" +
                "-fx-alignment: baseline-left;" +
                "-fx-pref-height: 70");

        Label h2 = new Label("Radius");
        h2.setFont(new Font("Times New Roman", 15));
        h2.setRotate(360);
        h2.setTranslateX(10);

        Slider radius = new Slider();
        radius.setOrientation(Orientation.HORIZONTAL);

        Label h3 = new Label("Off Set");
        h3.setFont(new Font("Times New Roman", 15));
        h3.setRotate(360);
        h3.setTranslateX(10);

        Slider offSetX = new Slider();
        offSetX.setOrientation(Orientation.HORIZONTAL);

        Slider offSetY = new Slider();
        offSetY.setOrientation(Orientation.HORIZONTAL);

        Label h4 = new Label("RGB");
        h4.setFont(new Font("Times New Roman", 15));
        h4.setRotate(360);
        h4.setTranslateX(10);

        Slider red = new Slider();
        red.setOrientation(Orientation.HORIZONTAL);

        Slider green = new Slider();
        green.setOrientation(Orientation.HORIZONTAL);

        Slider blue = new Slider();
        blue.setOrientation(Orientation.HORIZONTAL);

        sliderContainer.getChildren().addAll(h2, radius, h3, offSetX,offSetY, h4, red, green, blue);


        Rectangle r = new Rectangle(150,75);
        r.setFill(Color.rgb(40,13,64));
        r.setEffect(new DropShadow(5.0,3.0,3.0,Color.rgb(values[3],values[4],values[5])));

        container.getChildren().addAll(heading, r, sliderContainer);

        radius.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    values[0] = newValue.intValue();
                    r.setEffect(new DropShadow(values[0],values[1],values[2], Color.rgb(values[3],values[4],values[5])));
                }
        );
        offSetX.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    values[1] = newValue.intValue();
                    r.setEffect(new DropShadow(values[0],values[1],values[2], Color.rgb(values[3],values[4],values[5])));
                }
        );

        offSetY.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    values[2] = newValue.intValue();
                    r.setEffect(new DropShadow(values[0],values[1],values[2], Color.rgb(values[3],values[4],values[5])));
                }
        );

        red.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    values[3] = newValue.intValue();
                    r.setEffect(new DropShadow(values[0],values[1],values[2], Color.rgb(values[3],values[4],values[5])));
                }
        );

        green.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    values[5] = newValue.intValue();
                    r.setEffect(new DropShadow(values[0],values[1],values[2], Color.rgb(values[3],values[4],values[5])));
                }
        );

        blue.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    values[4] = newValue.intValue();
                    r.setEffect(new DropShadow(values[0],values[1],values[2], Color.rgb(values[3],values[4],values[5])));
                }
        );


        Scene scene = new Scene(container, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
