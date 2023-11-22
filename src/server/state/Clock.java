package server.state;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

// Represents a clock that notifies its observers every fixed period of time.
public class Clock extends Observable {

  private static Clock globalInstance;

  private LocalDateTime date;
  private final Timer timer;
  private final int period; // Period in seconds after which observers are notified.

  private Clock(int period) {
    this.period = period;
    timer = new Timer();
  }

  public static Clock getInstance() {
    if (Clock.globalInstance == null) {
      Clock.globalInstance = new Clock(1);
    }
    return Clock.globalInstance;
  }

  public void start() {
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        date = LocalDateTime.now();
        System.out.println("run() executed at " + date);
        setChanged();  // Mark that this object has changed.
        notifyObservers(); // Notify all registered observers.
      }
    };
    timer.scheduleAtFixedRate(repeatedTask, 0, 1000L * period);
  }

  public void stop() {
    timer.cancel();
  }

  public int getPeriod() {
    return period;
  }

  public LocalDateTime getDate() {
    return date;
  }

}
