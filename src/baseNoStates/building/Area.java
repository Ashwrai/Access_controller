package baseNoStates.building;


import baseNoStates.Door;

import java.util.Set;

public abstract class Area {
  // Class Area is used to represent either spaces or partitions
  private String name;

  public Area(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public abstract Set<Door> getDoors();
}
