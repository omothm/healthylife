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
import com.omothm.healthylife.db.BmiSource;
import com.omothm.healthylife.db.DbHelper;
import com.omothm.healthylife.db.SQLiteDate;
import com.omothm.healthylife.models.Bmi;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BmiActivity extends AppCompatActivity {

  private float bmi;
  private BmiSource bmiSource;
  private Button buttonAddEntry;
  private LineChart chart;
  private DbHelper dbHelper;
  private TextView textAnalysis;
  private TextView textBmi;
  private TextView textHeight;
  private TextView textWeight;

  private void loadData() {
    final List<Bmi> bmis = bmiSource.getAll();
    LineData data = setupData(bmis);
    ChartHelper.setupChart(chart, data, new ValueFormatter() {
      @Override
      public String getFormattedValue(float value) {
        final int index = (int) value;
        if (index < bmis.size()) {
          return bmis.get(index).getDate().toString();
        } else {
          return null;
        }
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bmi);

    // Setup back button
    final ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setDisplayHomeAsUpEnabled(true);
    }

    dbHelper = new DbHelper(this);

    chart = findViewById(R.id.chart);

    textWeight = findViewById(R.id.weight);
    textHeight = findViewById(R.id.height);
    textBmi = findViewById(R.id.bmi);
    textAnalysis = findViewById(R.id.analysis);
    buttonAddEntry = findViewById(R.id.add_entry);

    buttonAddEntry.setEnabled(false);

    final TextWatcher watcher = new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        try {
          final float weight = Float.parseFloat(textWeight.getText().toString());
          final float height = Float.parseFloat(textHeight.getText().toString());
          bmi = Bmi.calculate(weight, height);
          textBmi.setText(String.format(Locale.getDefault(), "%.1f", bmi));
          final String analysis = Bmi.getAnalysis(BmiActivity.this, bmi);
          textAnalysis.setText(analysis);
          buttonAddEntry.setEnabled(true);
        } catch (NumberFormatException ignore) {
          textBmi.setText(null);
          textAnalysis.setText(null);
          buttonAddEntry.setEnabled(false);
        }
      }

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };

    textWeight.addTextChangedListener(watcher);
    textHeight.addTextChangedListener(watcher);

    buttonAddEntry.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        final SQLiteDate today = new SQLiteDate();
        final Bmi newBmi = new Bmi(today, bmi);
        final boolean success;

        // If a record for today exists, update. Otherwise, insert.
        final List<Bmi> bmis = bmiSource.getLatest();
        if (bmis.size() > 0 && bmis.get(0).getDate().equals(today)) {
          newBmi.setId(bmis.get(0).getId());
          final int update = bmiSource.update(newBmi);
          success = update > 0;
        } else {
          final long insert = bmiSource.insert(newBmi);
          success = insert != -1;
        }
        if (success) {
          Toast.makeText(BmiActivity.this, R.string.entry_added, Toast.LENGTH_SHORT).show();
          loadData();
        } else {
          Toast.makeText(BmiActivity.this, R.string.entry_failed, Toast.LENGTH_SHORT).show();
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

    bmiSource = new BmiSource(database);
    loadData();
  }

  private LineData setupData(final List<Bmi> bmis) {

    ArrayList<Entry> values = new ArrayList<>();

    for (int i = 0; i < bmis.size(); i++) {
      float val = bmis.get(i).getValue();
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
