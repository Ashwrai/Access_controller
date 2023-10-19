package baseNoStates.building;

import java.util.ArrayList;

public class Partition extends Area{
  // A partition is a group of spaces that makes a group, like a floor of a building
  private ArrayList<Area> areas;

  public Partition(String name) {
    super(name);
    this.areas = new ArrayList<Area>();
  }
  public void addArea(Area area){
    areas.add(area);
  }
  public ArrayList<Area> getAreas(){
    return this.areas;
  }

}
