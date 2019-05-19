package com.omothm.healthylife.models;

import com.omothm.healthylife.db.SQLiteDate;

public abstract class Model {

  private long id;

  public abstract SQLiteDate getDate();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public abstract String getStringValue();
}
