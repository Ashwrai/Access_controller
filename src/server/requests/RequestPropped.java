package server.requests;

import org.json.JSONArray;
import org.json.JSONObject;
import server.DirectoryDoors;
import server.Door;

import java.util.HashSet;
import java.util.Set;

public class RequestPropped implements Request {

  private Set<Door> proppedDoors;

  public RequestPropped() {
    proppedDoors = new HashSet<>();
  }

  @Override
  public void process() {
    for (Door door : DirectoryDoors.getAllDoors()) {
      if (door.isPropped()) {
        proppedDoors.add(door);
      }
    }
  }

  @Override
  public JSONObject answerToJson() {
    JSONObject result = new JSONObject();
    JSONArray array = new JSONArray();
    result.put("class", "prop");
    for (Door door : proppedDoors) {
      array.put(door.toJson());
    }
    result.put("propped", array);
    return result;
  }
}
