package baseNoStates.state;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

// Represents a clock that notifies its observers every fixed period of time.
public class Clock extends Observable {

    private LocalDateTime date;
    private Timer timer;
    private int period; // Period in seconds after which observers are notified.

    public Clock(int period){
        this.period=period;
        timer=new Timer();
    }

    public void start(){
        TimerTask repeatedTask = new TimerTask() {
            @Override
            public void run() {
                date = LocalDateTime.now();
                System.out.println("run() executed at " + date);
                setChanged();  // Mark that this object has changed.
                notifyObservers(); // Notify all registered observers.
            }
        };
        timer.scheduleAtFixedRate(repeatedTask, 0, 1000*period);
    }

    public void stop() {
        timer.cancel();
    }

    public int getPeriod(){
        return period;
    }

    public LocalDateTime getDate() {
        return date;
    }

}
