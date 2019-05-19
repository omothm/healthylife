package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.db.SQLiteDate;

/** Abtract data model containing an ID and a context.*/
public abstract class Model {

  protected final Context context;
  private long id;

  public Model(Context context) {
    this.context = context;
  }

  public abstract SQLiteDate getDate();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public abstract String getStringValue();
}
