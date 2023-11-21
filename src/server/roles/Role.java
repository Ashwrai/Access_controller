package server.roles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import server.building.Space;


public class Role {
  // Class role is added as an attribute in User, so it is more easily accessed to get its permission once we get the
  // User from its credentials
  // TODO hacer los condition checks dentro de la clase permission directamente
  protected String id;
  protected boolean superuser;
  private final Set<String> actions;  // ["open", "close", ...]
  private final Set<String> spaces;  // ["room1", "room2", "parking", ...]
  private final Schedule schedule;

  public Role(String id, Schedule schedule, Set<String> actions, Set<String> spaces) {
    this.id = id;
    this.superuser = false;
    this.schedule = schedule;
    this.actions = actions;
    this.spaces = spaces;
  }

  public Role(String id) {
    this.id = id;
    this.superuser = id.equals(RoleId.ADMINISTRATOR);
    this.schedule = null;
    this.actions = null;
    this.spaces = null;
  }


  public Set<String> getActions() {
    return actions;
  }

  public Set<String> getSpaces() {
    return spaces;
  }


  @Override
  public String toString() {
    return "Permission{"
        + "startTime=" + schedule.getStartTime()
        + ", endTime=" + schedule.getEndTime()
        + ", days=" + Arrays.toString(schedule.getDays().toArray())
        + ", actions=" + Arrays.toString(actions.toArray())
        + ", spaces=" + Arrays.toString(spaces.toArray())
        + '}';
  }

  public HashSet<String> hasPermission(DayOfWeek day, LocalDate date, LocalTime time, Space accessed, Space from, String action) {
    HashSet<String> reasons = new HashSet<>();
    if (superuser) {
      return reasons;
    }
    if (id.equals(RoleId.BLANK)) {
      reasons.add(Reasons.NO_ROLE_GRANTED);
      return reasons;
    }
    reasons.add(Reasons.NOT_WITHIN_DATE);
    reasons.add(Reasons.NOT_WITHIN_TIME);
    reasons.add(Reasons.RESTRICTED_AREA);
    reasons.add(Reasons.NOT_WITHIN_DAY_OF_WEEK);
    reasons.add(Reasons.ACTION_DISALLOWED);

    // user has a permission that is specific to that area
    if (getSpaces().contains(accessed.getName())) {
      reasons.remove(Reasons.RESTRICTED_AREA);

      // user has a permission within that date range
      if (schedule.getStartDate().isBefore(date) && schedule.getEndDate().isAfter(date)) {
        reasons.remove(Reasons.NOT_WITHIN_DATE);
      }

      // the permission for that area is valid within today
      if (schedule.getDays().contains(day)) {
        reasons.remove(Reasons.NOT_WITHIN_DAY_OF_WEEK);
      }

      // the permission is valid within the time range
      if (time.isAfter(schedule.getStartTime()) && time.isBefore(schedule.getEndTime())) {
        reasons.remove(Reasons.NOT_WITHIN_TIME);
      }

      if (getActions().contains(action)) {
        reasons.remove(Reasons.ACTION_DISALLOWED);
      }
    }
    return reasons;
  }
}
