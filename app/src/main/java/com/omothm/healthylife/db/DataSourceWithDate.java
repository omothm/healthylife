package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.omothm.healthylife.models.Model;
import java.util.List;

/** Manages inserting, updating, and deleting records from a database table. */
public abstract class DataSourceWithDate<T extends Model> {

  private static final String TAG = DataSourceWithDate.class.getSimpleName();

  protected final Context context;
  protected final SQLiteDatabase database;
  /** Descendents of this class are assumed to have a date column */
  protected final String dateColumn;
  protected final String idColumn;
  protected final String tableName;

  public DataSourceWithDate(final Context context, final SQLiteDatabase database,
      final String tableName,
      final String idColumn, final String dateColumn) {
    this.context = context;
    this.database = database;
    this.tableName = tableName;
    this.dateColumn = dateColumn;
    this.idColumn = idColumn;
  }

  /** Lets the user create a return a ContentValues object for element insertion and updating. */
  public abstract ContentValues buildValues(@NonNull final T t);

  /**
   * @param t if null, deletes all rows
   */
  public void delete(final T t) {
    String whereClause = t == null ? null : idColumn + " = ?";
    String[] whereArgs = t == null ? null : new String[] { String.valueOf(t.getId()) };

    final int rows = database.delete(tableName, whereClause, whereArgs);
    Log.d(TAG, "Number of rows deleted: " + rows);
  }

  public void deleteAll() {
    delete(null);
  }

  /** Lets the user create a selection a ContentValues object for element insertion and updating. */
  @NonNull
  public abstract List<T> get(String selection);

  public List<T> getAll() {
    return get(null);
  }

  /** Gets the latest entry (according to date). */
  public List<T> getLatest() {
    final String sql = dateColumn + " = (SELECT MAX(" + dateColumn + ") FROM " + tableName + ")";
    return get(sql);
  }

  public long insert(@NonNull final T t) {
    final ContentValues values = buildValues(t);
    final long rowId = database.insert(tableName, null, values);
    Log.d(TAG, "Entry inserted with row ID: " + rowId);
    return rowId;
  }

  public int update(@NonNull final T t) {
    final ContentValues values = buildValues(t);

    final String whereClause = idColumn + " = ?";
    final String[] whereArgs = { String.valueOf(t.getId()) };

    final int rows = database.update(tableName, values, whereClause, whereArgs);
    Log.d(TAG, "Number of rows updated: " + rows);
    return rows;
  }
}
