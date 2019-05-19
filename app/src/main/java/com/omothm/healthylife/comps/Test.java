package com.omothm.healthylife.comps;

import android.app.Activity;

public class Test {

  private final Activity activity;
  private final String name;
  private String date;
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
