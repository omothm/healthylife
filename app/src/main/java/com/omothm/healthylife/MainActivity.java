package com.omothm.healthylife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.omothm.healthylife.db.DataSource;

public class MainActivity extends AppCompatActivity {

  private DataSource dataSource;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    dataSource = new DataSource(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    dataSource.open();
  }

  @Override
  protected void onPause() {
    super.onPause();
    dataSource.close();
  }
}
