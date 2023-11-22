package server.state;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.DirectoryAreas;

// Represents a clock that notifies its observers every fixed period of time.
public class Clock extends Observable {

  private static Logger logger = LoggerFactory.getLogger(Clock.class);
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
      logger.debug("clock created");
      Clock.globalInstance = new Clock(1);
    }
    return Clock.globalInstance;
  }

  public void start() {
    TimerTask repeatedTask = new TimerTask() {
      @Override
      public void run() {
        logger.debug("clock ticked");
        date = LocalDateTime.now();
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
