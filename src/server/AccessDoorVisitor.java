package server;

import server.building.Area;
import server.building.AreaVisitor;
import server.building.Partition;
import server.building.Space;

import java.util.ArrayList;
import java.util.HashSet;

public class AccessDoorVisitor implements AreaVisitor {
  private final ArrayList<Door> doors = new ArrayList<>();

  public void visit(final Space space) {
    doors.addAll(space.getDoors());
  }

  public void visit(final Partition partition) {
    for (Area area : partition.getAreas()) {
      area.accept(this);
    }
  }

  public ArrayList<Door> getDoors() {
    return doors;
  }
}
