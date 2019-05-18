package com.omothm.healthylife.layouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.omothm.healthylife.R;

public class LinearLayoutTopBorder extends LinearLayout {

  final Paint paint = new Paint();

  {
    setWillNotDraw(false);
    paint.setStyle(Style.STROKE);
  }

  public LinearLayoutTopBorder(Context context) {
    super(context);
  }

  public LinearLayoutTopBorder(Context context, AttributeSet attrs) {
    super(context, attrs);
    setAttrs(context, attrs);
  }

  public LinearLayoutTopBorder(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    setAttrs(context, attrs);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.drawLine(0, 1, getWidth(), 1, paint);
  }

  private void setAttrs(final Context context, final AttributeSet attrs) {
    final TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.LinearLayoutTopBorder);
    final int color = arr.getColor(R.styleable.LinearLayoutTopBorder_border_color,
        Color.argb(255, 192, 192, 192));
    final float width = arr.getDimension(R.styleable.LinearLayoutTopBorder_border_width, 1f);
    paint.setColor(color);
    paint.setStrokeWidth(width);
    arr.recycle();
  }
}
