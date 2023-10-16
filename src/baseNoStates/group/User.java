package baseNoStates.group;

public class User {
  private final String name;
  private final String credential;
  private final Group group;

  public User(String name, String credential, Group group) {
    this.name = name;
    this.credential = credential;
    this.group = group;
  }

  public String getCredential() {
    return credential;
  }

  @Override
  public String toString() {
    return "User{name=" + name + ", credential=" + credential + "}";
  }
}
