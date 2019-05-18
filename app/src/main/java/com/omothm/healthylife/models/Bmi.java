package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;

public class Bmi {

  private SQLiteDate date;
  private float value;

  public SQLiteDate getDate() {
    return date;
  }

  public float getValue() {
    return value;
  }
}
