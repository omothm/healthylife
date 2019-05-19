package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;
import org.jetbrains.annotations.NotNull;

public class BloodSugar extends Model {

  private SQLiteDate date;
  private float value;

  public BloodSugar(final SQLiteDate date, final float value) {
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

  public float getValue() {
    return value;
  }

  public void setValue(float value) {
    this.value = value;
  }

  @NotNull
  @Override
  public String toString() {
    return date.toString() + ": " + getStringValue();
  }
}
