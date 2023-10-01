package baseNoStates;

import baseNoStates.requests.RequestReader;
import baseNoStates.state.Locked;
import baseNoStates.state.State;
import org.json.JSONObject;
import java.util.Timer;
import java.util.TimerTask;


public class Door {
  private final String id;
  private State state;
  private boolean closed;

  public Door(String id) {
    this.id = id;
    this.closed=true;
    this.state = new Locked(this);
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
        if (closed) {
          closed = false;
        } else {
          System.out.println("Can't open door " + id + " because it's already open");
        }
        break;
      case Actions.CLOSE:
        if (closed) {
          System.out.println("Can't close door " + id + " because it's already closed");
        } else {
          closed = true;
        }
        break;
      case Actions.LOCK:
        state.lock();
        break;
      case Actions.UNLOCK:
        state.unlocked();
        break;
      case Actions.UNLOCK_SHORTLY:
        state.unlocked();
        Timer timer= new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            state.lock();
          }
        }, 10000);
        //System.out.println("Action " + action + " not implemented yet");
        break;
      default:
        assert false : "Unknown action " + action;
        System.exit(-1);
    }
  }

  public boolean isClosed() {
    return closed;
  }

  public String getId() {
    return id;
  }

  public String getStateName() {
    return state.asString();
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
}
