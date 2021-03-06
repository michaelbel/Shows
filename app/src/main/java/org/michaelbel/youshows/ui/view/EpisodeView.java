package org.michaelbel.youshows.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.michaelbel.youshows.AndroidExtensions;
import org.michaelbel.material.widget.LayoutHelper;
import org.michaelbel.youshows.Theme;
import org.michaelbel.material.extensions.Extensions;
import org.michaelbel.shows.R;

/**
 * Date: 06 APR 2018
 * Time: 22:19 MSK
 *
 * @author Michael Bel
 */

@SuppressLint("ClickableViewAccessibility")
public class EpisodeView extends FrameLayout {

    private TextView nameText;
    private TextView valueText;
    private TextView airDateText;
    private CheckBox checkBox;

    private Paint paint;
    private boolean divider;
    private Rect rect = new Rect();

    public EpisodeView(Context context) {
        super(context);

        setElevation(Extensions.dp(context, 1));
        setForeground(Extensions.selectableItemBackgroundDrawable(context));
        setBackgroundColor(ContextCompat.getColor(context, Theme.foregroundColor()));

        if (paint == null) {
            paint = new Paint();
            paint.setStrokeWidth(1);
            paint.setColor(ContextCompat.getColor(context, Theme.dividerColor()));
        }

        nameText = new TextView(context);
        nameText.setLines(1);
        nameText.setMaxLines(1);
        nameText.setSingleLine();
        nameText.setEllipsize(TextUtils.TruncateAt.END);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        nameText.setTextColor(ContextCompat.getColor(context, Theme.changelogVersionText()));
        nameText.setLayoutParams(LayoutHelper.makeFrame(context, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 10, 56, 0));
        addView(nameText);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(LayoutHelper.makeFrame(context, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.START | Gravity.TOP, 16, 35, 16, 0));
        addView(layout);

        valueText = new TextView(context);
        valueText.setLines(1);
        valueText.setMaxLines(1);
        valueText.setSingleLine();
        valueText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        valueText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        valueText.setLayoutParams(LayoutHelper.makeLinear(context, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL));
        layout.addView(valueText);

        View dotDivider = new View(context);
        dotDivider.setBackground(AndroidExtensions.getIcon(context, R.drawable.dot_divider, ContextCompat.getColor(context, Theme.secondaryTextColor())));
        dotDivider.setLayoutParams(LayoutHelper.makeLinear(context, 4, 4, Gravity.CENTER_VERTICAL, 6, 1, 6, 0));
        layout.addView(dotDivider);

        airDateText = new TextView(context);
        airDateText.setLines(1);
        airDateText.setMaxLines(1);
        airDateText.setSingleLine();
        airDateText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        airDateText.setTextColor(ContextCompat.getColor(context, Theme.secondaryTextColor()));
        airDateText.setLayoutParams(LayoutHelper.makeLinear(context, LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL));
        layout.addView(airDateText);

        ImageView selectIcon = new ImageView(context);
        selectIcon.setVisibility(VISIBLE);
        selectIcon.setImageDrawable(AndroidExtensions.getIcon(context, R.drawable.ic_check_outline, ContextCompat.getColor(context, R.color.green)));
        selectIcon.setLayoutParams(LayoutHelper.makeFrame(context, 29, 29, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 14, 0));
        addView(selectIcon);

        checkBox = new CheckBox(context, R.drawable.round_check2);
        checkBox.setVisibility(VISIBLE);
        checkBox.setColor(ContextCompat.getColor(context, R.color.green), ContextCompat.getColor(context, Theme.foregroundColor()));
        checkBox.setLayoutParams(LayoutHelper.makeFrame(context, 24, 24, Gravity.END | Gravity.CENTER_VERTICAL, 0, 0, 16, 0));
        addView(checkBox);
    }

    public void setName(@NonNull String text) {
        nameText.setText(text);
    }

    public void setNumber(@NonNull String text) {
        valueText.setText(text);
    }

    public void setDate(String text) {
        if (TextUtils.isEmpty(text)) {
            airDateText.setText(R.string.UnknownDate);
        } else {
            airDateText.setText(text);
        }
    }

    public void setChecked(boolean value, boolean animated) {
        checkBox.setChecked(value, animated);
    }

    public void setDivider(boolean divider) {
        this.divider = divider;
        setWillNotDraw(!divider);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
        int height = Extensions.dp(getContext(),64) + (divider ? 1 : 0);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (divider) {
            canvas.drawLine(getPaddingLeft(), getHeight() - 1, getWidth() - getPaddingRight(), getHeight() - 1, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getForeground() != null) {
            if (rect.contains((int) event.getX(), (int) event.getY())) {
                return true;
            }

            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                getForeground().setHotspot(event.getX(), event.getY());
            }
        }

        return super.onTouchEvent(event);
    }
}