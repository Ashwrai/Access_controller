package baseNoStates;

import baseNoStates.roles.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Set;

/*- Purpose:
    Manages and holds the information about users and their permissions in the building.
- Design Pattern:
    Singleton Pattern: Similar to above classes, we're using a private static variable and public static methods to manage the users.*/

public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    Role blank = new Role(RoleId.BLANK, null, false);
    users.add(new User("Bernat", "12345",blank));
    users.add(new User("Blai", "77532", blank));

    // employees :
    // Sep. 1 2023 to Mar. 1 2024
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    Role employee = new Role(RoleId.EMPLOYEE, new Permission(
        LocalDate.of(2023, Month.SEPTEMBER, 1),
        LocalDate.of(2024, Month.MARCH, 1),
        LocalTime.of(9, 0),
        LocalTime.of(17, 0),
        Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY),
        Set.of(Actions.OPEN, Actions.CLOSE, Actions.UNLOCK_SHORTLY),
        Set.of(
            "exterior",
            "stairs",
            "hall",
            "room 1",
            "room 2",
            "corridor",
            "room 3",
            "IT"
        )
    ), false);
    users.add(new User("Ernest", "74984", employee));
    users.add(new User("Eulalia", "43295", employee));


    // managers :
    // Sep. 1 2023 to Mar. 1 2024
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    Role manager = new Role(RoleId.MANAGER, new Permission(
        LocalDate.of(2023, Month.SEPTEMBER, 1),
        LocalDate.of(2024, Month.MARCH, 1),
        LocalTime.of(8, 0),
        LocalTime.of(20, 0),
        Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
        Set.of(Actions.OPEN, Actions.CLOSE, Actions.UNLOCK, Actions.LOCK, Actions.UNLOCK_SHORTLY),
        Set.of("room 1", "room 2", "room 3", "hall", "parking", "stairs", "exterior", "corridor", "IT")
    ), false);
    users.add(new User("Manel", "95783", manager));
    users.add(new User("Marta", "05827", manager));

    // admin :
    // always=2023 to 2100
    // all days of the week
    // all actions
    // all spaces
    Role administrator = new Role(RoleId.ADMINISTRATOR, null, true);
    users.add(new User("Ana", "11343", administrator));
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
