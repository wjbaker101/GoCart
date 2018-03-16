package com.wjbaker.gocart.ui.elements;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.shopping.Product;

/**
 * Created by William on 14/03/2018.
 */
public abstract class ProductItem extends CardView
{
    private Product boundProduct;

    protected float scale;

    private Paint[] allPaints;

    protected Paint white;
    protected Paint black;
    protected Paint lightGrey;
    protected Paint darkGrey;
    protected Paint primaryColour;
    protected Paint darkerGrey;
    protected Paint primaryDarkColour;
    protected Paint green;

    public ProductItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.boundProduct = null;

        this.scale = getResources().getDisplayMetrics().density;

        this.initDisplay();

        this.initColours();
    }

    private void initDisplay()
    {
        // Adds shadow around the View
        this.setCardElevation(2.0F * this.scale);

        // Adds extra padding so that shadow is visible
        this.setUseCompatPadding(true);

        // Sets the default padding of the View
        this.setPadding(8, 8, 8, 8);
    }

    /**
     * Creates the different colours and sets their properties.
     */
    private void initColours()
    {
        final float strokeWidth = 4.0F;
        final float fontSize = 45.0F;

        this.allPaints = new Paint[]
        {
            this.white = new Paint(),
            this.black = new Paint(),
            this.lightGrey = new Paint(),
            this.darkGrey = new Paint(),
            this.darkerGrey = new Paint(),
            this.primaryColour = new Paint(),
            this.primaryDarkColour = new Paint(),
            this.green = new Paint(),
        };

        this.white.setColor(getResources().getColor(R.color.white));
        this.black.setColor(getResources().getColor(R.color.black));
        this.lightGrey.setColor(getResources().getColor(R.color.light_grey));
        this.darkGrey.setColor(getResources().getColor(R.color.dark_grey));
        this.darkerGrey.setColor(getResources().getColor(R.color.darker_grey));
        this.primaryColour.setColor(getResources().getColor(R.color.colorPrimary));
        this.primaryDarkColour.setColor(getResources().getColor(R.color.colorPrimaryDark));
        this.green.setColor(getResources().getColor(R.color.green));

        // Loops through all of the defined paints
        // Sets the common properties of each
        for (Paint paint : this.allPaints)
        {
            paint.setAntiAlias(true);
            paint.setStrokeWidth(strokeWidth);
            paint.setTextSize(fontSize);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: return this.onTouchDown(touchX, touchY);
            case MotionEvent.ACTION_UP: return this.onTouchUp(touchX, touchY);
        }

        return true;
    }

    /**
     * Called when the user touches the View.
     *
     * @param x X position of the touch, relative to the top-left corner of the View.
     * @param y Y position of the touch, relative to the top-left corner of the View.
     * @return Boolean value.
     */
    protected abstract boolean onTouchDown(float x, float y);

    /**
     * Called when the user lifts their finger after a touch.
     *
     * @param x X position of the touch, relative to the top-left corner of the View.
     * @param y Y position of the touch, relative to the top-left corner of the View.
     * @return Boolean value.
     */
    protected abstract boolean onTouchUp(float x, float y);

    /**
     * Scales the given value from pixels relative the density of the device's screen.
     *
     * @param value Number of pixels to scale.
     * @return The scaled value.
     */
    protected int scaled(int value)
    {
        return (int)(value * this.scale);
    }

    /**
     * Stores the product information from the given Product.
     *
     * @param product Product to bind to the View.
     */
    public void bindProduct(Product product)
    {
        this.boundProduct = product;
    }

    /**
     * Gets the currently bound Product.
     *
     * @return The Product.
     */
    protected Product getBoundProduct()
    {
        return this.boundProduct;
    }
}
