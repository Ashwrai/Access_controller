import 'app_localizations.dart';

/// The translations for Catalan Valencian (`ca`).
class AppLocalizationsCa extends AppLocalizations {
  AppLocalizationsCa([String locale = 'ca']) : super(locale);

  @override
  String get title => 'Sistema de Control d\'Accés';

  @override
  String get menu => 'Menú';

  @override
  String get spaces => 'Espais';

  @override
  String get recent => 'Recents';

  @override
  String get lockedDoors => 'Portes Bloquejades';

  @override
  String get error => 'Error';

  @override
  String get ok => 'D\'acord';

  @override
  String get openAction => 'Obrir';

  @override
  String get closeAction => 'Tancar';

  @override
  String get lockAction => 'Bloquejar';

  @override
  String get unlockAction => 'Desbloquejar';

  @override
  String get unlockShortlyAction => 'Desbloqueig Temporal';

  @override
  String get adminGroup => 'Administradors';

  @override
  String get managerGroup => 'Gestors';

  @override
  String get employeeGroup => 'Empleats';

  @override
  String get openDoor => 'Obrir Porta';

  @override
  String get closeDoor => 'Tancar Porta';

  @override
  String get unlockDoor => 'Desbloquejar Porta';

  @override
  String get lockDoor => 'Bloquejar Porta';
}
