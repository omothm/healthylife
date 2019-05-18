package com.omothm.healthylife.db;

import android.support.annotation.NonNull;

class ContractHelper {

  static String createTable(@NonNull final String tableName, @NonNull final String... comps) {
    if (comps.length == 0) {
      throw new IllegalArgumentException("There must be at least one component");
    }
    final StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE ").append(tableName).append(" ( ");
    for (int i = 0; i < comps.length; i++) {
      builder.append(comps[i]);
      if (i < comps.length - 1) {
        builder.append(", ");
      }
    }
    builder.append(" )");
    return builder.toString();
  }
}
