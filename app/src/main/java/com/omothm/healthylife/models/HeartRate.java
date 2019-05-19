package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;
import org.jetbrains.annotations.NotNull;

public class HeartRate extends Model {

  private SQLiteDate date;
  private int value;

  public HeartRate(final SQLiteDate date, final int value) {
    this.date = date;
    this.value = value;
  }

  public SQLiteDate getDate() {
    return date;
  }

  public void setDate(final SQLiteDate date) {
    this.date = date;
  }

  @Override
  public String getStringValue() {
    return String.valueOf(value);
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  @NotNull
  @Override
  public String toString() {
    return date.toString() + ": " + getStringValue();
  }
}
