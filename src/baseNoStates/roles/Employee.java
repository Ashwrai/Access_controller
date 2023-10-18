package baseNoStates.roles;
import baseNoStates.Actions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Set;


public class Employee extends Role{
    public Employee() {
        permission = (new Permission(
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
        ));
    }
}
