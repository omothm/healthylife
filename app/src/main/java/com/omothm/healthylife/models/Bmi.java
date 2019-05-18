package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;
import org.jetbrains.annotations.NotNull;

public class Bmi {

  private SQLiteDate date;
  private long id;
  private float value;

  public Bmi(final SQLiteDate date, final float value) {
    this.date = date;
    this.value = value;
  }

  public SQLiteDate getDate() {
    return date;
  }

  public void setDate(final SQLiteDate date) {
    this.date = date;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
    return date.toString() + ": " + value;
  }
}
