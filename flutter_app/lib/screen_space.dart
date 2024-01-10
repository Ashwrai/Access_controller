import 'package:exercise_flutter_acs/screen_partition.dart';
import 'requests.dart';
import 'package:flutter/material.dart';
import 'tree.dart';
import 'flutter_gen/gen_l10n/app_localizations.dart';

class ScreenSpace extends StatefulWidget {
  final String id;
  final Function(Locale) setLocale;

  const ScreenSpace({super.key, required this.id, required this.setLocale});

  @override
  State<ScreenSpace> createState() => _StateScreenSpace();
}

class _StateScreenSpace extends State<ScreenSpace> {
  late Future<Tree> futureTree;
  List<String> reasons = [];

  @override
  void initState() {
    super.initState();
    futureTree = getTree(widget.id);
  }

  @override
  void setState(VoidCallback fn) {
    super.setState(fn);
    _showAlerts();
  }

  void _showAlerts() {
    for (String reason in reasons) {
      Future.delayed(Duration.zero, () {
        _showAlertDialog(reason);
      });
    }
  }

  void _showAlertDialog(String item) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Error'),
          content: Text(item),
          actions: <Widget>[
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: const Text('OK'),
            ),
          ],
        );
      },
    );
  }

// future with listview
// https://medium.com/nonstopio/flutter-future-builder-with-list-view-builder-d7212314e8c9
  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(
      future: futureTree,
      builder: (context, snapshot) {
        // anonymous function
        if (snapshot.hasData) {
          return Scaffold(
            appBar: AppBar(
              backgroundColor: Theme.of(context).colorScheme.primary,
              foregroundColor: Theme.of(context).colorScheme.onPrimary,
              title: Text(snapshot.data!.root.id),
              actions: <Widget>[
                IconButton(
                    icon: const Icon(Icons.home),
                    onPressed: () {
                      Navigator.of(context).pushAndRemoveUntil(
                        MaterialPageRoute(
                            builder: (context) => ScreenPartition(
                                id: "ROOT",
                                showMenu: true,
                                setLocale: widget.setLocale)),
                        (Route<dynamic> route) => false,
                      );
                    }),
                //TODO other actions
              ],
            ),
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.root.children.length,
              itemBuilder: (BuildContext context, int i) =>
                  _buildRow(snapshot.data!.root.children[i], i),
              separatorBuilder: (BuildContext context, int index) =>
                  const Divider(),
            ),
          );
        } else if (snapshot.hasError) {
          return Text("${snapshot.error}");
        }
        // By default, show a progress indicator
        return Container(
            height: MediaQuery.of(context).size.height,
            color: Colors.white,
            child: const Center(
              child: CircularProgressIndicator(),
            ));
      },
    );
  }

  Widget _buildRow(Door door, int index) {
    IconButton doorIcon;
    IconButton lockIcon;
    IconButton shortIcon;

    shortIcon = IconButton(
        icon: const Icon(
          Icons.lock_clock_outlined,
        ),
        onPressed: () async {
          reasons = await unlockShortly(door.id, false);
          _refresh();
        });

    if (door.closed) {
      doorIcon = IconButton(
        icon: const Icon(Icons.door_front_door_sharp),
        onPressed: () async {
          reasons = await open(door.id, false);
          _refresh();
        },
      );
    } else {
      doorIcon = IconButton(
        icon: const Icon(Icons.meeting_room_sharp),
        onPressed: () async {
          reasons = await close(door.id, false);
          _refresh();
        },
      );
    }

    if (door.state == 'unlocked') {
      lockIcon = IconButton(
        icon: const Icon(Icons.lock_open_sharp),
        onPressed: () async {
          reasons = await lock(door.id, false);
          _refresh();
        },
      );
    } else if (door.state == 'locked') {
      lockIcon = IconButton(
        icon: const Icon(Icons.lock_sharp),
        onPressed: () async {
          reasons = await unlock(door.id, false);
          _refresh();
        },
      );
    } else if (door.state == 'propped') {
      lockIcon = IconButton(
        icon: const Icon(Icons.lock_sharp),
        onPressed: () async {
          reasons = await unlock(door.id, false);
          _refresh();
        },
      );
      shortIcon = IconButton(
          icon: const Icon(
            Icons.warning,
          ),
          onPressed: () async {
            reasons = await unlockShortly(door.id, false);
            _refresh();
          });
    } else if (door.state == 'unlocked_shortly') {
      lockIcon = IconButton(
        icon: const Icon(Icons.lock_sharp),
        onPressed: () async {
          reasons = await unlock(door.id, false);
          _refresh();
        },
      );
      shortIcon = IconButton(
          icon: const Icon(
            Icons.punch_clock,
          ),
          onPressed: () async {
            reasons = await unlockShortly(door.id, false);
            _refresh();
          });
    } else {
      lockIcon = IconButton(
        icon: const Icon(Icons.lock_sharp),
        onPressed: () async {
          reasons = await unlock(door.id, false);
          _refresh();
        },
      );
      lockIcon = IconButton(
        icon: const Icon(Icons.lock_sharp),
        onPressed: () async {
          reasons = await unlock(door.id, false);
          _refresh();
        },
      );
    }

    return ListTile(
      leading: const Icon(Icons.sensor_door),
      title: Text(door.id),
      trailing: Row(
        mainAxisSize:
            MainAxisSize.min, // Ensure widgets take minimum space horizontally
        children: [
          lockIcon,
          shortIcon,
          doorIcon,
        ],
      ),
    );
  }

  void _refresh() async {
    futureTree = getTree(widget.id);
    setState(() {});
  }

  // '${door.state}, closed=${door.closed}'
}
