package com.omothm.healthylife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.omothm.healthylife.db.DataSource;
import com.omothm.healthylife.db.SQLiteDate;
import com.omothm.healthylife.models.Bmi;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private DataSource dataSource;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    dataSource = new DataSource(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    dataSource.close();
  }

  @Override
  protected void onResume() {
    super.onResume();
    dataSource.open();
    // Add sample entries to database for testing
    /*dataSource.insert(new Bmi(new SQLiteDate(2019, 5, 18), 18f));
    dataSource.insert(new Bmi(new SQLiteDate(2019, 5, 19), 25f));
    dataSource.insert(new Bmi(new SQLiteDate(2019, 5, 22), 20f));*/
    List<Bmi> bmis = dataSource.getAllBmis();
    for (final Bmi bmi : bmis) {
      Log.d(TAG, bmi.toString());
    }
    bmis.get(0).setDate(new SQLiteDate(2020, 1, 1));
    dataSource.update(bmis.get(0));
    bmis = dataSource.getAllBmis();
    for (final Bmi bmi : bmis) {
      Log.d(TAG, bmi.toString());
    }
  }
}
