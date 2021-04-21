package PS33_TextShadow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextShadow extends Application {
    @Override
    public void start(Stage stage) {

        stage.setTitle("Text shadow Problem Set33");

        VBox container = new VBox();
        container.setStyle("-fx-background-color: blue;" +
                "-fx-spacing: 20; " +
                "-fx-padding: 50,50,50,50; " +
                "-fx-alignment: center");


        Text text = new Text("JavaFX");
        text.setFont(Font.font(null, FontWeight.BOLD, 70));
        text.setStyle("-fx-effect: dropshadow(gaussian,white,5.0,0,3.0,3.0);" +
                "-fx-fill: white");
        //text.setEffect(new DropShadow(10, 5.0,5.0,Color.WHITE));
        container.getChildren().add(text);

        Scene scene = new Scene(container, 400, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
