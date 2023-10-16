package baseNoStates.roles;

public class Administrator extends Role {
    // The Administrator role can have a special implementation of hasPermission
    @Override
    public boolean hasPermission(Permission permission) {
        return true;  // that always returns true, since they can do anything.
    }
}
