package com.omothm.healthylife.activities;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
  private MainAdapter adapter;

  private BmiSource bmiSource;
  private BloodPressureSource bpSource;
  private BloodSugarSource bsSource;
  private SQLiteDatabase database;
  private DbHelper dbHelper;
  private EerSource eerSource;
  private HeartRateSource hrSource;
  private WeightSource weightSource;

  private <T extends Model> void addTest(final String name, final Activity activity,
      final List<T> latest) {
    final Test test = new Test(name, activity);
    if (latest != null && latest.size() > 0) {
      final T t = latest.get(0);
      test.setResult(t.getStringValue());
      test.setDate(t.getDate().toString());
    } else {
      test.setResult(getString(R.string.not_tested));
      test.setDate(getString(R.string.no_date));
    }
    tests.add(test);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    adapter = new MainAdapter(this, tests);
    setupRecyclerView();

    dbHelper = new DbHelper(this);
  }

  @Override
  protected void onPause() {
    super.onPause();
    // Close database
    dbHelper.close();
  }

  @Override
  protected void onResume() {
    super.onResume();

    // Open database
    database = dbHelper.open();

    bmiSource = new BmiSource(database);
    eerSource = new EerSource(database);
    bpSource = new BloodPressureSource(database);
    bsSource = new BloodSugarSource(database);
    hrSource = new HeartRateSource(database);
    weightSource = new WeightSource(database);

    final Activity bmiActivity = new BmiActivity();
    addTest(getString(R.string.test_name_bmi), bmiActivity, bmiSource.getLatest());
    addTest(getString(R.string.test_name_eer), bmiActivity, eerSource.getLatest());
    addTest(getString(R.string.test_name_blood_pressure), bmiActivity, bpSource.getLatest());
    addTest(getString(R.string.test_name_blood_sugar), bmiActivity, bsSource.getLatest());
    addTest(getString(R.string.test_name_heart_rate), bmiActivity, hrSource.getLatest());
    addTest(getString(R.string.test_name_weight), bmiActivity, weightSource.getLatest());
  }

  private void setupRecyclerView() {
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);

    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(adapter);
  }
}
