package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.SQLiteDate;
import org.jetbrains.annotations.NotNull;

/** A model of the blood pressure entry in the database. */
public class BloodPressure extends Model {

  private int bottom; // bottom number
  private SQLiteDate date; // date of reading
  private int top; // top number

  public BloodPressure(Context context, final SQLiteDate date, final int top, final int bottom) {
    super(context);
    this.date = date;
    this.top = top;
    this.bottom = bottom;
  }

  public static String getAnalysis(Context context, int topNumber,
      int bottomNumber) {
    final StringBuilder builder = new StringBuilder();
    if (topNumber < 90) {
      builder.append(context.getString(R.string.low_pressure));
    } else if (topNumber < 120) {
      builder.append(context.getString(R.string.ideal_pressure));
    } else if (topNumber < 140) {
      builder.append(context.getString(R.string.pre_high_pressure));
    } else {
      builder.append(context.getString(R.string.high_pressure));
    }
    builder.append(" / ");
    if (bottomNumber < 60) {
      builder.append(context.getString(R.string.low_pressure));
    } else if (bottomNumber < 80) {
      builder.append(context.getString(R.string.ideal_pressure));
    } else if (bottomNumber < 90) {
      builder.append(context.getString(R.string.pre_high_pressure));
    } else {
      builder.append(context.getString(R.string.high_pressure));
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
