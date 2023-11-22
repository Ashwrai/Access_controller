package server.requests;

import static server.DirectoryAreas.getRootArea;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import server.Actions;
import server.Door;
import server.building.Area;
import server.building.visitor.FindAreaById;
import server.building.visitor.GetPartitionDoors;



public class RequestArea implements Request {
  private final String credential;
  private final String action;
  private final String areaId;
  private final LocalDateTime now;
  private final ArrayList<RequestReader> requests = new ArrayList<>();


  public RequestArea(String credential, String action, LocalDateTime now, String areaId) {
    this.credential = credential;
    this.areaId = areaId;
    assert action.equals(Actions.LOCK) || action.equals(Actions.UNLOCK)
        : "invalid action " + action + " for an area request";
    this.action = action;
    this.now = now;
  }

  public String getAction() {
    return action;
  }

  @Override
  public JSONObject answerToJson() {
    JSONObject json = new JSONObject();
    json.put("action", action);
    json.put("areaId", areaId);
    JSONArray jsonRequests = new JSONArray();
    for (RequestReader rd : requests) {
      jsonRequests.put(rd.answerToJson());
    }
    json.put("requestsDoors", jsonRequests);
    return json;
  }

  @Override
  public String toString() {
    String requestsDoorsStr;
    if (requests.isEmpty()) {
      requestsDoorsStr = "";
    } else {
      requestsDoorsStr = requests.toString();
    }
    return "Request{"
        + "credential=" + credential
        + ", action=" + action
        + ", now=" + now
        + ", areaId=" + areaId
        + ", requestsDoors=" + requestsDoorsStr
        + "}";
  }

  // processing the request of an area is creating the corresponding door requests and forwarding
  // them to all of its doors. For some it may be authorized and action will be done, for others
  // it won't be authorized and nothing will happen to them.
  public void process() {
    FindAreaById findAreaVisitor = new FindAreaById(areaId);
    getRootArea().accept(findAreaVisitor);
    Area area = findAreaVisitor.getResult();
    if (area != null) {
      GetPartitionDoors doorVisitor = new GetPartitionDoors();
      area.accept(doorVisitor);
      ArrayList<Door> doors = doorVisitor.getDoors();
      for (Door door : doors) {
        RequestReader requestReader = new RequestReader(credential, action, now, door.getId());
        requestReader.process();
      }

    }
  }
}
