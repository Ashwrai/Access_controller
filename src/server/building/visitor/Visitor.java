package server.building.visitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.building.Partition;
import server.building.Space;

public interface Visitor {
  static final Logger logger = LoggerFactory.getLogger(Visitor.class);

  void visitSpace(Space space);

  void visitPartition(Partition partition);

}
