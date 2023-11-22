package server;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.building.Area;
import server.building.Partition;
import server.building.Space;
import server.building.visitor.FindAreaById;


public class DirectoryAreas {
  //  - Purpose:
  //  Manages and holds the information about different areas in the building.
  //  These areas can be both partitions and spaces.
  //      - Design Pattern:
  //  Singleton Pattern: Ensures that a class has only one instance and provides
  //  a global point to this instance. This is evident in the usage of a private
  //  static variable for areas and public static methods to access and
  //  manipulate it.

  private static final Logger logger = LoggerFactory.getLogger(DirectoryAreas.class);
  private static Area rootArea;
  private static Set<Area> areas;

  // This method initializes the areas in the building.
  public static void makeAreas() {
    Partition building = new Partition("building");
    rootArea = building;
    Partition basement = new Partition("basement");
    Partition groundFloor = new Partition("ground_floor");
    Partition firstFloor = new Partition("floor1");
    Space exterior = new Space("exterior");
    Space parking = new Space("parking");
    Space stairs = new Space("stairs");
    Space hall = new Space("hall");
    Space room1 = new Space("room1");
    Space room2 = new Space("room2");
    Space corridor = new Space("corridor");
    Space room3 = new Space("room3");
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

    DirectoryAreas.areas = Set.of(
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

  public static Area fromSet(Set<String> ids) {
    Partition area = new Partition(UUID.randomUUID().toString());
    for (String id : ids) {
      area.addArea(DirectoryAreas.findAreaById(id));
    }
    return area;
  }

  public static Area findAreaById(final String id) {
    FindAreaById visitor = new FindAreaById(id);
    rootArea.accept(visitor);
    return visitor.getResult();
  }

  // Returns a specific space by its name.
  public static Space getSpaceByName(String name) {
    Area area = DirectoryAreas.findAreaByName(name);
    if (area instanceof Space) {
      return (Space) area;
    }
    logger.warn("space with name " + name + " not found");
    return null;
  }

  // Returns a specific area by its name.
  public static Area findAreaByName(String name) {
    for (Area area : DirectoryAreas.areas) {
      if (area.getName().equals(name)) {
        return area;
      }
    }
    logger.warn("area with name " + name + " not found");
    return null; // otherwise we get a Java error
  }

  // Used by RequestRefresh to get all doors in the areas.
  public static Set<Area> getAllDoors() {
    logger.debug("queried all areas");
    for (Area area : DirectoryAreas.areas) {
      logger.debug("> " + area.getName());
    }
    return DirectoryAreas.areas;
  }

  public static Area getRootArea() {
    return rootArea;
  }

}
