package baseNoStates.roles;

import baseNoStates.Actions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.time.DayOfWeek;
public class Permission {
    // Class permission is used to store every rule that we need to check the allowance
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Set<DayOfWeek> days;  // ["Monday", "Tuesday", ...]
    private final Set<String> actions;  // ["open", "close", ...]
    private final Set<String> spaces;  // ["room1", "room2", "parking", ...]

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

}
