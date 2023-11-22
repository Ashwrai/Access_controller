package server.building;

public interface AreaVisitor {
  /**
   * Visit space.
   * @param space The space to visit.
   */
  void visit(Space space);
  /**
   * Visit partition.
   * @param partition The partition to visit.
   */
  void visit(Partition partition);
}
