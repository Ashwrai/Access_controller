package baseNoStates.roles;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class Permission {
    private LocalTime startTime;
    private LocalTime endTime;
    private List<String> days;  // ["Monday", "Tuesday", ...]
    private List<String> actions;  // ["open", "close", ...]
    private List<String> spaces;  // ["room1", "room2", "parking", ...]

    public Permission(LocalTime startTime, LocalTime endTime, List<String> days,
                      List<String> actions, List<String> spaces) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = days;
        this.actions = actions;
        this.spaces = spaces;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<String> getActions() {
        return actions;
    }

    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public List<String> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<String> spaces) {
        this.spaces = spaces;
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
