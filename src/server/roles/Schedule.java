package server.roles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

// used for limitting the span of a role permission
public class Schedule {
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final Set<DayOfWeek> days;  // ["Monday", "Tuesday", ...]

  public Schedule(LocalDate startDate, LocalDate endDate, LocalTime startTime,
                  LocalTime endTime, Set<DayOfWeek> days) {
    this.startDate = startDate;
    this.endDate = endDate;
    this.startTime = startTime;
    this.endTime = endTime;
    this.days = days;

  }

  public boolean isWithinDate(LocalDate date) {
    return this.getStartDate().isBefore(date) && this.getEndDate().isAfter(date);
  }

  public boolean isWithinDayOfWeek(DayOfWeek dayOfWeek) {
    return this.getDays().contains(dayOfWeek);
  }

  public boolean isWithinTime(LocalTime time) {
    return time.isAfter(this.getStartTime()) && time.isBefore(this.getEndTime());
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public Set<DayOfWeek> getDays() {
    return days;
  }

  public LocalTime getEndTime() {
    return endTime;
  }

  public LocalTime getStartTime() {
    return startTime;
  }
}
