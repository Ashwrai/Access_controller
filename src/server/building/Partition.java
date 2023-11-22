package server.building;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import server.Door;
import server.building.visitor.Visitor;

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
    logger.debug("queried area list");
    for (Area area : areas) {
      logger.debug("> " + area.getName());
    }
    return this.areas;
  }

  public Set<Door> getDoors() {
    Set<Door> doors = new HashSet<>();
    for (Area area : this.areas) {
      doors.addAll(area.getDoors());
    }
    logger.debug("queried all doors");
    for (Door door : doors) {
      logger.debug("> " + door.getId());
    }
    return doors;
  }

  @Override
  public void accept(final Visitor visitor) {
    logger.debug("Visitor accepted");
    visitor.visitPartition(this);
  }
}
