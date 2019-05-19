package com.omothm.healthylife.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.omothm.healthylife.R;
import com.omothm.healthylife.comps.Test;
import java.util.Collections;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

  private final Context context;
  private List<Test> tests = Collections.emptyList();

  public MainAdapter(final Context context, final List<Test> tests) {
    setTests(tests);
    this.context = context;
  }

  @Override
  public int getItemCount() {
    return tests.size();
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    final Test test = tests.get(i);
    viewHolder.name.setText(test.getName());
    viewHolder.result.setText(test.getResult());
    viewHolder.date.setText(test.getDate());
    viewHolder.view.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(context, test.getActivity().getClass());
        context.startActivity(intent);
      }
    });
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    final View v = LayoutInflater.from(viewGroup.getContext())
                       .inflate(R.layout.item, viewGroup, false);
    return new ViewHolder(v);
  }

  public void setTests(final List<Test> tests) {
    this.tests = tests;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    final TextView name, result, date;
    final View view;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      view = itemView;
      name = itemView.findViewById(R.id.name);
      result = itemView.findViewById(R.id.result);
      date = itemView.findViewById(R.id.date);
    }
  }
}
