import 'package:exercise_flutter_acs/utils.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert' as convert;

import 'tree.dart';

const String baseUrl = "https://9a75d778577487f7ae212dcda8e831fb.serveo.net";
Future<Tree> getTree(String areaId) async {
  Uri uri = Uri.parse("$baseUrl/get_children?$areaId");
  final response = await http.get(uri);
  // response is NOT a Future because of await
  if (response.statusCode == 200) {
    // TODO: change prints by logs, use package Logger for instance
    // which is the most popular, see https://pub.dev/packages/logger
    print("statusCode=$response.statusCode");
    print(response.body);
    // If the server did return a 200 OK response, then parse the JSON.
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    return Tree(decoded);
  } else {
    // If the server did not return a 200 OK response, then throw an exception.
    print("statusCode=$response.statusCode");
    throw Exception('failed to get answer to request $uri');
  }
}

// http://localhost:8080/reader?credential=11343&action=unlock&datetime=2023-09-05T09:30&doorId=D2

Future<List<String>> performAction(String act, String id, bool area) async {
  if (area && (act == 'unlock_shortly' || act == 'open' || act == 'close')) {
    throw 'Invalid request';
  }
  String cred = "11343"; // credential, cred, change if needed
  DateTime date = DateTime.now();
  TimeOfDay time = TimeOfDay.now();
  String dt = Utils.formattedDateTime(date, time);
  String selector = area ? "areaId=$id" : "doorId=$id";
  Uri uri = Uri.parse(
      "$baseUrl/reader?credential=$cred&action=$act&datetime=$dt&$selector");
  final response = await http.get(uri);
  // response is NOT a Future because of await
  if (response.statusCode == 200) {
    // TODO: change prints by logs, use package Logger for instance
    // which is the most popular, see https://pub.dev/packages/logger
    print("statusCode=$response.statusCode");
    print(response.body);
    // If the server did return a 200 OK response, then parse the JSON.
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    List<String> reasons = List<String>.from(decoded["reasons"]);
    return reasons;
  } else {
    // If the server did not return a 200 OK response, then throw an exception.
    print("statusCode=$response.statusCode");
    throw Exception('failed to get answer to request $uri');
  }
}

Future<List<String>> lock(String id, bool area) async {
  return performAction('lock', id, area);
}

Future<List<String>> unlockShortly(String id, bool area) async {
  return performAction('unlock_shortly', id, area);
}

Future<List<String>> unlock(String id, bool area) async {
  return performAction('unlock', id, area);
}

Future<List<String>> open(String id, bool area) async {
  return performAction('open', id, area);
}

Future<List<String>> close(String id, bool area) async {
  return performAction('close', id, area);
}

Future<Tree> getProppedDoors() async {
  Uri uri = Uri.parse("$baseUrl/get_propped");
  final response = await http.get(uri);
  if (response.statusCode == 200) {
    // TODO: change prints by logs, use package Logger for instance
    // which is the most popular, see https://pub.dev/packages/logger
    print("statusCode=$response.statusCode");
    print(response.body);
    // If the server did return a 200 OK response, then parse the JSON.
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    return Tree(decoded);
  } else {
    // If the server did not return a 200 OK response, then throw an exception.
    print("statusCode=$response.statusCode");
    throw Exception('failed to get answer to request $uri');
  }
}
