package com.omothm.healthylife.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import com.omothm.healthylife.db.Contract.EerEntry;
import com.omothm.healthylife.models.Eer;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class EerSource extends DataSourceWithDate<Eer> {

  public EerSource(SQLiteDatabase database) {
    super(database, EerEntry.TABLE_NAME, EerEntry._ID, EerEntry.COLUMN_DATE);
  }

  @Override
  public ContentValues buildValues(@NonNull Eer eer) {
    final ContentValues values = new ContentValues();
    values.put(EerEntry.COLUMN_DATE, eer.getDate().getMillis());
    values.put(EerEntry.COLUMN_VALUE, eer.getValue());
    return values;
  }

  @NotNull
  @Override
  public List<Eer> get(String selection) {
    final List<Eer> eers = new ArrayList<>();
    final Cursor cursor = database.query(tableName, null, selection, null, null, null, null);
    try {
      while (cursor.moveToNext()) {
        final long dateMillis = cursor.getLong(cursor.getColumnIndex(EerEntry.COLUMN_DATE));
        final SQLiteDate date = new SQLiteDate(dateMillis);
        final float value = cursor.getFloat(cursor.getColumnIndex(EerEntry.COLUMN_VALUE));
        final long id = cursor.getLong(cursor.getColumnIndex(EerEntry._ID));
        final Eer Eer = new Eer(date, value);
        Eer.setId(id);
        eers.add(Eer);
      }
    } finally {
      if (cursor != null && !cursor.isClosed()) {
        cursor.close();
      }
    }
    return eers;
  }
}
