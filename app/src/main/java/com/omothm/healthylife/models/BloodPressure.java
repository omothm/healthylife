package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.activities.BloodPressureActivity;
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

  public static String getAnalysis(Context context, int topNumber,
      int bottomNumber) {
    final StringBuilder builder = new StringBuilder();
    if (topNumber < 90) {
      builder.append("Low");
    } else if (topNumber < 120) {
      builder.append("Ideal");
    } else if (topNumber < 140) {
      builder.append("Pre-high");
    } else {
      builder.append("High");
    }
    builder.append(" / ");
    if (bottomNumber < 60) {
      builder.append("Low");
    } else if (bottomNumber < 80) {
      builder.append("Ideal");
    } else if (bottomNumber < 90) {
      builder.append("Pre-high");
    } else {
      builder.append("High");
    }
    return builder.toString();
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
    return top + " / " + bottom;
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
