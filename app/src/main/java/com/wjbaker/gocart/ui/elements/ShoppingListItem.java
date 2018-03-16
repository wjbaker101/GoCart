package com.wjbaker.gocart.ui.elements;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.wjbaker.gocart.R;

/**
 * Created by William on 11/03/2018.
 */
public class ShoppingListItem extends ProductItem
{
    /**
     * Stores whether the CheckBox is checked or not.
     */
    private boolean isChecked;

    /**
     * Stores whether or not the touch down was over the checkbox
     */
    private boolean isTouchingCheckbox;

    public ShoppingListItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        this.isChecked = false;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        this.drawGrabIndicator(canvas, (int)(8 * this.scale), (int)(31 * this.scale), (int)(5 * this.scale));

        this.drawCheckBox(canvas, (int)(2 * 16 * this.scale) + (int)(15.5F * this.scale), 55);

        if (this.getBoundProduct() != null)
            canvas.drawText(this.getBoundProduct().getName(), (int)(3 * 16 * this.scale) + (int)(15.5F * this.scale) + 55, (int)(this.getHeight() / 2.0F) + (50 / 3.0F), this.black);
    }

    private void drawCheckBox(Canvas canvas, int x, int width)
    {
        int y = (int)((this.getHeight() / 2.0F) - (width / 2.0F));

        if (this.isChecked)
        {
            canvas.drawRect(x, y, x + width, y + width, this.darkerGrey);
        }
        else
        {
            canvas.drawLine(x, y, x + width, y, this.darkerGrey);
            canvas.drawLine(x + width, y, x + width, y + width, this.darkerGrey);
            canvas.drawLine(x, y + width, x + width, y + width, this.darkerGrey);
            canvas.drawLine(x, y, x, y + width, this.darkerGrey);
        }
    }

    private void drawGrabIndicator(Canvas canvas, int startX, int width, int dotSize)
    {
        int x = startX + (int)(width / 2.0F);
        int y = (int)((this.getHeight() / 2.0F));

        canvas.drawCircle(x, y, dotSize, this.darkerGrey);
    }

    @Override
    protected boolean onTouchDown(float x, float y)
    {
        float startX = 2 * 16 * scale + 15.5F * scale;
        float startY = (this.getHeight() / 2.0F) - (55 / 2.0F);

        if (x > startX - 20 && x < startX + 75)
        {
            if (y > startY - 20 && y < startY + 75)
            {
                this.isTouchingCheckbox = true;
            }
        }

        return true;
    }

    @Override
    protected boolean onTouchUp(float x, float y)
    {
        float startX = 2 * 16 * scale + 15.5F * scale;
        float startY = (this.getHeight() / 2.0F) - (55 / 2.0F);

        if (!this.isTouchingCheckbox) return true;

        if (x > startX - 20 && x < startX + 75)
        {
            if (y > startY - 20 && y < startY + 75)
            {
                this.isTouchingCheckbox = false;
                this.isChecked = !this.isChecked;

                this.invalidate();
            }
        }

        return true;
    }
}
