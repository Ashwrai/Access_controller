package baseNoStates.roles;

import java.util.ArrayList;
import java.util.List;

public abstract class Role {
    protected List<Permission> permissions = new ArrayList<>();

    public boolean hasPermission(Permission checkPermission) {
        for (Permission permission : permissions) {
            if (permission.equals(checkPermission)) {
                return true;
            }
        }
        return false;
    }
}
