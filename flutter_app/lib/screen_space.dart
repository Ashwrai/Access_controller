import 'package:flutter/material.dart';
import 'tree.dart';

class ScreenSpace extends StatefulWidget {
  final String id;

  const ScreenSpace({super.key, required this.id});

  @override
  State<ScreenSpace> createState() => _StateScreenSpace();
}

class _StateScreenSpace extends State<ScreenSpace> {
  late Tree tree;

  @override
  void initState() {
    super.initState();
    tree = getTree(widget.id);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(tree.root.id),
        actions: <Widget>[
          IconButton(icon: const Icon(Icons.home), onPressed: () {}
              // TODO go home page = root
              ),
          //TODO other actions
        ],
      ),
      body: ListView.separated(
        // it's like ListView.builder() but better
        // because it includes a separator between items
        padding: const EdgeInsets.all(16.0),
        itemCount: tree.root.children.length,
        itemBuilder: (BuildContext context, int index) =>
            _buildRow(tree.root.children[index], index),
        separatorBuilder: (BuildContext context, int index) => const Divider(),
      ),
    );
  }

  Widget _buildRow(Door door, int index) {
    return ListTile(
      title: Text('D ${door.id}'),
      trailing: Text('${door.state}, closed=${door.closed}'),
    );
  }
}
