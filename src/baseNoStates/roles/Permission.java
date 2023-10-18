package baseNoStates.roles;

import baseNoStates.Actions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.time.DayOfWeek;
public class Permission {
//    ArrayList<DayOfWeek> monToFri = new
//            ArrayList<>(Arrays.asList(DayOfWeek.MONDAY,
//            DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
//            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
//    ArrayList<DayOfWeek> monToSat = (ArrayList<DayOfWeek>)
//            monToFri.clone().add(DayOfWeek.SATURDAY);
//    LocalDate d = LocalDate.of(2023, 9, 1); // Sept 1 2023
//    LocalTime h = LocalTime.of(9, 0); // 9:00 AM
//    LocalDateTime dt1 = LocalDateTime.now();
//    //...
//    LocalDateTime dt2 = LocalDateTime.now();
//    Duration duration = Duration.between(dt1, dt2);
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<DayOfWeek> days;  // ["Monday", "Tuesday", ...]
    private Set<String> actions;  // ["open", "close", ...]
    private Set<String> spaces;  // ["room1", "room2", "parking", ...]

    public Permission(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Set<DayOfWeek> days,
                      Set<String> actions, Set<String> spaces) {
        this.startDate=startDate;
        this.endDate=endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
        this.actions = actions;
        this.spaces = spaces;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }


    public Set<DayOfWeek> getDays() {
        return days;
    }

    public Set<String> getActions() {
        return actions;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Set<String> getSpaces() {
        return spaces;
    }


    @Override
    public String toString() {
        return "Permission{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", days=" + Arrays.toString(days.toArray()) +
                ", actions=" + Arrays.toString(actions.toArray()) +
                ", spaces=" + Arrays.toString(spaces.toArray()) +
                '}';
    }

    // Puedes agregar otros métodos según lo necesites. Por ejemplo, un método
    // para verificar si un día específico está dentro de los días permitidos, etc.
}
