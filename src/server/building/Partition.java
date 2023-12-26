package server.building;

import org.json.JSONArray;
import org.json.JSONObject;
import server.Door;
import server.building.visitor.Visitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Partition extends Area {
  // - Purpose:
  //   Represents a partition (a group of spaces) in the building.
  //   For example, a floor can be a partition that groups several rooms/spaces.
  private final ArrayList<Area> areas;

  public Partition(String name) {
    super(name);
    this.areas = new ArrayList<Area>();
  }

  public void addArea(Area area) {
    areas.add(area);
  }

  public ArrayList<Area> getAreas() {
    logger.debug("queried area list");
    for (Area area : areas) {
      logger.debug("> " + area.getName());
    }
    return this.areas;
  }

  public Set<Door> getDoors() {
    Set<Door> doors = new HashSet<>();
    for (Area area : this.areas) {
      doors.addAll(area.getDoors());
    }
    logger.debug("queried all doors");
    for (Door door : doors) {
      logger.debug("> " + door.getId());
    }
    return doors;
  }

  @Override
  public void accept(final Visitor visitor) {
    logger.debug("Visitor accepted");
    visitor.visitPartition(this);
  }

  public JSONObject toJson(int depth) {
    // for depth=1 only the root and children,
    // for recusive = all levels use Integer.MAX_VALUE
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", getName());
    JSONArray jsonAreas = new JSONArray();
    if (depth > 0) {
      for (Area a : areas) {
        jsonAreas.put(a.toJson(depth - 1));
      }
      json.put("areas", jsonAreas);
    }
    return json;
  }

}
