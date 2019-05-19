package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.omothm.healthylife.db.Contract.BloodPressureEntry;
import com.omothm.healthylife.models.BloodPressure;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class BloodPressureSource extends DataSourceWithDate<BloodPressure> {

  public BloodPressureSource(Context context, SQLiteDatabase database) {
    super(context, database, BloodPressureEntry.TABLE_NAME, BloodPressureEntry._ID,
        BloodPressureEntry.COLUMN_DATE);
  }

  @Override
  public ContentValues buildValues(@NonNull BloodPressure bp) {
    final ContentValues values = new ContentValues();
    values.put(BloodPressureEntry.COLUMN_DATE, bp.getDate().getMillis());
    values.put(BloodPressureEntry.COLUMN_TOP, bp.getTop());
    values.put(BloodPressureEntry.COLUMN_BOTTOM, bp.getBottom());
    return values;
  }

  @NotNull
  @Override
  public List<BloodPressure> get(String selection) {
    final List<BloodPressure> bps = new ArrayList<>();
    final Cursor cursor = database.query(tableName, null, selection, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        final long dateMillis = cursor
                                    .getLong(cursor.getColumnIndex(BloodPressureEntry.COLUMN_DATE));
        final SQLiteDate date = new SQLiteDate(dateMillis);
        final int top = cursor.getInt(cursor.getColumnIndex(BloodPressureEntry.COLUMN_TOP));
        final int bottom = cursor.getInt(cursor.getColumnIndex(BloodPressureEntry.COLUMN_BOTTOM));
        final long id = cursor.getLong(cursor.getColumnIndex(BloodPressureEntry._ID));
        final BloodPressure BloodPressure = new BloodPressure(context, date, top, bottom);
        BloodPressure.setId(id);
        bps.add(BloodPressure);
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return bps;
  }
}
