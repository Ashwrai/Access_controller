package baseNoStates;

import baseNoStates.roles.*;

import java.util.ArrayList;

public final class DirectoryUsers {
  private static final ArrayList<User> users = new ArrayList<>();

  public static void makeUsers() {

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    Blank blank= new Blank();
    users.add(new User("Bernat", "12345",blank));
    users.add(new User("Blai", "77532", blank));

    // employees :
    // Sep. 1 2023 to Mar. 1 2024
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    Employee employee = new Employee();
    users.add(new User("Ernest", "74984", employee));
    users.add(new User("Eulalia", "43295", employee));


    // managers :
    // Sep. 1 2023 to Mar. 1 2024
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    Manager manager = new Manager();
    users.add(new User("Manel", "95783", manager));
    users.add(new User("Marta", "05827", manager));

    // admin :
    // always=2023 to 2100
    // all days of the week
    // all actions
    // all spaces
    Administrator administrator = new Administrator();
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
