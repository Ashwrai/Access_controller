package server.state;

import server.Door;

// This class represents the locked state of a door.

public class Locked extends State {

  // The locked state can only be unlocked.
  // It cannot be opened or closed, and it is already locked.
  public Locked(Door door) {
    super(door);
  }

  public void open() {
    System.out.println("Can't open door " + door.getId() + " because it's locked");
  }

  public void close() {
    System.out.println("Can't close door " + door.getId() + " because it's already closed");
  }

  // This method transitions the door to the UnlockedShortly state,
  public void unlockShortly() {
    door.setState(new UnlockedShortly(door));
  }

  @Override
  public void lock() {
    System.out.println(door.getId() + " is already locked");
  }

  @Override
  public void unlock() {
    door.setState(new Unlocked(door));
  }

  @Override
  public String getName() {
    return "locked";
  }
}
