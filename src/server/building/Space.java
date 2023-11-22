package server.building;

import java.util.HashSet;
import java.util.Set;
import server.Door;
import server.building.visitor.Visitor;

public class Space extends Area {
  // - Purpose:
  //    Represents a single room or space
  //    in the building which can be accessed through one or more doors.
  private final Set<Door> doors;

  public Space(String name) {
    super(name);
    this.doors = new HashSet<>();
  }

  public void addDoor(Door door) {
    this.doors.add(door);
  }

  public Set<Door> getDoors() {
    return this.doors;
  }

  public void accept(final Visitor visitor) {
    logger.debug("Visitor accepted");
    visitor.visitSpace(this);
  }
}
