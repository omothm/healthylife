package com.omothm.healthylife.models;

import android.content.Context;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.SQLiteDate;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;

public class Eer extends Model {

  private SQLiteDate date;
  private float value;

  public Eer(final Context context, final SQLiteDate date, final float value) {
    super(context);
    this.date = date;
    this.value = value;
  }

  public static float calculate(boolean gender, int age, float weight, float height,
      float activity) {
    return gender ? (float) (662 - (9.53 * age) + activity * ((15.91 * weight) + (539.6 * height)))
               // male
               : (float) (354 - (6.91 * age) + activity * ((9.36 * weight) + (726
                                                                                  * height))); // female
  }

  public static String getAnalysis(Context context, float eer) {
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
    return String.format(Locale.getDefault(), "%.1f", value) + " " + context.getString(R.string.eer_unit);
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
