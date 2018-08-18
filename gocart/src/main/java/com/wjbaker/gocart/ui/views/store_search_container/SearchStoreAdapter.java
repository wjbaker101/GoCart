package com.wjbaker.gocart.ui.views.store_search_container;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjbaker.gocart.R;
import com.wjbaker.gocart.stores.TescoStore;
import com.wjbaker.gocart.ui.activities.SearchStoreActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 25/04/2018.
 */

public class SearchStoreAdapter extends RecyclerView.Adapter<SearchStoreViewHolder>
{
    private List<TescoStore> tescoStores;

    private SearchStoreActivity searchStoreActivity;

    public SearchStoreAdapter(SearchStoreActivity searchStoreActivity)
    {
        this.tescoStores = new ArrayList<>();

        this.searchStoreActivity = searchStoreActivity;
    }

    @Override
    public SearchStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_search_item, parent, false);

        SearchStoreViewHolder viewHolder = new SearchStoreViewHolder(view, this.searchStoreActivity);

        return viewHolder;
    }

    /**
     * Updates the contents of the View within the Recycler View.
     *
     * @param holder Holder of the View.
     * @param position Position of the View within the dataset.
     */
    @Override
    public void onBindViewHolder(SearchStoreViewHolder holder, int position)
    {
        holder.bind(this.tescoStores.get(position));
    }

    @Override
    public int getItemCount()
    {
        return tescoStores.size();
    }

    public void addItem(TescoStore tescoStore)
    {
        this.tescoStores.add(tescoStore);

        this.notifyItemChanged(this.tescoStores.size() - 1);
    }

    public void removeItem(TescoStore tescoStore)
    {
        this.tescoStores.remove(tescoStore);

        this.notifyDataSetChanged();
    }

    public void removeAll()
    {
        this.tescoStores.clear();

        this.notifyDataSetChanged();
    }
}
