package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.activities.BloodSugarActivity;
import com.omothm.healthylife.db.SQLiteDate;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class BloodSugar extends Model {

  private SQLiteDate date;
  private int value;

  public BloodSugar(final SQLiteDate date, final int value) {
    this.date = date;
    this.value = value;
  }

  public static String getAnalysis(Context context, int bs) {
    if (bs < 140) {
      return "Normal";
    } else {
      return "Diabetic";
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
    return String.valueOf(value);
  }

  public float getValue() {
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
