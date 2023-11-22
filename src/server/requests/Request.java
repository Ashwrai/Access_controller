package server.requests;

import org.json.JSONObject;

/* * Defines the essential behaviors for all request types:
represent as JSON, describe as a string, and process.*/
public interface Request {
  JSONObject answerToJson();

  String toString();

  void process();
}
