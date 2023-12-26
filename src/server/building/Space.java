package server.building;

import org.json.JSONArray;
import org.json.JSONObject;
import server.Door;
import server.building.visitor.Visitor;

import java.util.HashSet;
import java.util.Set;

public class Space extends Area {
  // - Purpose:
  //    Represents a single room or space
  //    in the building which can be accessed through one or more doors.
  private final Set<Door> doors;

  public Space(String name) {
    super(name);
    this.doors = new HashSet<>();
  }

  public void addDoor(Door door) {
    this.doors.add(door);
  }

  public Set<Door> getDoors() {
    return this.doors;
  }

  public void accept(final Visitor visitor) {
    logger.debug("Visitor accepted");
    visitor.visitSpace(this);
  }

  public JSONObject toJson(int depth) { // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", getName());
    JSONArray jsonDoors = new JSONArray();
    for (Door d : getDoors()) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }

}
