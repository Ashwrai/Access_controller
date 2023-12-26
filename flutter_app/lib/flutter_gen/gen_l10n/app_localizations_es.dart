import 'app_localizations.dart';

/// The translations for Spanish Castilian (`es`).
class AppLocalizationsEs extends AppLocalizations {
  AppLocalizationsEs([String locale = 'es']) : super(locale);

  @override
  String get title => 'Sistema de Control de Acceso';

  @override
  String get menu => 'Menú';

  @override
  String get spaces => 'Espacios';

  @override
  String get recent => 'Recientes';

  @override
  String get lockedDoors => 'Puertas Bloqueadas';

  @override
  String get error => 'Error';

  @override
  String get ok => 'Aceptar';

  @override
  String get openAction => 'Abrir';

  @override
  String get closeAction => 'Cerrar';

  @override
  String get lockAction => 'Bloquear';

  @override
  String get unlockAction => 'Desbloquear';

  @override
  String get unlockShortlyAction => 'Desbloqueo Temporal';

  @override
  String get adminGroup => 'Administradores';

  @override
  String get managerGroup => 'Gerentes';

  @override
  String get employeeGroup => 'Empleados';

  @override
  String get openDoor => 'Abrir Puerta';

  @override
  String get closeDoor => 'Cerrar Puerta';

  @override
  String get unlockDoor => 'Desbloquear Puerta';

  @override
  String get lockDoor => 'Bloquear Puerta';
}
