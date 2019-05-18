package com.omothm.healthylife.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import com.omothm.healthylife.db.Contract.BmiEntry;

/**
 * The implementation of {@link SQLiteOpenHelper} for managing the creation and upgrade of
 * databases.
 */
public class DbHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "healthy_life.db";
  private static final int VERSION_NUMBER = 1;

  public DbHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, VERSION_NUMBER);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(BmiEntry.CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // For now, we'll just drop the current tables and create new ones
    db.execSQL("DROP TABLE IF EXISTS " + BmiEntry.TABLE_NAME);
    onCreate(db);
  }
}
