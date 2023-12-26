import 'package:intl/intl.dart';

class Utils {
  static final DateFormat _dateFormatter = DateFormat("yyyy-MM-dd");
  static final DateFormat _timeFormatter = DateFormat("HH:mm");

  static String formattedDate(date) {
    return _dateFormatter.format(date);
  }

  static String formattedTime(time) {
    return _timeFormatter.format(DateTime(1, 1, 1, time.hour, time.minute));
  }

  static String formattedDateTime(date, time) {
    return "${formattedDate(date)}T${formattedTime(time)}";
  }
}
