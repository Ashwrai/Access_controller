package server.building.visitor;

import java.util.ArrayList;
import server.Door;
import server.building.Area;
import server.building.Partition;
import server.building.Space;


public class GetSpacesFromArea implements Visitor {
  private final ArrayList<Space> spaces = new ArrayList<>();

  public void visitSpace(final Space space) {
    spaces.add(space);
  }

  public void visitPartition(final Partition partition) {
    for (Area area : partition.getAreas()) {
      area.accept(this);
    }
  }

  public ArrayList<Space> getSpaces() {
    return spaces;
  }
}
