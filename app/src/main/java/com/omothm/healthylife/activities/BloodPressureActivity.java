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
import com.omothm.healthylife.db.BloodPressureSource;
import com.omothm.healthylife.db.DbHelper;
import com.omothm.healthylife.db.SQLiteDate;
import com.omothm.healthylife.models.BloodPressure;
import java.util.ArrayList;
import java.util.List;

public class BloodPressureActivity extends AppCompatActivity {

  private int bottom; // input bottom pressure value
  private BloodPressureSource bpSource; // data source
  private Button buttonAddEntry; // UI add button
  private LineChart chart; // UI chart
  private DbHelper dbHelper; // database helper
  private TextView textAnalysis; // UI analysis text
  private TextView textBottom; // UI bottom pressure
  private TextView textTop; // UI top pressure
  private int top; // input top pressure value

  // load data into chart
  private void loadData() {
    // retrieve all data
    final List<BloodPressure> bps = bpSource.getAll();
    // convert them into LineData
    LineData data = setupData(bps);
    // Setup chart configuration and set x-axis values to be dates
    ChartHelper.setupChart(chart, data, new ValueFormatter() {
      @Override
      public String getFormattedValue(float value) {
        final int index = (int) value;
        if (index < bps.size()) {
          return bps.get(index).getDate().toString();
        } else {
          return null;
        }
      }
    });
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_bp);

    // Setup back button
    final ActionBar ab = getSupportActionBar();
    if (ab != null) {
      ab.setDisplayHomeAsUpEnabled(true);
    }

    // Initialize database helper
    dbHelper = new DbHelper(this);

    // Get UI components
    chart = findViewById(R.id.chart);
    textTop = findViewById(R.id.top);
    textBottom = findViewById(R.id.bottom);
    textAnalysis = findViewById(R.id.analysis);
    buttonAddEntry = findViewById(R.id.add_entry);

    buttonAddEntry.setEnabled(false);

    // Handler of text change
    final TextWatcher watcher = new TextWatcher() {
      @Override
      public void afterTextChanged(Editable s) {
        buttonAddEntry.setEnabled(false);
        // Try to make sense of input. If successful, set output and analysis.
        try {
          top = Integer.parseInt(textTop.getText().toString());
          bottom = Integer.parseInt(textBottom.getText().toString());
          if (top < 20 || top > 250 || bottom < 10 || bottom > 140) {
            throw new IllegalArgumentException();
          }
          final String analysis = BloodPressure.getAnalysis(BloodPressureActivity.this, top,
              bottom);
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
    };

    // Register text listeners
    textTop.addTextChangedListener(watcher);
    textBottom.addTextChangedListener(watcher);

    // Register button listener
    buttonAddEntry.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        // Add new entry
        final SQLiteDate today = new SQLiteDate();
        final BloodPressure newBp = new BloodPressure(BloodPressureActivity.this, today, top,
            bottom);
        final boolean success;

        // If a record for today exists, update. Otherwise, insert.
        final List<BloodPressure> bps = bpSource.getLatest();
        if (bps.size() > 0 && bps.get(0).getDate().equals(today)) {
          newBp.setId(bps.get(0).getId());
          final int update = bpSource.update(newBp);
          success = update > 0;
        } else {
          final long insert = bpSource.insert(newBp);
          success = insert != -1;
        }
        if (success) {
          Toast.makeText(BloodPressureActivity.this, R.string.entry_added, Toast.LENGTH_SHORT)
              .show();
          loadData();
        } else {
          Toast.makeText(BloodPressureActivity.this, R.string.entry_failed, Toast.LENGTH_SHORT)
              .show();
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

    bpSource = new BloodPressureSource(this, database);
    loadData();
  }

  private LineData setupData(final List<BloodPressure> bps) {

    // Two sets of data for top and bottom values
    ArrayList<Entry> values1 = new ArrayList<>();
    ArrayList<Entry> values2 = new ArrayList<>();

    for (int i = 0; i < bps.size(); i++) {
      values1.add(new Entry(i, bps.get(i).getTop()));
      values2.add(new Entry(i, bps.get(i).getBottom()));
    }

    final int color = ContextCompat.getColor(this, R.color.colorPrimary);

    // create a dataset and give it a type
    LineDataSet set1 = new LineDataSet(values1, "Top number");
    LineDataSet set2 = new LineDataSet(values2, "Bottom number");
    set1.setLineWidth(1.75f);
    set1.setCircleRadius(5f);
    set1.setCircleHoleRadius(2.5f);
    set1.setColor(color);
    set1.setCircleColor(color);
    set1.setHighLightColor(color);
    set1.setDrawValues(false);

    // create a data object with the data sets
    return new LineData(set1, set2);
  }
}
