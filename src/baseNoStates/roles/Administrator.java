package baseNoStates.roles;

import baseNoStates.building.Space;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;

public class Administrator extends Role {
    // The Administrator role can have a special implementation of hasPermission
    @Override
    public HashSet<String> hasPermission(DayOfWeek day, LocalDate date, LocalTime time, Space accessed, String action) {
        return new HashSet<>();  // that always returns true, since they can do anything.
    }
}
