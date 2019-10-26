package com.wjbaker.gocart.ui.views.store_search_container;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.stores.TescoStore;
import com.wjbaker.gocart.ui.activities.SearchStoreActivity;
import com.wjbaker.gocart.ui.dialogs.StoreSelectDialog;

/**
 * Created by William on 25/04/2018.
 */
public class SearchStoreViewHolder extends RecyclerView.ViewHolder
{
    private final View VIEW;

    private SearchStoreActivity activity;

    public SearchStoreViewHolder(View view, SearchStoreActivity activity)
    {
        super(view);

        this.VIEW = view;

        this.activity = activity;
    }

    public void bind(TescoStore tescoStore)
    {
        TextView nameTextView = this.VIEW.findViewById(R.id.store_search_item_name);

        nameTextView.setText(tescoStore.getName());

        this.VIEW.setOnClickListener(this.getViewOnClick(tescoStore));
    }

    private View.OnClickListener getViewOnClick(final TescoStore tescoStore)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                StoreSelectDialog
                    .create(tescoStore)
                    .show(activity.getFragmentManager(), "store_select");
            }
        };
    }
}
