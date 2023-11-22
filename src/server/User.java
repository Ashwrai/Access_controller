package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.roles.Role;

public class User {
  private static Logger logger = LoggerFactory.getLogger(User.class);
  private final String name;
  private final String credential;
  private Role role;

  public User(String name, String credential, Role role) {
    // Log the creation of the user with an info message
    logger.info("created user " + name);

    // Initialize user properties
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
