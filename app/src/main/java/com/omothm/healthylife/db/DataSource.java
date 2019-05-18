package com.omothm.healthylife.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataSource {

  private static final String TAG = DataSource.class.getSimpleName();

  private final DbHelper dbHelper;
  private SQLiteDatabase database;

  public DataSource(final Context context) {
    dbHelper = new DbHelper(context);
  }

  public void open() {
    database = dbHelper.getWritableDatabase();
    Log.d(TAG, "Database opened");
  }

  public void close() {
    dbHelper.close();
    Log.d(TAG, "Database closed");
  }
}
