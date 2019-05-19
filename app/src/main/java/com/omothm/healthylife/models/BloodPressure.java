package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;
import org.jetbrains.annotations.NotNull;

public class BloodPressure extends Model {

  private int bottom;
  private SQLiteDate date;
  private int top;

  public BloodPressure(final SQLiteDate date, final int top, final int bottom) {
    this.date = date;
    this.top = top;
    this.bottom = bottom;
  }

  public int getBottom() {
    return bottom;
  }

  public void setBottom(int bottom) {
    this.bottom = bottom;
  }

  public SQLiteDate getDate() {
    return date;
  }

  public void setDate(final SQLiteDate date) {
    this.date = date;
  }

  @Override
  public String getStringValue() {
    return top + "/" + bottom;
  }

  public int getTop() {
    return top;
  }

  public void setTop(int top) {
    this.top = top;
  }

  @NotNull
  @Override
  public String toString() {
    return date.toString() + ": " + getStringValue();
  }
}
