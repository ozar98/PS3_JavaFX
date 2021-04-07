package PS32_DigitalClockApp;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;


public class DigitalClockApp extends Application {
    int hour, minute, second;
    String timeString;


    @Override public void start(Stage stage) {
        stage.setTitle("Digital Alarm Clock");

        VBox clockContainer = new VBox();
        clockContainer.setStyle("-fx-background-color: blue;" +
                "-fx-spacing: 20; " +
                "-fx-padding: 50,50,50,50; " +
                "-fx-alignment: center");

        //clockContainer.setStyle("-fx-background-color: black");
        //clockContainer.setPadding(new Insets(50, 50, 50, 50));
        //clockContainer.setSpacing(20);
        //clockContainer.setAlignment(Pos.CENTER);

        Label label = new Label("00:00:00");
        label.setFont(new Font("Times New Roman", 50));
        label.setStyle("-fx-text-fill: white");

        //set Alarm button
        Button setAlarm = new Button("Set Alarm");
        setAlarm.setFont(new Font("Times New Roman", 15));
        setAlarm.setStyle("-fx-pref-height: 20;" +
                "-fx-pref-width: 165;" +
                "-fx-alignment: center");
        clockContainer.getChildren().addAll(label, setAlarm);

        // Animating the clock by 1 second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                label.setText(simpleDateFormat.format(time.getTime()));
                            }
                        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        VBox alarmContainer = new VBox();
        alarmContainer.setStyle("-fx-background-color: blue;" +
                "-fx-spacing: 20; " +
                "-fx-padding: 50,50,50,50; " +
                "-fx-alignment: center");

        HBox valueContainer = new HBox();
        valueContainer.setStyle("-fx-spacing: 8;" +
                "-fx-alignment: center;" +
                "-fx-pref-height: 30");

        //valueContainer.setSpacing(8);
        //valueContainer.setAlignment(Pos.CENTER);
        //valueContainer.setPrefHeight(30);

        ComboBox hourComboBox = new ComboBox();
        hourComboBox.setMaxHeight(30);
        hourComboBox.getItems().addAll
                ("00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23);
        hourComboBox.setPromptText("Hour");

        Label colon = new Label(":");
        colon.setFont(new Font("Times New Roman", 20));
        colon.setStyle("-fx-text-fill: white");

        ComboBox minuteComboBox = new ComboBox();
        minuteComboBox.setStyle("-fx-max-height: 30");
        minuteComboBox.getItems().addAll
                (
                        "00", "01", "02", "03", "04", "05", "06", "07", "08", "09",
                        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
                        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
                        43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59
                );
        minuteComboBox.setPromptText("Min");

        valueContainer.getChildren().addAll(hourComboBox, colon, minuteComboBox);

        Button done = new Button("Done");
        done.setFont(new Font("Times New Roman", 15));
        done.setStyle("-fx-pref-height: 20;" +
                "-fx-pref-width: 156;" +
                "-fx-alignment: center");

        alarmContainer.getChildren().addAll(valueContainer, done);

        Scene clock = new Scene(clockContainer, 380, 300);
        Scene alarm = new Scene(alarmContainer, 380, 300);

        setAlarm.setOnAction(event -> {
            stage.setScene(alarm);
        });


        done.setOnAction(event -> {
            if (hourComboBox.getValue() != null || minuteComboBox.getValue() != null) {
                System.out.printf("Alarm is set for ");
                System.out.print(hourComboBox.getValue());
                System.out.print(":");
                System.out.print(minuteComboBox.getValue());
                System.out.println();

                hourComboBox.setValue(null);
                minuteComboBox.setValue(null);

                stage.setScene(clock);

            };
        });



        stage.setScene(clock);
        stage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
