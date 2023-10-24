package baseNoStates;

import baseNoStates.building.Area;
import baseNoStates.building.Partition;
import baseNoStates.building.Space;

import java.util.Set;

/*- Purpose:
    Manages and holds the information about different areas in the building. These areas can be both partitions and spaces.
- Design Pattern:
    Singleton Pattern: Ensures that a class has only one instance and provides a global point to this instance.
    This is evident in the usage of a private static variable for areas and public static methods to access and manipulate it.*/
public class DirectoryAreas {

  private static Set<Area> areas;

  // This method initializes the areas in the building.
  public static void makeAreas(){
    Partition building = new Partition("building");
    Partition basement = new Partition("basement");
    Partition groundFloor = new Partition("ground floor");
    Partition firstFloor = new Partition("floor 1");
    Space exterior = new Space("exterior");
    Space parking = new Space("parking");
    Space stairs = new Space("stairs");
    Space hall = new Space("hall");
    Space room1 = new Space("room 1");
    Space room2 = new Space("room 2");
    Space corridor = new Space("corridor");
    Space room3 = new Space("room 3");
    Space IT = new Space("IT");

    building.addArea(basement);
    building.addArea(groundFloor);
    building.addArea(firstFloor);
    building.addArea(exterior);
    building.addArea(stairs);

    basement.addArea(parking);

    groundFloor.addArea(hall);
    groundFloor.addArea(room1);
    groundFloor.addArea(room2);

    firstFloor.addArea(room3);
    firstFloor.addArea(corridor);
    firstFloor.addArea(IT);

    DirectoryAreas.areas=Set.of(
        building,
        basement,
        groundFloor,
        firstFloor,
        exterior,
        parking,
        stairs,
        hall,
        room1,
        room2,
        corridor,
        room3,
        IT
    );
  }

  // Returns a specific space by its name.
  public static Space getSpaceByName(String name) {
    Area area = DirectoryAreas.findAreaByName(name);
    if(area instanceof Space){
      return (Space) area;
    }
    System.out.println("space with name " +name + " not found");
    return null;
  }

  // Returns a specific area by its name.
  public static Area findAreaByName(String name) {
    for (Area area : DirectoryAreas.areas) {
      if (area.getName().equals(name)) {
        return area;
      }
    }
    System.out.println("area with name " +name + " not found");
    return null; // otherwise we get a Java error
  }

  // Used by RequestRefresh to get all doors in the areas.
  public static Set<Area> getAllDoors() {
    System.out.println(DirectoryAreas.areas);
    return DirectoryAreas.areas;
  }


}
