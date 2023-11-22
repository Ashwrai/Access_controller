package server.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Door;

// This class represents the locked state of a door.

public class Locked extends State {

  // The locked state can only be unlocked.
  // It cannot be opened or closed, and it is already locked.
  public Locked(Door door) {
    super(door);
    logger.info(door.getId() + " was locked");
  }

  public void open() {
    logger.warn("Can't open door " + door.getId() + " because it's locked");
  }

  public void close() {
    logger.warn("Can't close door " + door.getId() + " because it's already closed");
  }

  // This method transitions the door to the UnlockedShortly state,
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
  }

  @Override
  public void lock() {
    logger.warn(door.getId() + " is already locked");
  }

  @Override
  public void unlock() {
    logger.info(door.getId() + " was unlocked");
    door.setState(new Unlocked(door));
  }

  @Override
  public String getName() {
    return "locked";
  }
}
