package baseNoStates;
import baseNoStates.building.*;

import java.util.Set;

/*- Purpose:
    Manages and holds the information about different doors in the building.
- Design Pattern:
    Singleton Pattern: We're using a private static variable and public static methods to manage the doors.
*/

public final class DirectoryDoors {
  private static Set<Door> allDoors;
  public static void makeDoors() {

    Space exterior = DirectoryAreas.getSpaceByName("exterior");
    Space parking = DirectoryAreas.getSpaceByName("parking");
    Space stairs = DirectoryAreas.getSpaceByName("stairs");
    Space hall = DirectoryAreas.getSpaceByName("hall");
    Space room1 = DirectoryAreas.getSpaceByName("room1");
    Space room2 = DirectoryAreas.getSpaceByName("room2");
    Space corridor = DirectoryAreas.getSpaceByName("corridor");
    Space room3 = DirectoryAreas.getSpaceByName("room3");
    Space IT = DirectoryAreas.getSpaceByName("IT");

    // basement
    Door d1 = new Door("D1", exterior, parking); // exterior, parking
    Door d2 = new Door("D2", stairs, parking); // stairs, parking

    // ground floor
    Door d3 = new Door("D3", exterior, hall); // exterior, hall
    Door d4 = new Door("D4", stairs, hall); // stairs, hall
    Door d5 = new Door("D5", hall, room1); // hall, room1
    Door d6 = new Door("D6", hall, room2); // hall, room2

    // first floor
    Door d7 = new Door("D7", stairs, corridor); // stairs, corridor
    Door d8 = new Door("D8", corridor, room3); // corridor, room3
    Door d9 = new Door("D9", corridor, IT); // corridor, IT

    allDoors = Set.of(d1, d2, d3, d4, d5, d6, d7, d8, d9);
  }

  public static Door findDoorById(String id) {
    for (Door door : allDoors) {
      if (door.getId().equals(id)) {
        return door;
      }
    }
    System.out.println("door with id " + id + " not found");
    return null; // otherwise we get a Java error
  }

  // this is needed by RequestRefresh
  public static Set<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

}
