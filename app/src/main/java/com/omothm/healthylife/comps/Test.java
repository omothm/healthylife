package com.omothm.healthylife.comps;

import android.app.Activity;

/** Test resembles a single test that the app monitors for the user. */
public class Test {

  /** The associated activity to open upon clicking on a test's entry. */
  private final Activity activity;
  /** The name of the test. */
  private final String name;
  /** The last test's date. */
  private String date;
  /** The last test's result. */
  private String result;

  public Test(final String name, final Activity activity) {
    this.name = name;
    this.activity = activity;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
