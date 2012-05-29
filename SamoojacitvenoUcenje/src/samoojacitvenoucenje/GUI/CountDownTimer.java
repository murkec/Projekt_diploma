/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package samoojacitvenoucenje.GUI;

import java.awt.Label;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;



/**
 *
 * @author Evgen
 */
public class CountDownTimer extends Label {
    
    
    private Timer timer;
    private int secondsRemaining = 0;


    public CountDownTimer(int startTimeInSeconds) {
        this.secondsRemaining = startTimeInSeconds;
        this.setText(convertSecToMin(this.secondsRemaining));
        
        timer = new Timer();
           
    }
    
    public void addTime(int addSeconds) {
        this.secondsRemaining += addSeconds;
    }
    
    public void removeTime(int addSeconds) {
        this.secondsRemaining -= addSeconds;
    }
    
    private String convertSecToMin(int seconds){
        return new Time(seconds).toString();
    }
    
    private void start() {
        timer.schedule(new DisplayCountdown(this.secondsRemaining), 0, 1000);

    }
    
    private void test()
    {
        
    }
    
    class DisplayCountdown extends TimerTask {

        private int secondsRemaining = 0;

        public DisplayCountdown(int remainingSeconds) {
            this.secondsRemaining = remainingSeconds;
        }
        
        @Override
        public void run() {
            if (secondsRemaining > 0) {
                System.out.println(secondsRemaining + " seconds remaining");
                secondsRemaining--;
            } else {
                System.out.println("Countdown finished");
                System.exit(0);
            }
        }
    }
    
}

