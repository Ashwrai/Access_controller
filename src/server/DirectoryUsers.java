package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.roles.Role;
import server.roles.RoleId;
import server.roles.Schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Set;


public final class DirectoryUsers {
  //  - Purpose:
  //      Manages and holds the information about users and their permissions in the building.
  //  - Design Pattern
  //      Singleton Pattern: Similar to above classes, we're using
  //      a private static variable and public static methods to manage the users.
  private static Logger logger = LoggerFactory.getLogger(DirectoryUsers.class);
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    Role blank = new Role(RoleId.BLANK);
    users.add(new User("Bernat", "12345", blank));
    users.add(new User("Blai", "77532", blank));

    // employees :
    // Sep. 1 2023 to Mar. 1 2024
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    Role employee = new Role(
        RoleId.EMPLOYEE,
        new Schedule(
          LocalDate.of(2023, Month.SEPTEMBER, 1),
          LocalDate.of(2024, Month.MARCH, 1),
          LocalTime.of(9, 0),
          LocalTime.of(17, 0),
          Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
              DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)),
          Set.of(Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY),
          DirectoryAreas.fromSet(Set.of("exterior", "stairs", "hall", "room1",
              "room2", "corridor", "room3", "IT"))
    );
    users.add(new User("Ernest", "74984", employee));
    users.add(new User("Eulalia", "43295", employee));


    // managers :
    // Sep. 1 2023 to Mar. 1 2024
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    Role manager = new Role(
        RoleId.MANAGER,
        new Schedule(
        LocalDate.of(2023, Month.SEPTEMBER, 1),
        LocalDate.of(2024, Month.MARCH, 1),
        LocalTime.of(8, 0),
        LocalTime.of(20, 0),
        Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)),
        Set.of(Actions.OPEN, Actions.CLOSE, Actions.UNLOCK, Actions.LOCK, Actions.UNLOCK_SHORTLY),
        DirectoryAreas.fromSet(Set.of("room1", "room2", "room3", "hall", "parking",
            "stairs", "exterior", "corridor", "IT"))
    );
    users.add(new User("Manel", "95783", manager));
    users.add(new User("Marta", "05827", manager));

    // admin :
    // always=2023 to 2100
    // all days of the week
    // all actions
    // all spaces
    Role administrator = new Role(RoleId.ADMINISTRATOR);
    users.add(new User("Ana", "11343", administrator));
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    logger.warn("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
