package com.omothm.healthylife.db;

import java.util.Calendar;
import org.jetbrains.annotations.NotNull;

public class SQLiteDate {

  private final Calendar cal;

  {
    cal = Calendar.getInstance();
    final int day = cal.get(Calendar.DAY_OF_MONTH);
    final int month = cal.get(Calendar.MONTH);
    final int year = cal.get(Calendar.YEAR);
    cal.clear();
    cal.set(year, month, day);
    // This tells the calendar to not accept values outside the normalized range and throw an
    // exception. If the calendar is set to be lenient, it would accept 31 February as 3 March.
    cal.setLenient(false);
  }

  public SQLiteDate() { }

  public SQLiteDate(long millis) {
    setMillis(millis);
  }

  public SQLiteDate(int year, int month, int day) {
    setYear(year);
    setMonth(month);
    setDay(day);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SQLiteDate that = (SQLiteDate) o;
    return cal.equals(that.cal);
  }

  public int getDay() {
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  public void setDay(final int day) {
    cal.set(Calendar.DAY_OF_MONTH, day);
  }

  public long getMillis() {
    return cal.getTime().getTime();
  }

  public void setMillis(long millis) {
    cal.setTimeInMillis(millis);
  }

  /**
   * @return 1 for January, 12 for December
   */
  public int getMonth() {
    return cal.get(Calendar.MONTH) + 1;
  }

  /**
   * @param month 1 for January, 12 for December
   */
  public void setMonth(final int month) {
    cal.set(Calendar.MONTH, month - 1);
  }

  public int getYear() {
    return cal.get(Calendar.YEAR);
  }

  public void setYear(final int year) {
    cal.set(Calendar.YEAR, year);
  }

  @Override
  public int hashCode() {
    return cal.hashCode();
  }

  @NotNull
  @Override
  public String toString() {
    return getYear() + "-" + getMonth() + "-" + getDay();
  }
}
