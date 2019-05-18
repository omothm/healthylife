package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.omothm.healthylife.db.Contract.BmiEntry;
import com.omothm.healthylife.models.Bmi;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

  private static final String TAG = DataSource.class.getSimpleName();

  private final DbHelper dbHelper;
  private SQLiteDatabase database;

  public DataSource(final Context context) {
    dbHelper = new DbHelper(context);
  }

  public void close() {
    dbHelper.close();
    Log.d(TAG, "Database closed");
  }

  /**
   * @param bmi if null, deletes all rows
   */
  public void delete(final Bmi bmi) {
    String whereClause = bmi == null ? null : BmiEntry._ID + " = ?";
    String[] whereArgs = bmi == null ? null : new String[] { String.valueOf(bmi.getId()) };

    final int rows = database.delete(BmiEntry.TABLE_NAME, whereClause, whereArgs);
    Log.d(TAG, "Number of rows deleted: " + rows);
  }

  public void deleteAllBmis() {
    delete(null);
  }

  public List<Bmi> getAllBmis() {
    return getBmis(null);
  }

  public List<Bmi> getBmis(final String selection) {
    final List<Bmi> bmis = new ArrayList<>();
    final Cursor cursor = database
                              .query(BmiEntry.TABLE_NAME, null, selection, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        final long dateMillis = cursor.getLong(cursor.getColumnIndex(BmiEntry.COLUMN_DATE));
        final SQLiteDate date = new SQLiteDate(dateMillis);
        final float value = cursor.getFloat(cursor.getColumnIndex(BmiEntry.COLUMN_VALUE));
        final long id = cursor.getLong(cursor.getColumnIndex(BmiEntry._ID));
        final Bmi bmi = new Bmi(date, value);
        bmi.setId(id);
        bmis.add(bmi);
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return bmis;
  }

  public List<Bmi> getLatestBmi() {
    final String sql = BmiEntry.COLUMN_DATE + " = (SELECT MAX(" + BmiEntry.COLUMN_DATE + ") FROM "
                           + BmiEntry.TABLE_NAME + ")";
    return getBmis(sql);
  }

  public void insert(@NonNull final Bmi bmi) {
    final ContentValues values = new ContentValues();
    values.put(BmiEntry.COLUMN_DATE, bmi.getDate().getMillis());
    values.put(BmiEntry.COLUMN_VALUE, bmi.getValue());
    final long rowId = database.insert(BmiEntry.TABLE_NAME, null, values);
    Log.d(TAG, "BMI entry inserted with row ID: " + rowId);
  }

  public void open() {
    database = dbHelper.getWritableDatabase();
    Log.d(TAG, "Database opened");
  }

  public void update(@NonNull final Bmi bmi) {
    final ContentValues values = new ContentValues();
    values.put(BmiEntry.COLUMN_DATE, bmi.getDate().getMillis());
    values.put(BmiEntry.COLUMN_VALUE, bmi.getValue());

    final String whereClause = BmiEntry._ID + " = ?";
    final String[] whereArgs = { String.valueOf(bmi.getId()) };

    final int rows = database.update(BmiEntry.TABLE_NAME, values, whereClause, whereArgs);
    Log.d(TAG, "Number of rows updated: " + rows);
  }
}
