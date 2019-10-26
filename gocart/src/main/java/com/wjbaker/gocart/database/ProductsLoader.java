package com.wjbaker.gocart.database;

import android.content.Context;
import android.database.Cursor;
import androidx.loader.content.AsyncTaskLoader;

import com.wjbaker.gocart.shopping.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 04/05/2018.
 *
 * Custom Content Loader.<br>
 * Provides a way of getting data from the ProductsProvider.
 */
public class ProductsLoader extends AsyncTaskLoader<List<Product>>
{
    private List<Product> data;

    private Context context;

    public ProductsLoader(Context context)
    {
        super(context);

        this.context = context;
    }

    /**
     * Queries the database using the ProductsProvider.
     *
     * @return List of Products in the user's shopping list.
     */
    @Override
    public List<Product> loadInBackground()
    {
        List<Product> loadedData = new ArrayList<>();

        Cursor productsFromProvider = this.context.getContentResolver().query(ProductsProvider.CONTENT_URI, null, null, null, null);

        if (productsFromProvider.getCount() > 0)
        {
            productsFromProvider.moveToFirst();

            do
            {
                int tpnb = Integer.parseInt(productsFromProvider.getString(0));
                String name = productsFromProvider.getString(1);
                String description = productsFromProvider.getString(2);
                float cost = Float.parseFloat(productsFromProvider.getString(3));
                float quantity = Float.parseFloat(productsFromProvider.getString(4));
                String superDepartment = productsFromProvider.getString(5);
                String department = productsFromProvider.getString(6);
                String imageURL = productsFromProvider.getString(7);
                Boolean isChecked = productsFromProvider.getString(8).equals("1");
                int amount = Integer.parseInt(productsFromProvider.getString(9));

                Product product = new Product(tpnb, name, description, cost, quantity, superDepartment, department, imageURL);

                product.setChecked(isChecked);
                product.setAmount(amount);

                loadedData.add(product);
            }
            while (productsFromProvider.moveToNext());
        }

        return loadedData;
    }

    @Override
    public void deliverResult(List<Product> data)
    {
        if (isReset())
        {
            this.releaseResources(data);

            return;
        }

        List<Product> oldData = this.data;

        this.data = data;

        if (this.isStarted())
        {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data)
        {
            releaseResources(oldData);
        }
    }

    @Override
    protected void onStartLoading()
    {
        if (this.data != null)
        {
            deliverResult(this.data);
        }

        if (this.takeContentChanged() || this.data == null)
        {
            this.forceLoad();
        }
    }

    @Override
    protected void onStopLoading()
    {
        this.cancelLoad();
    }

    @Override
    protected void onReset()
    {
        this.onStopLoading();

        if (this.data != null)
        {
            releaseResources(this.data);
            this.data = null;
        }
    }

    @Override
    public void onCanceled(List<Product> data)
    {
        super.onCanceled(data);

        this.releaseResources(data);
    }

    private void releaseResources(List<Product> data)
    {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }
}
