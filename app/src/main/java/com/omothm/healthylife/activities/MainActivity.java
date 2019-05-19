package com.omothm.healthylife.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.omothm.healthylife.R;
import com.omothm.healthylife.comps.Test;
import com.omothm.healthylife.db.BloodPressureSource;
import com.omothm.healthylife.db.BloodSugarSource;
import com.omothm.healthylife.db.BmiSource;
import com.omothm.healthylife.db.DbHelper;
import com.omothm.healthylife.db.EerSource;
import com.omothm.healthylife.db.HeartRateSource;
import com.omothm.healthylife.db.WeightSource;
import com.omothm.healthylife.models.Model;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private final List<Test> tests = new ArrayList<>();
  private final MainAdapter adapter = new MainAdapter(tests);
  private BmiSource bmiSource;
  private BloodPressureSource bpSource;
  private BloodSugarSource bsSource;
  private SQLiteDatabase database;
  private DbHelper dbHelper;
  private EerSource eerSource;
  private HeartRateSource hrSource;
  private WeightSource weightSource;

  private <T extends Model> void addTest(final String name, final List<T> latest) {
    final Test test = new Test(name);
    if (latest != null && latest.size() > 0) {
      final T t = latest.get(0);
      test.setResult(t.getStringValue());
      test.setDate(t.getDate().toString());
    } else {
      test.setResult(getString(R.string.NOT_TESTED));
      test.setDate(getString(R.string.NO_DATE));
    }
    tests.add(test);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    setupRecyclerView();

    dbHelper = new DbHelper(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Close database
    dbHelper.close();
    Log.d(TAG, "Database closed");
  }

  @Override
  protected void onResume() {
    super.onResume();

    // Open database
    database = dbHelper.getWritableDatabase();
    Log.d(TAG, "Database opened");

    bmiSource = new BmiSource(database);
    eerSource = new EerSource(database);
    bpSource = new BloodPressureSource(database);
    bsSource = new BloodSugarSource(database);
    hrSource = new HeartRateSource(database);
    weightSource = new WeightSource(database);

    addTest(getString(R.string.TEST_NAME_BMI), bmiSource.getLatest());
    addTest(getString(R.string.TEST_NAME_EER), eerSource.getLatest());
    addTest(getString(R.string.TEST_NAME_BLOOD_PRESSURE), bpSource.getLatest());
    addTest(getString(R.string.TEST_NAME_BLOOD_SUGAR), bsSource.getLatest());
    addTest(getString(R.string.TEST_NAME_HEART_RATE), hrSource.getLatest());
    addTest(getString(R.string.TEST_NAME_WEIGHT), weightSource.getLatest());
  }

  private void setupRecyclerView() {
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);

    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(adapter);
  }
}
