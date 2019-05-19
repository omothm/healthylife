package com.omothm.healthylife.activities;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
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
import com.omothm.healthylife.db.SQLiteDate;
import com.omothm.healthylife.db.WeightSource;
import com.omothm.healthylife.models.BloodPressure;
import com.omothm.healthylife.models.BloodSugar;
import com.omothm.healthylife.models.Bmi;
import com.omothm.healthylife.models.Eer;
import com.omothm.healthylife.models.HeartRate;
import com.omothm.healthylife.models.Model;
import com.omothm.healthylife.models.Weight;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private final List<Test> tests = new ArrayList<>();
  private MainAdapter adapter;
  private BmiSource bmiSource;
  private BloodPressureSource bpSource;
  private BloodSugarSource bsSource;
  private DbHelper dbHelper;
  private EerSource eerSource;
  private HeartRateSource hrSource;
  private WeightSource weightSource;

  private void addMockups() {
    final int nBmi = 5, nEer = 6, nBp = 2, nBs = 0, nHr = 11, nW = 16;
    // Add only if not already added
    final List<Bmi> bmis = bmiSource.getAll();
    if (bmis.size() >= nBmi) {
      // This means either mockups were inserted or there is actual values, so don't add anything
      return;
    }
    Log.d(TAG, "Inserting mockup records");
    final SQLiteDate today = new SQLiteDate();
    for (int i = 0; i < nBmi; i++) {
      bmiSource.insert(new Bmi(this, new SQLiteDate(today.getMillis() - 86400000L * (nBmi - i)),
          15 + (float) (Math.random() * 20f)));
    }
    for (int i = 0; i < nEer; i++) {
      eerSource.insert(new Eer(this, new SQLiteDate(today.getMillis() - 86400000L * (nEer - i)),
          1500f + (float) (Math.random() * 1500f)));
    }
    for (int i = 0; i < nBp; i++) {
      bpSource
          .insert(new BloodPressure(this, new SQLiteDate(today.getMillis() - 86400000L * (nBp - i)),
              70 + (int) (Math.random() * 120), 40 + (int) (Math.random() * 60)));
    }
    for (int i = 0; i < nBs; i++) {
      bsSource
          .insert(new BloodSugar(this, new SQLiteDate(today.getMillis() - 86400000L * (nBs - i)),
              50 + (int) (Math.random() * 90)));
    }
    for (int i = 0; i < nHr; i++) {
      hrSource.insert(new HeartRate(this, new SQLiteDate(today.getMillis() - 86400000L * (nHr - i)),
          40 + (int) (Math.random() * 110)));
    }
    for (int i = 0; i < nW; i++) {
      weightSource.insert(new Weight(this, new SQLiteDate(today.getMillis() - 86400000L * (nW - i)),
          55f + (float) (Math.random() * 10f)));
    }
  }

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

    final ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setDisplayShowHomeEnabled(true);
      ab.setIcon(R.drawable.ic_launcher_foreground);
    }

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
    SQLiteDatabase database = dbHelper.open();

    bmiSource = new BmiSource(this, database);
    eerSource = new EerSource(this, database);
    bpSource = new BloodPressureSource(this, database);
    bsSource = new BloodSugarSource(this, database);
    hrSource = new HeartRateSource(this, database);
    weightSource = new WeightSource(this, database);

    addMockups();

    tests.clear();
    addTest(getString(R.string.test_name_bmi), new BmiActivity(), bmiSource.getLatest());
    addTest(getString(R.string.test_name_eer), new EerActivity(), eerSource.getLatest());
    addTest(getString(R.string.test_name_blood_pressure), new BloodPressureActivity(),
        bpSource.getLatest());
    addTest(getString(R.string.test_name_blood_sugar), new BloodSugarActivity(),
        bsSource.getLatest());
    addTest(getString(R.string.test_name_heart_rate), new HeartRateActivity(),
        hrSource.getLatest());
    addTest(getString(R.string.test_name_weight), new WeightActivity(), weightSource.getLatest());

    setupRecyclerView();
  }

  private void setupRecyclerView() {
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);

    final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(adapter);
  }
}
