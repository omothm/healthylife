package com.omothm.healthylife.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SQLiteDateTest {

  @Rule
  public ExpectedException exception = ExpectedException.none();

  SQLiteDate date1 = new SQLiteDate();
  SQLiteDate date2 = new SQLiteDate(2020, 10, 10);

  @Test
  public void dayMonthYear() {
    date1.setDay(date2.getDay());
    date1.setMonth(date2.getMonth());
    date1.setYear(date2.getYear());
    assertEquals(date2, date1);
  }

  @Test
  public void erroneousValues() {
    date1.setDay(31);
    date1.setMonth(3);
    date1.setYear(2010);
    assertEquals(31, date1.getDay());
    assertEquals(3, date1.getMonth());
    assertEquals(2010, date1.getYear());

    exception.expect(IllegalArgumentException.class);
    date1.setDay(31);
    date1.setMonth(2);
    date1.setYear(2010);
    assertNotEquals(31, date1.getDay());
    assertEquals(2, date1.getMonth());
    assertEquals(2010, date1.getYear());
  }

  @Test
  public void millis() {
    SQLiteDate date = new SQLiteDate();
    date.setMillis(date2.getMillis());
    assertEquals(date2.getMillis(), date.getMillis());
    assertEquals(date2.getDay(), date.getDay());
    assertEquals(date2.getMonth(), date.getMonth());
    assertEquals(date2.getYear(), date.getYear());
    date.setMillis(-10000000000L);
    assertEquals(-10000000000L, date.getMillis());
    System.out.println(date.getDay());
    System.out.println(date.getMonth());
    System.out.println(date.getYear());
  }
}