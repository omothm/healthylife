package com.omothm.healthylife.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.omothm.healthylife.R;
import com.omothm.healthylife.comps.Test;
import com.omothm.healthylife.db.DataSource;
import com.omothm.healthylife.models.Bmi;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private final List<Test> tests = new ArrayList<>();
  private final MainAdapter adapter = new MainAdapter(tests);

  private DataSource dataSource;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setupRecyclerView();

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

    // BMI test
    Test test = new Test(getString(R.string.TEST_NAME_BMI));
    final List<Bmi> bmis = dataSource.getLatestBmi();
    if (bmis.size() > 0) {
      final Bmi bmi = bmis.get(0);
      test.setResult(String.valueOf(bmi.getValue()));
      test.setDate(bmi.getDate().toString());
    }
    tests.add(test);
  }

  private void setupRecyclerView() {
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);

    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);


    recyclerView.setAdapter(adapter);
  }
}
