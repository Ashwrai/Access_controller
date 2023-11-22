package server.building;

import java.util.HashSet;
import java.util.Set;
import server.Door;
import server.Visitor;

// - Purpose:
//    Represents a single room or space
//    in the building which can be accessed through one or more doors.
public class Space extends Area {
  private final Set<Door> doors;

  public Space(String name) {
    super(name);
    this.doors = new HashSet<>();
  }


  public void accept(final AreaVisitor visitor) {
    visitor.visit(this);
  }
  public void addDoor(Door door){
    this.doors.add(door);
  }
  public Set<Door> getDoors(){
    return this.doors;
  }
}
