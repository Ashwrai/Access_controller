package baseNoStates;

import baseNoStates.roles.Role;

public class User {
  private final String name;
  private final String credential;
  private final Role role;

  public User(String name, String credential, Role role) {
    this.name = name;
    this.credential = credential;
    this.role = role;
  }

  public String getCredential() {
    return credential;
  }

  public Role getRole(){
    return this.role;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
