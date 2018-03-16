package com.wjbaker.gocart.ui.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.ShoppingList;

import java.util.Locale;

/**
 * Created by William on 14/03/2018.
 */
public class ProductSearchItem extends ProductItem
{
    private final int plusSize = this.scaled(8);

    private Bitmap tick;

    private boolean isTouchingAddButton;

    public ProductSearchItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.isTouchingAddButton = false;

        Bitmap originalTick = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_tick);
        this.tick = Bitmap.createScaledBitmap(originalTick, this.plusSize * 2, this.plusSize * 2, false);
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        if (this.getBoundProduct() != null)
            canvas.drawText(this.getBoundProduct().getName(), this.scaled(16), (int)(this.getHeight() / 2.0F) + (45 / 3.0F), this.black);

        this.drawPlus(canvas, this.getWidth() - this.scaled(8 + 16), (int)(this.getHeight() / 2.0F));

        this.drawCost(canvas);

        this.drawFadedBackground(canvas);
    }

    /**
     * Draws the "+" icon on the right-hand side of the View.
     *
     * @param canvas Canvas to draw onto.
     * @param centreX Central horizontal position of the plus.
     * @param centreY Central vertical position of the plus.
     */
    private void drawPlus(Canvas canvas, int centreX, int centreY)
    {
        final int left = this.scaled(16 * 2 + 4 * 3 + 1);

        canvas.drawRect(this.getWidth() - left, 0, this.getWidth(), this.getHeight(), this.lightGrey);

        canvas.drawLine(this.getWidth() - left, 0, this.getWidth() - left, this.getHeight(), this.darkGrey);

        if (ShoppingList.getInstance().get(this.getBoundProduct().getTPNB()) == null)
        {
            canvas.drawLine(centreX - this.plusSize, centreY, centreX + this.plusSize, centreY, this.darkerGrey);
            canvas.drawLine(centreX, centreY - this.plusSize, centreX, centreY + this.plusSize, this.darkerGrey);
        }
        else
        {
            canvas.drawBitmap(this.tick, centreX - this.plusSize, centreY - this.plusSize, this.darkerGrey);
        }
    }

    private void drawCost(Canvas canvas)
    {
        final String cost = this.getFormattedCost();

        final float stringWidth = this.black.measureText(cost);

        final int right = this.getWidth() - this.scaled(16 * 2 + 4 * 3 + 1);

        canvas.drawRect(right - stringWidth - this.scaled(8 * 2), 0, right, this.getHeight(), this.white);

        canvas.drawText(cost, right - stringWidth - this.scaled(8), (int)(this.getHeight() / 2.0F) + (45 / 3.0F), this.black);
    }

    private void drawFadedBackground(Canvas canvas)
    {
        final float stringWidth = this.black.measureText(this.getFormattedCost());

        final int right = this.getWidth() - this.scaled(16 * 2 + 4 * 3 + 1 + 8 * 2) - (int)stringWidth;

        final int width = 25;

        Shader gradient = new LinearGradient(right - width, 0, right, 0, Color.TRANSPARENT, Color.WHITE, Shader.TileMode.CLAMP);

        Paint p = new Paint();
        p.setShader(gradient);

        canvas.drawRect(right - width, 0, right, this.getHeight(), p);
    }

    @Override
    protected boolean onTouchDown(float x, float y)
    {
        final int left = this.scaled((16 * 2) + (4 * 3) + 1);

        if (x > this.getWidth() - left && x < this.getWidth())
        {
            if (y > 0 && y < this.getHeight())
            {
                this.isTouchingAddButton = true;
            }
        }

        return true;
    }

    @Override
    protected boolean onTouchUp(float x, float y)
    {
        final int left = this.scaled((16 * 2) + (4 * 3) + 1);

        if (!this.isTouchingAddButton || ShoppingList.getInstance().get(this.getBoundProduct().getTPNB()) != null) return true;

        if (x > this.getWidth() - left && x < this.getWidth())
        {
            if (y > 0 && y < this.getHeight())
            {
                this.isTouchingAddButton = false;

                ShoppingList.getInstance().addItem(this.getBoundProduct());

                this.invalidate();
            }
        }

        return true;
    }

    private String getFormattedCost()
    {
        float cost = this.getBoundProduct().getCost();

        return String.format(Locale.ENGLISH, "£%.2f", cost);
    }
}
