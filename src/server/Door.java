package server;

import server.requests.RequestReader;
import server.state.Locked;
import server.state.State;
import org.json.JSONObject;
import server.building.*;

public class Door {
  private final String id;
  private State state;
  private boolean closed;
  private Space from;
  private Space to;

  public Door(String id, Space from, Space to) {
    this.id = id;
    this.closed = true;
    this.state = new Locked(this);
    this.from = from;
    this.to = to;
    this.to.addDoor(this);
  }

  public void processRequest(RequestReader request) {
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      String action = request.getAction();
      doAction(action);
    } else {
      System.out.println("not authorized");
    }
    request.setDoorStateName(getStateName());
  }

  private void doAction(String action) {
    switch (action) {
      case Actions.OPEN:
        state.open();
        break;
      case Actions.CLOSE:
        state.close();
        break;
      case Actions.LOCK:
        state.lock();
        break;
      case Actions.UNLOCK:
        state.unlock();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlockShortly();
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public void setClose(boolean c) {
    closed = c;
  }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state.getName();
  }

  @Override
  public String toString() {
    return "Door{"
        + ", id='" + id + '\''
        + ", closed=" + closed
        + ", state=" + getStateName()
        + "}";
  }

  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("id", id);
    json.put("state", getStateName());
    json.put("closed", closed);
    return json;
  }

  public void setState(State state) {
    this.state = state;
  }

  public void setFrom(Space from) {
    this.from = from;
  }

  public void setTo(Space to) {
    this.to = to;
  }


  public Space getFrom() {
    return this.from;
  }

  public Space getTo() {
    return this.to;
  }
}

