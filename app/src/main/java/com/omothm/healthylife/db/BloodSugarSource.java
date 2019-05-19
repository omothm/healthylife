package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.omothm.healthylife.db.Contract.BloodSugarEntry;
import com.omothm.healthylife.models.BloodSugar;
import java.util.ArrayList;
import java.util.List;

public class BloodSugarSource extends DataSourceWithDate<BloodSugar> {

  public BloodSugarSource(SQLiteDatabase database) {
    super(database, BloodSugarEntry.TABLE_NAME, BloodSugarEntry._ID, BloodSugarEntry.COLUMN_DATE);
  }

  @Override
  public ContentValues buildValues(@NonNull BloodSugar eer) {
    final ContentValues values = new ContentValues();
    values.put(BloodSugarEntry.COLUMN_DATE, eer.getDate().getMillis());
    values.put(BloodSugarEntry.COLUMN_VALUE, eer.getValue());
    return values;
  }

  @Override
  public List<BloodSugar> get(String selection) {
    final List<BloodSugar> eers = new ArrayList<>();
    final Cursor cursor = database.query(tableName, null, selection, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        final long dateMillis = cursor.getLong(cursor.getColumnIndex(BloodSugarEntry.COLUMN_DATE));
        final SQLiteDate date = new SQLiteDate(dateMillis);
        final float value = cursor.getFloat(cursor.getColumnIndex(BloodSugarEntry.COLUMN_VALUE));
        final long id = cursor.getLong(cursor.getColumnIndex(BloodSugarEntry._ID));
        final BloodSugar BloodSugar = new BloodSugar(date, value);
        BloodSugar.setId(id);
        eers.add(BloodSugar);
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return eers;
  }
}
