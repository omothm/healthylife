package com.omothm.healthylife.db;

import static org.junit.Assert.*;

import com.omothm.healthylife.db.Contract.BmiEntry;
import org.junit.Test;

public class ContractHelperTest {

  @Test
  public void test() {
    System.out.println(ContractHelper.createTable(BmiEntry.TABLE_NAME,
        BmiEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT",
        BmiEntry.COLUMN_DATE + " INTEGER NOT NULL", BmiEntry.COLUMN_VALUE + " REAL NOT NULL",
        "UNIQUE ( " + BmiEntry._ID + " ) ON CONFLICT REPLACE"));
  }
}