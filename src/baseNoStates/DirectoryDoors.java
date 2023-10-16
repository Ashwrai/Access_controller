package baseNoStates;

import baseNoStates.building.*;

import java.util.ArrayList;
import java.util.Arrays;

public final class DirectoryDoors {
  private static ArrayList<Door> allDoors;
  public static void makeDoors() {
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

    // basement
    basement.addArea(parking);
    Door d1 = new Door("D1", exterior, parking); // exterior, parking
    Door d2 = new Door("D2", stairs, parking); // stairs, parking

    // ground floor
    groundFloor.addArea(hall);
    groundFloor.addArea(room1);
    groundFloor.addArea(room2);
    Door d3 = new Door("D3", exterior, hall); // exterior, hall
    Door d4 = new Door("D4", stairs, hall); // stairs, hall
    Door d5 = new Door("D5", hall, room1); // hall, room1
    Door d6 = new Door("D6", hall, room2); // hall, room2

    // first floor
    firstFloor.addArea(room3);
    firstFloor.addArea(corridor);
    firstFloor.addArea(IT);
    Door d7 = new Door("D7", stairs, corridor); // stairs, corridor
    Door d8 = new Door("D8", corridor, room3); // corridor, room3
    Door d9 = new Door("D9", corridor, IT); // corridor, IT

    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));
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
  public static ArrayList<Door> getAllDoors() {
    System.out.println(allDoors);
    return allDoors;
  }

}
