package baseNoStates.building;

import java.util.ArrayList;

public class SpaceAggregationVisitor implements AreaVisitor{
  private ArrayList<Space> spaces = new ArrayList<>();
  public void visit(final Space space) {
    spaces.add(space);
  }

  public void visit(final Partition partition) {
    for (Area area : partition.getAreas()) {
      area.accept(this);
    }
  }

  public ArrayList<Space> getSpaces() {
    return spaces;
  }
}
