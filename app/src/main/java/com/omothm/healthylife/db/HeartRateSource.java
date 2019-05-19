package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.omothm.healthylife.db.Contract.HeartRateEntry;
import com.omothm.healthylife.models.HeartRate;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class HeartRateSource extends DataSourceWithDate<HeartRate> {

  public HeartRateSource(SQLiteDatabase database) {
    super(database, HeartRateEntry.TABLE_NAME, HeartRateEntry._ID, HeartRateEntry.COLUMN_DATE);
  }

  @Override
  public ContentValues buildValues(@NonNull HeartRate eer) {
    final ContentValues values = new ContentValues();
    values.put(HeartRateEntry.COLUMN_DATE, eer.getDate().getMillis());
    values.put(HeartRateEntry.COLUMN_VALUE, eer.getValue());
    return values;
  }

  @NotNull
  @Override
  public List<HeartRate> get(String selection) {
    final List<HeartRate> eers = new ArrayList<>();
    final Cursor cursor = database.query(tableName, null, selection, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        final long dateMillis = cursor.getLong(cursor.getColumnIndex(HeartRateEntry.COLUMN_DATE));
        final SQLiteDate date = new SQLiteDate(dateMillis);
        final int value = cursor.getInt(cursor.getColumnIndex(HeartRateEntry.COLUMN_VALUE));
        final long id = cursor.getLong(cursor.getColumnIndex(HeartRateEntry._ID));
        final HeartRate HeartRate = new HeartRate(date, value);
        HeartRate.setId(id);
        eers.add(HeartRate);
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return eers;
  }
}
