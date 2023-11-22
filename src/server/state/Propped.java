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
    logger.warn("Door " + door.getId() + " is open, it's propped");
  }

  @Override
  public void lock() {
    logger.warn("Can't lock, door " + door.getId() + "is open because it's propped");
  }

  @Override
  public void unlock() {
    logger.warn("Door " + door.getId() + "is open, it's propped");
  }

  @Override
  public String getName() {
    return "propped";
  }


}
