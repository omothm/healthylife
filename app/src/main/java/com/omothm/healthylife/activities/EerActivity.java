package com.omothm.healthylife.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.DbHelper;
import com.omothm.healthylife.db.EerSource;
import com.omothm.healthylife.db.SQLiteDate;
import com.omothm.healthylife.models.Eer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EerActivity extends AppCompatActivity {

  private Button buttonAddEntry;
  private LineChart chart;
  private DbHelper dbHelper;
  private float eer;
  private EerSource eerSource;
  private Spinner spinnerActivity;
  private Spinner spinnerGender;
  private TextView textAge;
  private TextView textAnalysis;
  private TextView textEer;
  private TextView textHeight;
  private TextView textWeight;

  private void handleChange() {
    try {
      // male is true
      final boolean gender = spinnerGender.getSelectedItem().toString().equals("Male");
      final int age = Integer.parseInt(textAge.getText().toString());
      if (age < 19) {
        throw new NumberFormatException();
      }
      final float weight = Float.parseFloat(textWeight.getText().toString());
      final float height = Float.parseFloat(textHeight.getText().toString());
      final String activityString = spinnerActivity.getSelectedItem().toString();
      final float activity;
      if (activityString.equalsIgnoreCase("sedentary")) {
        activity = 1f;
      } else if (activityString.equalsIgnoreCase("low active")) {
        activity = gender ? 1.11f : 1.12f;
      } else if (activityString.equalsIgnoreCase("active")) {
        activity = gender ? 1.25f : 1.27f;
      } else {
        activity = gender ? 1.48f : 1.45f;
      }
      eer = Eer.calculate(gender, age, weight, height, activity);
      textEer.setText(String.format(Locale.getDefault(), "%.1f", eer));
      final String analysis = Eer.getAnalysis(EerActivity.this, eer);
      textAnalysis.setText(analysis);
      buttonAddEntry.setEnabled(true);
    } catch (NumberFormatException ignore) {
      textEer.setText(null);
      textAnalysis.setText(null);
      buttonAddEntry.setEnabled(false);
    }
  }

  private void loadData() {
    final List<Eer> eers = eerSource.getAll();
    LineData data = setupData(eers);
    ChartHelper.setupChart(chart, data, new ValueFormatter() {
      @Override
      public String getFormattedValue(float value) {
        final int index = (int) value;
        if (index < eers.size()) {
          return eers.get(index).getDate().toString();
        } else {
          return null;
        }
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_eer);

    // Setup back button
    final ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setDisplayHomeAsUpEnabled(true);
    }

    dbHelper = new DbHelper(this);

    chart = findViewById(R.id.chart);

    textWeight = findViewById(R.id.weight);
    textHeight = findViewById(R.id.height);
    textEer = findViewById(R.id.eer);
    textAnalysis = findViewById(R.id.analysis);
    textAge = findViewById(R.id.age);
    spinnerGender = findViewById(R.id.gender);
    spinnerActivity = findViewById(R.id.activity);
    buttonAddEntry = findViewById(R.id.add_entry);

    buttonAddEntry.setEnabled(false);

    // Register listeners
    spinnerGender.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        handleChange();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) { }
    });

    spinnerActivity.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        handleChange();
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) { }
    });

    textAge.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        handleChange();
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) { }
    });

    textWeight.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        handleChange();
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) { }
    });

    textHeight.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        handleChange();
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) { }
    });

    buttonAddEntry.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        final SQLiteDate today = new SQLiteDate();
        final Eer newEer = new Eer(EerActivity.this, today, eer);
        final boolean success;

        // If a record for today exists, update. Otherwise, insert.
        final List<Eer> eers = eerSource.getLatest();
        if (eers.size() > 0 && eers.get(0).getDate().equals(today)) {
          newEer.setId(eers.get(0).getId());
          final int update = eerSource.update(newEer);
          success = update > 0;
        } else {
          final long insert = eerSource.insert(newEer);
          success = insert != -1;
        }
        if (success) {
          Toast.makeText(EerActivity.this, R.string.entry_added, Toast.LENGTH_SHORT).show();
          loadData();
        } else {
          Toast.makeText(EerActivity.this, R.string.entry_failed, Toast.LENGTH_SHORT).show();
        }
        buttonAddEntry.setEnabled(false);
      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // End if back button is pressed
    if (item.getItemId() == android.R.id.home) {
      finish();
    }
    return super.onOptionsItemSelected(item);
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

    eerSource = new EerSource(this, database);
    loadData();
  }

  private LineData setupData(final List<Eer> eers) {

    ArrayList<Entry> values = new ArrayList<>();

    for (int i = 0; i < eers.size(); i++) {
      float val = eers.get(i).getValue();
      values.add(new Entry(i, val));
    }

    final int color = ContextCompat.getColor(this, R.color.colorPrimary);

    // create a dataset and give it a type
    LineDataSet set1 = new LineDataSet(values, "DataSet 1");
    set1.setLineWidth(1.75f);
    set1.setCircleRadius(5f);
    set1.setCircleHoleRadius(2.5f);
    set1.setColor(color);
    set1.setCircleColor(color);
    set1.setHighLightColor(color);
    set1.setDrawValues(false);

    // create a data object with the data sets
    return new LineData(set1);
  }
}
