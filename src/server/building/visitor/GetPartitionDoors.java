package server.building.visitor;

import server.Door;
import server.building.Area;
import server.building.Partition;
import server.building.Space;

import java.util.ArrayList;


public class GetPartitionDoors implements Visitor {
  private final ArrayList<Door> doors = new ArrayList<>();

  public void visitSpace(final Space space) {

    doors.addAll(space.getDoors());
  }

  public void visitPartition(final Partition partition) {
    for (Area area : partition.getAreas()) {
      area.accept(this);
    }
  }

  public ArrayList<Door> getDoors() {
    logger.debug("queried door list");
    for (Door door : doors) {
      logger.debug("> " + door.getId());
    }
    return doors;
  }
}
