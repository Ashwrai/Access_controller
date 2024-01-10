package server.state;

import server.Door;

// This class represents the propped state of a door.
public class Propped extends State {

  // The propped state can only be closed.
  Propped(Door door) {
    super(door);
    logger.warn(door.getId() + " was propped");
  }

  @Override
  public void open() {
    logger.warn(door.getId() + " is open, it's propped");
  }

  @Override
  public void close() {
    door.setClose(true);
    door.setState(new Locked(door));
    logger.info("Door " + door.getId() + " was locked");
  }

  @Override
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
  }

  @Override
  public void lock() {
    logger.warn("Can't lock, door " + door.getId() + "is open because it's propped");
    door.addReasons(door.getId() + " is propped, unable to lock door");
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
  }

  @Override
  public String getName() {
    return "propped";
  }


}
