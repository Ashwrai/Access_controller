package server.roles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.building.Area;
import server.building.Partition;
import server.building.Space;
import server.building.visitor.GetSpacesFromArea;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class Role {
  // Class role is added as an attribute in User, so it is more easily
  // accessed to get its permission once we get the
  // User from its credentials
  private static final Logger logger = LoggerFactory.getLogger(Role.class);
  private final String id;
  private final boolean superuser;
  private final Set<String> actions;  // ["open", "close", ...]
  private final Area area;  // ["room1", "room2", "parking", ...]
  private final server.roles.Schedule schedule;


  public Role(String id, Schedule schedule, Set<String> actions, Area area) {
    if (!(area instanceof Partition)) {
      throw new RuntimeException("only partition areas are allowed");
    }
    this.id = id;
    this.superuser = false;
    this.schedule = schedule;
    this.actions = actions;
    this.area = area;
    logger.debug("Roles" + this.id + "created");
  }

  public Role(String id) {
    // creates an administrator role, that will be superuser if the id is the administrator id
    this.id = id;
    this.superuser = id.equals(RoleId.ADMINISTRATOR);
    this.schedule = null;
    this.actions = null;
    this.area = null;
    logger.debug("Roles" + this.id + "created");
  }

  public Set<String> getActions() {
    return actions;
  }

  public Area getArea() {
    return area;
  }


  @Override
  public String toString() {
    return "Permission{"
        + "startTime=" + schedule.getStartTime()
        + ", endTime=" + schedule.getEndTime()
        + ", days=" + Arrays.toString(schedule.getDays().toArray())
        + ", actions=" + Arrays.toString(actions.toArray())
        + '}';
  }


  public HashSet<String> hasPermission(DayOfWeek day, LocalDate date, LocalTime time,
                                       server.building.Space accessed, Space from, String action) {

    //  check if the role has permission for the specified action in the specified time

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

    GetSpacesFromArea visitor = new GetSpacesFromArea();
    visitor.visitPartition((Partition) getArea());
    area.accept(visitor);
    ArrayList<Space> spaces = visitor.getSpaces();

    if (spaces.contains(accessed)) {
      reasons.remove(Reasons.RESTRICTED_AREA);

      if (schedule.isWithinDate(date)) {
        reasons.remove(Reasons.NOT_WITHIN_DATE);
      }

      if (schedule.isWithinDayOfWeek(day)) {
        reasons.remove(Reasons.NOT_WITHIN_DAY_OF_WEEK);
      }

      if (schedule.isWithinTime(time)) {
        reasons.remove(Reasons.NOT_WITHIN_TIME);
      }

      if (getActions().contains(action)) {
        reasons.remove(Reasons.ACTION_DISALLOWED);
      }
    }

    logger.debug("Reasons for role " + id + "Accessing area " + accessed.getName());
    for (String reason : reasons) {
      logger.debug("> " + reason);
    }
    return reasons;
  }
}