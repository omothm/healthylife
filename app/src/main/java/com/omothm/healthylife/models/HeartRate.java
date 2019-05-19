package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.SQLiteDate;
import org.jetbrains.annotations.NotNull;

public class HeartRate extends Model {

  private SQLiteDate date;
  private int value;

  public HeartRate(Context context, final SQLiteDate date, final int value) {
    super(context);
    this.date = date;
    this.value = value;
  }

  public static String getAnalysis(Context context, int hr) {
    if (hr > 40 && hr < 100) {
      return context.getString(R.string.normal);
    } else {
      return context.getString(R.string.abnormal);
    }
  }

  public SQLiteDate getDate() {
    return date;
  }

  public void setDate(final SQLiteDate date) {
    this.date = date;
  }

  @Override
  public String getStringValue() {
    return value + " " + context.getString(R.string.heart_rate_unit);
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
