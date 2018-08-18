package com.wjbaker.gocart.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by William on 03/05/2018.
 *
 * Provides an interface between the Activities and the database for getting Products in the shopping list.
 */
public class ProductsProvider extends ContentProvider
{
    /**
     * Stores the provider path for getting products.
     */
    private static final String PROVIDER_NAME = "com.wjbaker.gocart.products";

    /**
     * Creates a URI for the products.
     */
    public static final Uri CONTENT_URI = Uri.parse(String.format("content://%s/products", PROVIDER_NAME));

    /**
     * Stores the ID of the URI for getting all products.
     */
    private static final int PRODUCTS = 1;

    /**
     * Stores the ID of the URI got getting an individual Product.
     */
    private static final int PRODUCT_ID = 2;

    /**
     * Stores a newly created UriMatcher.
     */
    private static final UriMatcher uriMatcher = getUriMatcher();

    /**
     * Create a local instance of the database.
     */
    private DatabaseStorageHelper databaseStorageHelper;

    /**
     * Creates a new UriMatcher.
     *
     * @return UriMatcher.
     */
    private static UriMatcher getUriMatcher()
    {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(PROVIDER_NAME, "products", PRODUCTS);
        uriMatcher.addURI(PROVIDER_NAME, "products/#", PRODUCT_ID);

        return uriMatcher;
    }

    /**
     * Initialises the provider.<br>
     * Initialises the database.
     * @return
     */
    @Override
    public boolean onCreate()
    {
        Context context = getContext();

        this.databaseStorageHelper = new DatabaseStorageHelper(context);

        return true;
    }

    /**
     * Inserts a new Product into the database.
     *
     * @param uri
     * @param values
     * @return
     */
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        try
        {
            long id = this.databaseStorageHelper.addProduct(values);

            Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);

            return returnUri;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Queries the database.
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        String id = null;

        if (uriMatcher.match(uri) == PRODUCT_ID)
        {
            id = uri.getPathSegments().get(1);
        }

        return this.databaseStorageHelper.getProducts(id, projection, selection, selectionArgs, sortOrder);
    }

    /**
     * Deletes a Product from the database.
     *
     * @param uri
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        String id = null;

        if (uriMatcher.match(uri) == PRODUCT_ID)
        {
            id = uri.getPathSegments().get(1);
        }

        return this.databaseStorageHelper.deleteProduct(id);
    }

    /**
     * Updates an existing Product in the database.
     *
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        String id = null;

        if (uriMatcher.match(uri) == PRODUCT_ID)
        {
            id = uri.getPathSegments().get(1);
        }

        return this.databaseStorageHelper.updateProduct(id, values);
    }

    /**
     * Gets the type of URI which was passed.
     *
     * @param uri URI to check.
     * @return Path of the type of the URI.
     */
    @Override
    public String getType(Uri uri)
    {
        switch (uriMatcher.match(uri))
        {
            case PRODUCTS:
                return "vnd.android.cursor.dir/vnd.com.wjbaker.gocart.products";

            case PRODUCT_ID:
                return "vnd.android.cursor.item/vnd.com.wjbaker.gocart.products";

            default:
                return "";
        }
    }
}
