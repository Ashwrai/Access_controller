import 'package:exercise_flutter_acs/screen_propped.dart';

import 'requests.dart';
import 'package:flutter/material.dart';
import 'tree.dart';
import 'screen_space.dart';
import 'flutter_gen/gen_l10n/app_localizations.dart';

class ScreenPartition extends StatefulWidget {
  final String id;
  final bool showMenu;
  final Function(Locale) setLocale;

  const ScreenPartition({
    Key? key,
    required this.id,
    this.showMenu = false,
    required this.setLocale,
  }) : super(key: key);

  @override
  State<ScreenPartition> createState() => _ScreenPartitionState();
}

class _ScreenPartitionState extends State<ScreenPartition> {
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

  void addRecentArea(String spaceId) {
    setState(() {
      // Elimina el espacio si ya existe en la lista
      SpaceStore.recentAreas.remove(spaceId);
      // Agregar al inicio de la lista para que los más recientes aparezcan primero
      SpaceStore.recentAreas.insert(0, spaceId);
    });
  }
    void addRecentSpace(String spaceId) {
    setState(() {
      // Elimina el espacio si ya existe en la lista
      SpaceStore.recentSpaces.remove(spaceId);
      // Agregar al inicio de la lista para que los más recientes aparezcan primero
      SpaceStore.recentSpaces.insert(0, spaceId);
    });
  }

  void navigateToSpace(String spaceId) {
    // Aquí puedes manejar la navegación a un espacio específico
    // Por ejemplo, si cada espacio tiene una pantalla asociada, puedes hacer algo como esto:
    Navigator.of(context).push(MaterialPageRoute<void>(
      builder: (context) => ScreenSpace(
        id: spaceId,
        setLocale: widget.setLocale,
      ),
    ));
    // No olvides implementar la lógica necesaria en ScreenSpace para manejar el id correctamente
  }

  void _navigateDownPartition(String childId) {
    addRecentArea(childId);
    Navigator.of(context).push(MaterialPageRoute<void>(
      builder: (context) => ScreenPartition(
        id: childId,
        showMenu: false,
        setLocale: widget.setLocale,
      ),
    ));
  }

  void _navigateDownSpace(String childId) {
    addRecentSpace(childId); // Añadir a recientes
    Navigator.of(context).push(MaterialPageRoute<void>(
      builder: (context) => ScreenSpace(
        id: childId,
        setLocale: widget.setLocale,
      ),
    ));
  }
// future with listview
// https://medium.com/nonstopio/flutter-future-builder-with-list-view-builder-d7212314e8c9

  void goToInitialMenu() {
    Navigator.of(context).pushAndRemoveUntil(
      MaterialPageRoute(
        builder: (context) => ScreenPartition(
          id: "ROOT",
          showMenu: true,
          setLocale: widget.setLocale,
        ),
      ),
      (Route<dynamic> route) => false,
    );
  }

void showRecentSpaces() {
  Navigator.of(context).push(MaterialPageRoute<void>(
    builder: (context) => Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context)!.recent),
        backgroundColor: Theme.of(context).colorScheme.primary,
        foregroundColor: Theme.of(context).colorScheme.onPrimary,
        leading: IconButton(
          icon: const Icon(Icons.arrow_back),
          onPressed: () => Navigator.of(context).pop(),
        ),
      ),
      body: ListView.builder(
        itemCount: SpaceStore.recentAreas.length,
        itemBuilder: (context, index) {
          return ExpansionTile(
            title: Text(SpaceStore.recentAreas[index]),
            children: [
              ListView.builder(
                shrinkWrap: true,
                physics: NeverScrollableScrollPhysics(),
                itemCount: SpaceStore.recentSpaces.length,
                itemBuilder: (context, spaceIndex) {
                  return ListTile(
                    title: Text(SpaceStore.recentSpaces[spaceIndex]),
                    onTap: () {
                      Navigator.of(context).pop(); // Close the recent list
                      _navigateDownSpace(SpaceStore.recentSpaces[spaceIndex]);
                    },
                  );
                },
              ),
            ],
          );
        },
      ),
    ),
  ));
}

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<Tree>(
      future: futureTree,
      builder: (context, snapshot) {
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
                            setLocale: widget.setLocale,
                          ),
                        ),
                        (Route<dynamic> route) => false,
                      );
                    }),
              ],
            ),
            // Drawer con las opciones de menú
            drawer: widget.showMenu
                ? Drawer(
                    child: ListView(
                      padding: EdgeInsets.zero,
                      children: <Widget>[
                        DrawerHeader(
                          decoration: BoxDecoration(
                            color: Theme.of(context).colorScheme.primary,
                          ),
                          child: Text(AppLocalizations.of(context)!.menu,
                              style: const TextStyle(color: Colors.white)),
                        ),
                        ListTile(
                            leading: const Icon(Icons.language),
                            title: Text(AppLocalizations.of(context)!.idioma),
                            onTap: () {
                              showDialog(
                                  context: context,
                                  builder: (BuildContext context) {
                                    return AlertDialog(
                                      title: const Text('Selecciona un idioma'),
                                      content: SingleChildScrollView(
                                          child: ListBody(children: <Widget>[
                                        GestureDetector(
                                          child: const Text('English'),
                                          onTap: () {
                                            widget
                                                .setLocale(const Locale('en'));
                                            Navigator.of(context).pop();
                                            setState(() {});
                                          },
                                        ),
                                        const Padding(
                                            padding: EdgeInsets.all(8.0)),
                                        GestureDetector(
                                          child: const Text('Español'),
                                          onTap: () {
                                            widget
                                                .setLocale(const Locale('es'));
                                            Navigator.of(context).pop();
                                            setState(() {});
                                          },
                                        ),
                                        const Padding(
                                            padding: EdgeInsets.all(8.0)),
                                        GestureDetector(
                                            child: const Text('Catalan'),
                                            onTap: () {
                                              widget.setLocale(
                                                  const Locale('ca'));
                                              Navigator.of(context).pop();
                                              setState(() {});
                                            })
                                      ])),
                                    );
                                  });
                            }),
                        ListTile(
                          leading: const Icon(Icons.business_outlined),
                          title: Text(AppLocalizations.of(context)!.spaces),
                          onTap: () {
                            Navigator.pop(context); // Cierra el Drawer
                            Navigator.of(context).pushAndRemoveUntil(
                              MaterialPageRoute(
                                  builder: (context) => ScreenPartition(
                                        id: "ROOT",
                                        showMenu: true,
                                        setLocale: widget.setLocale,
                                      )),
                              (Route<dynamic> route) => false,
                            );
                          },
                        ),
                        ListTile(
                          leading: const Icon(Icons.history),
                          title: Text(AppLocalizations.of(context)!.recent),
                          onTap: () {
                            Navigator.pop(context); // Cierra el Drawer
                            showRecentSpaces(); // Función para mostrar espacios recientes
                          },
                        ),
                        ListTile(
                          leading: const Icon(Icons.warning),
                          title: Text(AppLocalizations.of(context)!.propped),
                          onTap: () {
                            Navigator.of(context).push(MaterialPageRoute<void>(
                              builder: (context) =>
                                  ScreenPropped(setLocale: widget.setLocale),
                            ));
                          },
                        ),
                      ],
                    ),
                  )
                : null,
            body: ListView.separated(
              // it's like ListView.builder() but better because it includes a separator between items
              padding: const EdgeInsets.all(16.0),
              itemCount: snapshot.data!.root.children.length,
              itemBuilder: (BuildContext context, int i) =>
                  _buildRow(snapshot.data!.root.children[i], i),
              separatorBuilder: (BuildContext context, int index) =>
                  const Divider(),
            ),
            bottomNavigationBar: BottomAppBar(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                children: <Widget>[
                  ElevatedButton(
                    onPressed: () async {
                        reasons = await lockArea(snapshot.data!.root.id);
                        _refresh();
                    },
                    child: Text('Lock everything'),
                  ),
                  ElevatedButton(
                    onPressed: () async {
                        reasons = await unlockArea(snapshot.data!.root.id);
                        _refresh();
                    },
                    child: Text('Unlock everything'),
                  ),
                ],
              ),
            ),          );
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

  Widget _buildRow(Area area, int index) {
    assert(area is Partition || area is Space);
    if (area is Partition) {
      return ListTile(
        leading: const Icon(Icons.layers),
        title: Text(area.id),
        onTap: () => _navigateDownPartition(area.id),
      );
    } else {
      return ListTile(
        leading: const Icon(Icons.group),
        title: Text(area.id),
        onTap: () => _navigateDownSpace(area.id),
      );
    }
  }

  void _refresh() async {
    futureTree = getTree(widget.id);
    setState(() {});
  }
}

