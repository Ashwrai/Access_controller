package server.state;

import server.Door;


public class Unlocked extends State {

  // the locked state can be closed if open, locked
  Unlocked(Door door) {
    super(door);
    logger.info(door.getId() + " was unlocked");
  }

  @Override
  public void open() {
    if (door.isClosed()) {
      door.setClose(false);
    } else {
      logger.warn("Can't open door " + door.getId() + " because it's already open");
    }
  }

  @Override
  public void close() {
    if (!door.isClosed()) {
      door.setClose(true);
      logger.info(door.getId() + " was closed");
    } else {
      logger.warn(door.getId() + " is already closed");
    }
  }

  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
  }

  @Override
  public void lock() {
    if (door.isClosed()) {
      door.setState(new Locked(door));
      logger.info(door.getId() + " was closed");
    } else {
      logger.warn(door.getId() + " can't be locked, it's open");
    }
  }

  @Override
  public void unlock() {
    logger.warn(door.getId() + "already unlocked");
  }

  @Override
  public String getName() {
    return "unlocked";
  }


}
