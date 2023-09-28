package baseNoStates.requests;

import org.json.JSONObject;

public interface Request {
  JSONObject answerToJson();

  String toString();

  void process();
}
