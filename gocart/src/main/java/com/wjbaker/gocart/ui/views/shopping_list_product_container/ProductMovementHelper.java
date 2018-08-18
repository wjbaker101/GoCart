package com.wjbaker.gocart.ui.views.shopping_list_product_container;

import android.graphics.Canvas;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.ui.views.shopping_list_product_container.adapter.ShoppingListProductAdapter;

/**
 * Created by William on 07/05/2018.
 *
 * Creates the functionality of the movement of the products when the use drags them.
 */
public class ProductMovementHelper extends ItemTouchHelper.Callback
{
    /**
     * Stores the adapter of the RecyclerView which the Product is inside.
     */
    private ShoppingListProductAdapter adapter;

    public ProductMovementHelper(ShoppingListProductAdapter adapter)
    {
        this.adapter = adapter;
    }

    /**
     * Gets which directions the Product can be moved towards.
     *
     * @param recyclerView RecyclerView which holds the Product.
     * @param viewHolder ViewHolder of the Product.
     * @return Integer values of directions.
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        // Allow the user to move the Product UP and DOWN
        // But not LEFT and RIGHT
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = 0;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * Called when the Product has been moved a position.
     *
     * @param recyclerView RecyclerView which holds the Product.
     * @param viewHolder ViewHolder of the Product.
     * @param target ViewHolder of another Product being moved out of the way.
     * @return True.
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        this.adapter.onViewMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        // No functionality for swiping LEFT and RIGHT
    }

    /**
     * Handles drawing of the Product's view.<br>
     * Changes the colour of the Products background when the user is dragging it.
     *
     * @param c Canvas of the View.
     * @param recyclerView RecyclerView which holds the Product.
     * @param viewHolder ViewHolder of the Product.
     * @param dX Change in X position of the Product.
     * @param dY Change in Y position of the Product.
     * @param actionState The current action of the movement of the Product.
     * @param isCurrentlyActive Whether the Product is currently active or not.
     */
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        // Get the view of the product
        CardView cardView = viewHolder.itemView.findViewById(R.id.product_item_shopping_content);

        // Set the initial/default value of the background colour
        cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.white));

        // Checks whether the Product is currently being dragged
        if (isCurrentlyActive)
        {
            cardView.setCardBackgroundColor(cardView.getResources().getColor(R.color.dark_grey));
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     * Interface for methods used for the movement of the Product.
     */
    public interface ActionCompletedContract
    {
        /**
         * Called when the position of the Product has changed.
         *
         * @param oldPosition The index of the position of the Product before being dragged.
         * @param newPosition The index of the current position of the Product.
         */
        void onViewMoved(int oldPosition, int newPosition);
    }
}
