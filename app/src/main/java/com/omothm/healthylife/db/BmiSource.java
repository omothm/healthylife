package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.omothm.healthylife.db.Contract.BmiEntry;
import com.omothm.healthylife.models.Bmi;
import java.util.ArrayList;
import java.util.List;

public class BmiSource extends DataSourceWithDate<Bmi> {

  public BmiSource(SQLiteDatabase database) {
    super(database, BmiEntry.TABLE_NAME, BmiEntry._ID, BmiEntry.COLUMN_DATE);
  }

  @Override
  public ContentValues buildValues(@NonNull Bmi bmi) {
    final ContentValues values = new ContentValues();
    values.put(BmiEntry.COLUMN_DATE, bmi.getDate().getMillis());
    values.put(BmiEntry.COLUMN_VALUE, bmi.getValue());
    return values;
  }

  @Override
  public List<Bmi> get(String selection) {
    final List<Bmi> bmis = new ArrayList<>();
    final Cursor cursor = database.query(tableName, null, selection, null, null, null, null);
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
}
