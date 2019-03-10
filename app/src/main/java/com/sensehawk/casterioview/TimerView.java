package com.sensehawk.casterioview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class TimerView extends View {

  private static final long MAX_TIME = 99999;

  private long startTime;
  private Paint backgroundPaint;
  private TextPaint numberPaint;
  private Runnable runnable = new Runnable() {
    @Override
    public void run() {
      updateTimer();
    }
  };

  public TimerView(Context context) {
    super(context);
    init();
  }

  public TimerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();
    int maxTextWidth = (int) Math.ceil(numberPaint.measureText(String.valueOf(MAX_TIME)));
    int maxTextHeight = (int) Math.ceil(-fontMetrics.top + fontMetrics.bottom);

    int contentWidth = maxTextWidth + getPaddingLeft() + getPaddingRight();
    int contentHeight = maxTextHeight + getPaddingTop() + getPaddingBottom();

    int contextSize = Math.max(contentWidth, contentHeight);
    int resolvedWidth = resolveSize(contextSize, widthMeasureSpec);
    int resolvedHeight = resolveSize(contextSize, heightMeasureSpec);
    setMeasuredDimension(resolvedWidth, resolvedHeight);
  }

  private void init() {
    backgroundPaint = new Paint();
    backgroundPaint.setColor(Color.parseColor("#880E4F"));

    numberPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    numberPaint.setColor(ContextCompat.getColor(getContext(), android.R.color.white));
    numberPaint.setTextSize(64f * getResources().getDisplayMetrics().scaledDensity);
  }

  public void start() {
    startTime = System.currentTimeMillis();
    updateTimer();
  }

  public void stop() {
    startTime = 0;
    removeCallbacks(runnable);
  }

  private void updateTimer() {
    invalidate();
    postDelayed(runnable, 200);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    int canvasWidth = canvas.getWidth();
    int canvasHeight = canvas.getHeight();
    float centerX = Math.round(canvasWidth * 0.5f);
    float centerY = Math.round(canvasHeight * 0.5f);

    float radius = (canvasWidth < canvasHeight ? canvasWidth : canvasHeight) * 0.5f;
    long seconds = (long) ((System.currentTimeMillis() - startTime) * 0.001);
    String number = String.valueOf(seconds);
    float textOffsetX = numberPaint.measureText(number) * 0.5f;
    float textOffsetY = numberPaint.getFontMetrics().ascent * -0.4f;
    canvas.drawCircle(centerX, centerY, radius, backgroundPaint);
    canvas.drawText(number, centerX - textOffsetX, centerY + textOffsetY, numberPaint);
  }
}
