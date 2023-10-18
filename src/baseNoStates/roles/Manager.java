package baseNoStates.roles;

import baseNoStates.Actions;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Set;

public class Manager extends Role {
    public Manager() {
        permission = (new Permission(
                LocalDate.of(2023, Month.SEPTEMBER, 1),
                LocalDate.of(2024, Month.MARCH, 1),
                LocalTime.of(8, 0),
                LocalTime.of(20, 0),
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY),
                Set.of(Actions.OPEN, Actions.CLOSE, Actions.UNLOCK, Actions.LOCK, Actions.UNLOCK_SHORTLY),
                Set.of("room 1", "room 2", "room 3", "hall", "parking", "stairs", "exterior", "corridor", "IT")
        ));
    }
}
