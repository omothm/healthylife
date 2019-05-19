package com.omothm.healthylife.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import com.omothm.healthylife.db.Contract.BloodPressureEntry;
import com.omothm.healthylife.db.Contract.BloodSugarEntry;
import com.omothm.healthylife.db.Contract.BmiEntry;
import com.omothm.healthylife.db.Contract.EerEntry;
import com.omothm.healthylife.db.Contract.HeartRateEntry;
import com.omothm.healthylife.db.Contract.WeightEntry;

/**
 * The implementation of {@link SQLiteOpenHelper} for managing the creation and upgrade of
 * databases.
 */
public class DbHelper extends SQLiteOpenHelper {

  private static final String TAG = DbHelper.class.getSimpleName();

  private static final String DATABASE_NAME = "healthy_life.db";
  private static final int VERSION_NUMBER = 1;

  public DbHelper(@Nullable Context context) {
    super(context, DATABASE_NAME, null, VERSION_NUMBER);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(BmiEntry.CREATE_TABLE);
    db.execSQL(EerEntry.CREATE_TABLE);
    db.execSQL(BloodPressureEntry.CREATE_TABLE);
    db.execSQL(BloodSugarEntry.CREATE_TABLE);
    db.execSQL(HeartRateEntry.CREATE_TABLE);
    db.execSQL(WeightEntry.CREATE_TABLE);
  }

  public SQLiteDatabase open() {
    final SQLiteDatabase db = getWritableDatabase();
    Log.d(TAG, "Database opened");
    return db;
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // For now, we'll just drop the current tables and create new ones
    db.execSQL("DROP TABLE IF EXISTS " + BmiEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + EerEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + BloodPressureEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + BloodSugarEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + HeartRateEntry.TABLE_NAME);
    db.execSQL("DROP TABLE IF EXISTS " + WeightEntry.TABLE_NAME);
    onCreate(db);
  }

  @Override
  public synchronized void close() {
    super.close();
    Log.d(TAG, "Database closed");
  }
}
