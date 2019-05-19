package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.SQLiteDate;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class Bmi extends Model {

  private SQLiteDate date;
  private float value;

  public Bmi(final Context context, final SQLiteDate date, final float value) {
    super(context);
    this.date = date;
    this.value = value;
  }

  public static float calculate(final float weight, final float height) {
    return weight / (height * height);
  }

  public static String getAnalysis(final Context context, final float bmi) {
    if (bmi < 18.5f) {
      return context.getString(R.string.underweight);
    } else if (bmi < 25f) {
      return context.getString(R.string.normal_weight);
    } else if (bmi < 30) {
      return context.getString(R.string.overweight);
    } else {
      return context.getString(R.string.obese);
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
    return String.format(Locale.getDefault(), "%.1f", value) + " " + context.getString(R.string.bmi_unit);
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
