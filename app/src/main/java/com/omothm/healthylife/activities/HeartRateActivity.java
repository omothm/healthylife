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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.omothm.healthylife.R;
import com.omothm.healthylife.db.HeartRateSource;
import com.omothm.healthylife.db.DbHelper;
import com.omothm.healthylife.db.SQLiteDate;
import com.omothm.healthylife.models.HeartRate;
import java.util.ArrayList;
import java.util.List;

public class HeartRateActivity extends AppCompatActivity {

  private HeartRateSource bsSource;
  private Button buttonAddEntry;
  private LineChart chart;
  private DbHelper dbHelper;
  private int hr;
  private TextView textAnalysis;

  private void loadData() {
    final List<HeartRate> hss = bsSource.getAll();
    LineData data = setupData(hss);
    ChartHelper.setupChart(chart, data, new ValueFormatter() {
      @Override
      public String getFormattedValue(float value) {
        final int index = (int) value;
        if (index < hss.size()) {
          return hss.get(index).getDate().toString();
        } else {
          return null;
        }
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hr);

    // Setup back button
    final ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setDisplayHomeAsUpEnabled(true);
    }

    dbHelper = new DbHelper(this);

    chart = findViewById(R.id.chart);

    final TextView textHr = findViewById(R.id.hr);
    textAnalysis = findViewById(R.id.analysis);
    buttonAddEntry = findViewById(R.id.add_entry);

    buttonAddEntry.setEnabled(false);

    textHr.addTextChangedListener(new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        buttonAddEntry.setEnabled(false);
        try {
          hr = Integer.parseInt(s.toString());
          if (hr < 20 || hr > 160) {
            throw new IllegalArgumentException();
          }
          final String analysis = HeartRate.getAnalysis(HeartRateActivity.this, hr);
          textAnalysis.setText(analysis);
          buttonAddEntry.setEnabled(true);
        } catch (NumberFormatException ignore) {
          textAnalysis.setText(null);
        } catch (IllegalArgumentException e) {
          textAnalysis.setText(R.string.invalid);
        }
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
        final HeartRate newBs = new HeartRate(HeartRateActivity.this, today, hr);
        final boolean success;

        // If a record for today exists, update. Otherwise, insert.
        final List<HeartRate> hss = bsSource.getLatest();
        if (hss.size() > 0 && hss.get(0).getDate().equals(today)) {
          newBs.setId(hss.get(0).getId());
          final int update = bsSource.update(newBs);
          success = update > 0;
        } else {
          final long insert = bsSource.insert(newBs);
          success = insert != -1;
        }
        if (success) {
          Toast.makeText(HeartRateActivity.this, R.string.entry_added, Toast.LENGTH_SHORT).show();
          loadData();
        } else {
          Toast.makeText(HeartRateActivity.this, R.string.entry_failed, Toast.LENGTH_SHORT).show();
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

    bsSource = new HeartRateSource(this, database);
    loadData();
  }

  private LineData setupData(final List<HeartRate> hss) {

    ArrayList<Entry> values = new ArrayList<>();

    for (int i = 0; i < hss.size(); i++) {
      float val = hss.get(i).getValue();
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
