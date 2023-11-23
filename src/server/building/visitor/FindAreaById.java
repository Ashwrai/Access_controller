package server.building.visitor;

import server.Door;
import server.building.Area;
import server.building.Partition;
import server.building.Space;


public class FindAreaById implements Visitor {

  private final String targetId;
  private Area result;

  public FindAreaById(final String id) {
    this.targetId = id;
  }

  public void visitSpace(final Space space) {
    if (space.getName().equals(targetId)) {
      result = space;
    }
  }

  public void visitPartition(final Partition partition) {
    logger.debug(targetId);
    logger.debug(partition.getName());
    if (partition.getName().equals(targetId)) {
      result = partition;
    } else {
      for (Area area : partition.getAreas()) {

        area.accept(this);
        if (result != null) {
          break;
        }
      }
    }
  }

  public Area getResult() {
    return result;
  }
}
