package main.classes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class timeThread implements Runnable {

    Thread time = new Thread(this,"Time");
    Date currentDate = new Date();

    @Override
    public void run() {
        while(true){

        }
    }
}
