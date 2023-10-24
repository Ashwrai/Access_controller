package baseNoStates.building;


import baseNoStates.Door;

import java.util.Set;

public abstract class Area {
  // - Purpose:
  //    Abstract representation of a space or partition in the building.
  //    All specific areas should extend from this class.
  private String name;

  public Area(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public abstract Set<Door> getDoors();
}
