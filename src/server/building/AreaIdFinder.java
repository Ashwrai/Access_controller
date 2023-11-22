package server.building;

public class AreaIdFinder implements AreaVisitor{

    private String targetId;

    private Area result;

  public AreaIdFinder(final String id) {
    this.targetId = id;
  }

  public void visit(final Space space) {
    if (space.getName().equals(targetId)) {
      result = space;
    }
  }


  public void visit(final Partition partition) {
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
