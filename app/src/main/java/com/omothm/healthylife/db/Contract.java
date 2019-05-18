package com.omothm.healthylife.db;

import android.provider.BaseColumns;

final class Contract {

  // Disallow instance creation
  private Contract() {
  }

  // BaseColumns contains the constants _ID and _COUNT which are used throughout the Android system
  // when working with cursors.
  public static class BmiEntry implements BaseColumns {

    public static final String TABLE_NAME = "bmi";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";

    public static final String CREATE_TABLE = ContractHelper.createTable(TABLE_NAME,
        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        COLUMN_DATE + " INTEGER NOT NULL", COLUMN_VALUE + " REAL NOT NULL",
        "UNIQUE ( " + _ID + " ) ON CONFLICT REPLACE");
  }
}
