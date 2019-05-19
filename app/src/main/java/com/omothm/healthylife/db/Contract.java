package com.omothm.healthylife.db;

import android.provider.BaseColumns;

final class Contract {

  // Disallow instance creation
  private Contract() {
  }

  public static class BloodPressureEntry implements BaseColumns {

    public static final String TABLE_NAME = "blood_pressure";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TOP = "top";
    public static final String COLUMN_BOTTOM = "bottom";

    public static final String CREATE_TABLE = ContractHelper.createTable(TABLE_NAME,
        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        COLUMN_DATE + " INTEGER NOT NULL", COLUMN_TOP + " INTEGER NOT NULL",
        COLUMN_BOTTOM + " INTEGER NOT NULL", "UNIQUE ( " + _ID + " ) ON CONFLICT REPLACE");
  }

  public static class BloodSugarEntry implements BaseColumns {

    public static final String TABLE_NAME = "blood_sugar";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";

    public static final String CREATE_TABLE = ContractHelper.createTable(TABLE_NAME,
        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        COLUMN_DATE + " INTEGER NOT NULL", COLUMN_VALUE + " REAL NOT NULL",
        "UNIQUE ( " + _ID + " ) ON CONFLICT REPLACE");
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

  public static class EerEntry implements BaseColumns {

    public static final String TABLE_NAME = "eer";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";

    public static final String CREATE_TABLE = ContractHelper.createTable(TABLE_NAME,
        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        COLUMN_DATE + " INTEGER NOT NULL", COLUMN_VALUE + " REAL NOT NULL",
        "UNIQUE ( " + _ID + " ) ON CONFLICT REPLACE");
  }

  public static class HeartRateEntry implements BaseColumns {

    public static final String TABLE_NAME = "heart_rate";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";

    public static final String CREATE_TABLE = ContractHelper.createTable(TABLE_NAME,
        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        COLUMN_DATE + " INTEGER NOT NULL", COLUMN_VALUE + " INTEGER NOT NULL",
        "UNIQUE ( " + _ID + " ) ON CONFLICT REPLACE");
  }

  public static class WeightEntry implements BaseColumns {

    public static final String TABLE_NAME = "weight";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_VALUE = "value";

    public static final String CREATE_TABLE = ContractHelper.createTable(TABLE_NAME,
        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        COLUMN_DATE + " INTEGER NOT NULL", COLUMN_VALUE + " REAL NOT NULL",
        "UNIQUE ( " + _ID + " ) ON CONFLICT REPLACE");
  }
}
