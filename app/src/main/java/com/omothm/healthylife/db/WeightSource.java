package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.omothm.healthylife.db.Contract.WeightEntry;
import com.omothm.healthylife.models.Weight;
import java.util.ArrayList;
import java.util.List;

public class WeightSource extends DataSourceWithDate<Weight> {

  public WeightSource(SQLiteDatabase database) {
    super(database, WeightEntry.TABLE_NAME, WeightEntry._ID, WeightEntry.COLUMN_DATE);
  }

  @Override
  public ContentValues buildValues(@NonNull Weight eer) {
    final ContentValues values = new ContentValues();
    values.put(WeightEntry.COLUMN_DATE, eer.getDate().getMillis());
    values.put(WeightEntry.COLUMN_VALUE, eer.getValue());
    return values;
  }

  @Override
  public List<Weight> get(String selection) {
    final List<Weight> eers = new ArrayList<>();
    final Cursor cursor = database.query(tableName, null, selection, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        final long dateMillis = cursor.getLong(cursor.getColumnIndex(WeightEntry.COLUMN_DATE));
        final SQLiteDate date = new SQLiteDate(dateMillis);
        final float value = cursor.getFloat(cursor.getColumnIndex(WeightEntry.COLUMN_VALUE));
        final long id = cursor.getLong(cursor.getColumnIndex(WeightEntry._ID));
        final Weight Weight = new Weight(date, value);
        Weight.setId(id);
        eers.add(Weight);
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return eers;
  }
}
