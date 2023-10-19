package baseNoStates.roles;

import baseNoStates.building.Space;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class Role {
    // Class role is added as an attribute in User, so it is more easily accessed to get its permission once we get the
    // User from its credentials
    // TODO hacer los condition checks dentro de la clase permission directamente
    protected Permission permission;

    public HashSet<String> hasPermission(DayOfWeek day, LocalDate date, LocalTime time, Space accessed, String action) {
        HashSet<String> reasons = new HashSet<>();
        reasons.add(Reasons.NOT_WITHIN_DATE);
        reasons.add(Reasons.NOT_WITHIN_TIME);
        reasons.add(Reasons.RESTRICTED_AREA);
        reasons.add(Reasons.NOT_WITHIN_DAY_OF_WEEK);
        reasons.add(Reasons.ACTION_DISALLOWED);

        // user has a permission that is specific to that area
        if (permission.getSpaces().contains(accessed.getName())) {
            reasons.remove(Reasons.RESTRICTED_AREA);

            // user has a permission within that date range
            if (permission.getStartDate().isBefore(date) && permission.getEndDate().isAfter(date)) {
                reasons.remove(Reasons.NOT_WITHIN_DATE);
            }

            // the permission for that area is valid within today
            if (permission.getDays().contains(day)) {
                reasons.remove(Reasons.NOT_WITHIN_DAY_OF_WEEK);
            }

            // the permission is valid within the time range
            if (time.isAfter(permission.getStartTime()) && time.isBefore(permission.getEndTime())) {
                reasons.remove(Reasons.NOT_WITHIN_TIME);
            }

            if (permission.getActions().contains(action)) {
                reasons.remove(Reasons.ACTION_DISALLOWED);
            }
        }
        return reasons;
    }
}
