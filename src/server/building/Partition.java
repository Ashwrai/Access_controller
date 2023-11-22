package server.building;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import server.Door;

public class Partition extends Area {
  // - Purpose:
  //   Represents a partition (a group of spaces) in the building.
  //   For example, a floor can be a partition that groups several rooms/spaces.
  private final ArrayList<Area> areas;

  public Partition(String name) {
    super(name);
    this.areas = new ArrayList<Area>();
  }

  public void addArea(Area area) {
    areas.add(area);
  }

  public ArrayList<Area> getAreas() {
    return this.areas;
  }

  public Set<Door> getDoors() {
    Set<Door> doors = new HashSet<>();
    for (Area area : this.areas) {
      doors.addAll(area.getDoors());
    }
    return doors;
  }

  @Override
  public void accept(final AreaVisitor visitor) {
    visitor.visit(this);
  }
}
