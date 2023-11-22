package server.building;

import java.util.Set;
import server.Door;

public abstract class Area {
  // - Purpose:
  //    Abstract representation of a space or partition in the building.
  //    All specific areas should extend from this class.
  private final String name;

  public Area(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract Set<Door> getDoors();
  public abstract void accept(AreaVisitor visitor);
}
