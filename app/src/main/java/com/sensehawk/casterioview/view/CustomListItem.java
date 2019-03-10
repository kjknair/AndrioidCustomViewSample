package com.sensehawk.casterioview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sensehawk.casterioview.R;

public class CustomListItem extends ViewGroup {

  private ImageView iconView;
  private TextView titleView;
  private TextView subTitleView;

  public CustomListItem(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    iconView = findViewById(R.id.icon);
    titleView = findViewById(R.id.title);
    subTitleView = findViewById(R.id.subtitle);
  }

  @Override protected boolean checkLayoutParams(LayoutParams p) {
    return p instanceof MarginLayoutParams;
  }

  @Override protected LayoutParams generateDefaultLayoutParams() {
    return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
  }

  @Override protected LayoutParams generateLayoutParams(LayoutParams p) {
    return new MarginLayoutParams(p);
  }

  @Override public LayoutParams generateLayoutParams(AttributeSet attrs) {
    return new MarginLayoutParams(getContext(), attrs);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    measureChildWithMargins(iconView, widthMeasureSpec, 0, heightMeasureSpec, 0);
    MarginLayoutParams p = (MarginLayoutParams) iconView.getLayoutParams();
    int iconWidthUsed = p.getMarginStart() + p.getMarginEnd() + iconView.getMeasuredWidth();
    int iconHeightUsed = p.topMargin + p.bottomMargin + iconView.getMeasuredHeight();

    measureChildWithMargins(titleView, widthMeasureSpec, iconWidthUsed, heightMeasureSpec, 0);
    p = (MarginLayoutParams) titleView.getLayoutParams();
    int titleWidthUsed = titleView.getMeasuredWidth() + p.getMarginStart() + p.getMarginEnd();
    int titleHeightUsed = titleView.getMeasuredHeight() + p.topMargin + p.leftMargin;

    measureChildWithMargins(subTitleView, widthMeasureSpec, iconWidthUsed, heightMeasureSpec,
        titleHeightUsed);
    p = (MarginLayoutParams) subTitleView.getLayoutParams();
    int subtitleWidthUsed = subTitleView.getMeasuredWidth() + p.getMarginStart() + p.getMarginEnd();
    int subtitleHeightUsed = subTitleView.getMeasuredHeight() + p.topMargin + p.leftMargin;

    int width = iconWidthUsed
        + Math.max(titleWidthUsed, subtitleWidthUsed)
        + getPaddingLeft()
        + getPaddingRight();
    int height = Math.max(iconHeightUsed, titleHeightUsed + subtitleHeightUsed)
        + getPaddingTop()
        + getPaddingBottom();

    setMeasuredDimension(resolveSize(width,widthMeasureSpec ), resolveSize(height, heightMeasureSpec));
  }

  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    final MarginLayoutParams iconLayoutParams = (MarginLayoutParams) iconView.getLayoutParams();
    int left = getPaddingLeft() + iconLayoutParams.getMarginStart();
    int top = getPaddingTop() + iconLayoutParams.topMargin;
    int right = left + iconView.getMeasuredWidth();
    int bottom = top + iconView.getMeasuredHeight();
    iconView.layout(left, top, right, bottom);

    int iconRightPlusMargin = right + iconLayoutParams.leftMargin;

    final MarginLayoutParams titleLayoutParams = (MarginLayoutParams) titleView.getLayoutParams();
    left = iconRightPlusMargin + titleLayoutParams.getMarginStart();
    top = getPaddingTop() + titleLayoutParams.topMargin;
    right = left + titleView.getMeasuredWidth();
    bottom = top + titleView.getMeasuredHeight();
    titleView.layout(left, top, right, bottom);

    int titleBottomPlusMargin = bottom + titleLayoutParams.bottomMargin;

    final MarginLayoutParams subtitleLayoutParams =
        (MarginLayoutParams) subTitleView.getLayoutParams();
    left = iconRightPlusMargin + subtitleLayoutParams.getMarginStart();
    top = titleBottomPlusMargin + subtitleLayoutParams.topMargin;
    right = left + subTitleView.getMeasuredWidth();
    bottom = top + subTitleView.getMeasuredHeight();
    subTitleView.layout(left, top, right, bottom);
  }
}
