package server.state;

import server.Door;

import java.util.Observable;
import java.util.Observer;



// Represents the UnlockedShortly state of a door, observing changes from a Clock instance.

public class UnlockedShortly extends State implements Observer {
  // The unlocked shorly functions exactly as unlocked state, only for 10 seconds.
  private int timer; // Timer counter.
  private static Clock clock; // Shared clock instance.
  private static final int MAX_TIME = 10;

  UnlockedShortly(Door door) {
    super(door);
    logger.info(door.getId() + " was unlocked shortly");
    timer = 0;
    if (UnlockedShortly.clock == null) {
      UnlockedShortly.clock = Clock.getInstance();
      UnlockedShortly.clock.start();
    }
    UnlockedShortly.clock.addObserver(this);
  }

  // Called when the observed Clock notifies of changes.
  public void update(Observable observable, Object arg) {
    if (timer < MAX_TIME) {
      timer++;
    } else {
      if (!door.isClosed()) {
        logger.info(door.getId() + " is propped");
        door.setState(new Propped(door));
      } else {
        logger.info(door.getId() + " is locked");
        door.setState(new Locked(door));
      }
      UnlockedShortly.clock.deleteObserver(this);
    }
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      logger.info(door.getId() + " was opened");
      door.setClose(false);
    } else {
      logger.warn("Can't open door " + door.getId() + " because it's already open");
    }
  }

  @Override
  public void close() {
    if (!door.isClosed()) {
      logger.info(door.getId() + " was closed");
      door.setClose(true);
    } else {
      logger.warn(door.getId() + " is already closed");
    }
  }

  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
  }

  @Override
  public void unlock() {
    logger.warn(door.getId() + " was already unlocked");
  }

  @Override
  public void lock() {
    if (door.isClosed()) {
      logger.info(door.getId() + " was locked");
      door.setState(new Locked(door));
    } else {
      logger.warn(door.getId() + " can't be locked, it's open");
      door.addReasons(door.getId() + " is propped, unable to lock door");
    }
  }


  @Override
  public String getName() {
    return "unlocked_shortly";
  }
}
