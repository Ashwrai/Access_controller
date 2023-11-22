package server.building.visitor;

import server.Door;
import server.building.Partition;
import server.building.Space;

public interface Visitor {

  void visitSpace(Space space);

  void visitPartition(Partition partition);

}
