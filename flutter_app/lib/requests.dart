import 'package:exercise_flutter_acs/utils.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert' as convert;

import 'tree.dart';

const String baseUrl = "https://9f12969c1315d5d0e1339500d65a2101.serveo.net";
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
Future<http.Response> performGenericAction(String act, String id, bool area) async {
  if (area && (act == 'unlock_shortly' || act == 'open' || act == 'close')) {
    throw 'Invalid request';
  }
  String cred = "11343"; // credential, cred, change if needed
  DateTime date = DateTime.now();
  TimeOfDay time = TimeOfDay.now();
  String dt = Utils.formattedDateTime(date, time);
  String selector = area ? "areaId=$id" : "doorId=$id";
  String endpoint = area ? "area" : "reader";
  Uri uri = Uri.parse(
      "$baseUrl/$endpoint?credential=$cred&action=$act&datetime=$dt&$selector");
  final response = await http.get(uri);
  if(response.statusCode != 200){
    print("statusCode=$response.statusCode");
    throw Exception('failed to get answer to request $uri');
  } else {
  print(response.body);
  }
  return response;
}

Future<List<String>> performDoorAction(String act, String id) async {
  final response = await performGenericAction(act, id, false);
  Map<String, dynamic> decoded = convert.jsonDecode(response.body);
  List<String> reasons = List<String>.from(decoded["reasons"]);
  return reasons;
}

Future<List<String>> performAreaAction(String act, String id) async {
  final response = await performGenericAction(act, id, true);
  Map<String, dynamic> decoded = convert.jsonDecode(response.body);
  List<String> combinedReasons =[];
  for (var doorResponse in decoded["requestsDoors"]) {
    List<String> doorReasons = List<String>.from(doorResponse["reasons"]);
    combinedReasons.addAll(doorReasons);
  }  return combinedReasons;
}

Future<List<String>> lockArea(String id) async {
  return await performAreaAction("lock", id);
}

Future<List<String>> unlockArea(String id) async {
  return await performAreaAction("unlock", id);
}

Future<List<String>> lockDoor(String id) async {
  return performDoorAction('lock', id);
}

Future<List<String>> unlockDoorShortly(String id) async {
  return performDoorAction('unlock_shortly', id);
}

Future<List<String>> unlockDoor(String id) async {
  return performDoorAction('unlock', id);
}

Future<List<String>> openDoor(String id) async {
  return performDoorAction('open', id);
}

Future<List<String>> closeDoor(String id) async {
  return performDoorAction('close', id);
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

