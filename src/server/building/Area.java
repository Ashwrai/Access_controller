package server.building;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.Door;
import server.building.visitor.Visitor;

public abstract class Area {
  // - Purpose:
  //    Abstract representation of a space or partition in the building.
  //    All specific areas should extend from this class.
  protected static final Logger logger = LoggerFactory.getLogger(Area.class);

  private final String name;

  public Area(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public abstract Set<Door> getDoors();

  public abstract void accept(Visitor visitor);
}
