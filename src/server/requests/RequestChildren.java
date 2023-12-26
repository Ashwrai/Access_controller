package server.requests;

import org.json.JSONArray;
import org.json.JSONObject;
import server.DirectoryAreas;
import server.building.Area;

public class RequestChildren implements Request {

  private String areaId;
  private Area area;

  public RequestChildren(String areaId) {
    this.areaId = areaId;
  }

  @Override
  public JSONObject answerToJson() {
    return area.toJson(1);
  }

  @Override
  public void process() {
    area = DirectoryAreas.findAreaById(areaId);
  }
}
