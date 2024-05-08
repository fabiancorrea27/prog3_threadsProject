package co.edu.uptc.utils;

import java.text.DecimalFormat;

public class Chronometer {

    private int seconds;
    private int minutes;
    private int hours;
    private DecimalFormat decimalFormat = new DecimalFormat("00");

    public Chronometer() {
        timerString();
    }

    public String timerString() {
        minutes = (seconds % 3600) / 60;
        hours = seconds / 3600;
        String text = (decimalFormat.format(hours) + ":" + decimalFormat.format(minutes) + ":"
                + decimalFormat.format(seconds % 60));
        return text;
    }

    public void increaseSeconds() {
        seconds++;
    }
}
