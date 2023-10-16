package baseNoStates.building;

import java.util.ArrayList;

public class Partition extends Area{
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
