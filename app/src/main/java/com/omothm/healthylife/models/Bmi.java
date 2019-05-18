package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;

public class Bmi {

  private SQLiteDate date;
  private float value;

  public Bmi(final SQLiteDate date, final float value) {
    this.date = date;
    this.value = value;
  }

  public SQLiteDate getDate() {
    return date;
  }

  public float getValue() {
    return value;
  }

  @Override
  public String toString() {
    return date.toString() + ": " + value;
  }
}
