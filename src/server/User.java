package server;

import server.roles.Role;

public class User {
  private final String name;
  private final String credential;
  private Role role;

  public User(String name, String credential, Role role) {
    this.name = name;
    this.credential = credential;
    this.role = role;
  }

  public String getCredential() {
    return credential;
  }

  public Role getRole() {
    return this.role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}