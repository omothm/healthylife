package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.SQLiteDate;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class Weight extends Model {

  private SQLiteDate date;
  private float value;

  public Weight(Context context, final SQLiteDate date, final float value) {
    super(context);
    this.date = date;
    this.value = value;
  }

  public static String getAnalysis(Context context, float weight) {
    return null;
  }

  public SQLiteDate getDate() {
    return date;
  }

  public void setDate(final SQLiteDate date) {
    this.date = date;
  }

  @Override
  public String getStringValue() {
    return String.format(Locale.getDefault(), "%.1f", value) + " " + context
                                                                   .getString(R.string.weight_unit);
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
