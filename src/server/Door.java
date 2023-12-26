package server;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.building.Space;
import server.requests.RequestChildren;
import server.requests.RequestReader;
import server.roles.Reasons;
import server.state.Locked;
import server.state.State;

public class Door {

  private static Logger logger = LoggerFactory.getLogger(Door.class);

  // Unique identifier for the door
  private final String id;
  private State state;
  private boolean closed;
  private Space from;
  private Space to;
  private RequestReader request;

  public Door(String id, Space from, Space to) {
    this.id = id;
    this.closed = true;

    this.state = new Locked(this);

    // Spaces associated with the door
    this.from = from;
    this.to = to;

    // Register the door with the destination space
    this.to.addDoor(this);
  }

  public void addReasons(String msg) {
    request.addReason(msg);
  }

  public void processRequest(RequestReader request) {
    this.request = request;
    // it is the Door that process the request because the door has and knows
    // its state, and if closed or open
    if (request.isAuthorized()) {
      logger.debug("processed request for door " + this.id);
      String action = request.getAction();
      doAction(action);
    } else {
      logger.warn("unauthorized request for door " + this.id);
    }

    // Set the door state in the request
    request.setDoorStateName(getStateName());
  }

  // Perform the specified action on the door based on its current state
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
        logger.warn("unknown action for door " + this.id + ": " + action);

        // Terminate the program due to an unexpected situation
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

