package PS32_DigitalClockApp;


import java.io.FileNotFoundException;

import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import javafx.event. *;




import javax.swing.*;

public class AlarmController implements Initializable {
    @FXML
    private Label CurrentTime;

    @FXML private ComboBox<Integer> HourBox;

    @FXML private ComboBox<Integer> MinBox;

    @FXML private ImageView image;

    private Timer newTimer;

    @FXML
    private Button stopButton;

    @FXML
    private Label elseLabel;

    private static final AudioClip ALARM = new AudioClip(AlarmController.class.getResource("/PS32_DigitalClockApp/applause4.mp3").toString());

    @Override
    public void initialize (URL url, ResourceBundle rb) {

        Timeline timeline = new Timeline (new KeyFrame (Duration.millis (1000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle (ActionEvent actionEvent) {

                        try {

                            if ((alarmTimeInMin() == clockTimeInMin())) {
                                AlarmController.ALARM.play();
                            }

                        }catch (NullPointerException e){

                        }
                    }
                }
        ));
        timeline.setCycleCount (Timeline.INDEFINITE);
        timeline.play ();

        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                HourBox.setValue(0);
                MinBox.setValue(0);
            }
        });
        remindButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(MinBox.getValue()+2>59){
                    int wait=(MinBox.getValue()+2)-59;
                    MinBox.setValue(wait-1);
                    if(HourBox.getValue()+1>23){
                        HourBox.setValue(0);
                    }
                    else {
                        HourBox.setValue(HourBox.getValue()+1);
                    }

                }
                else {
                    MinBox.setValue(MinBox.getValue()+2);
                }


            }
        });



        Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(1000),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        LocalTime now = LocalTime.now ();
                        String txt = String.format ("% 02d:% 02d:% 02d", now.getHour (), now.getMinute (), now.getSecond ());
                        CurrentTime.setText (txt);

                    }
                }));

        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline2.play();



        image.setVisible(false);



        MinBox.getItems().addAll(
                0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
                43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59
        );

        HourBox.getItems().addAll(0,1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
                20, 21, 22, 23);


    }


    private int alarmTimeInMin() {
        int hours;
        int min;
        if (HourBox.getValue() >= 0){hours= HourBox.getValue();}
        else{hours=0;}

        if (MinBox.getValue() >= 0){min = MinBox.getValue();}
        else {min=0;}

        return min + hours * 60;
    }

    private int clockTimeInMin() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        // if it's after noon
        if(cal.get(Calendar.AM_PM) == Calendar.PM) {
            int clockHour = cal.get(Calendar.HOUR);
            int clockTotalMin = (clockHour + 12)*60 + cal.get(Calendar.MINUTE);

            if(clockTotalMin >= 1440)
                clockTotalMin = cal.get(Calendar.MINUTE);
            return clockTotalMin;
        }
        else {
            // if it's before noon
            int clockHour = cal.get(Calendar.HOUR);
            return clockHour*60+cal.get(Calendar.MINUTE);
        }
    }
    @FXML
    void stopButton(ActionEvent event) {
        if (AlarmController.ALARM.isPlaying()){
            AlarmController.ALARM.setVolume(0);
            AlarmController.ALARM.stop();
        }

    }
    @FXML
    private Button remindButton;

    @FXML
    void remind(ActionEvent event) {

    }



}
