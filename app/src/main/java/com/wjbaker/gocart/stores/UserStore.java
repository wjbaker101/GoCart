package com.wjbaker.gocart.stores;

import android.content.Context;

import com.wjbaker.gocart.database.DatabaseStorage;
import com.wjbaker.gocart.database.DatabaseStorageHelper;

import java.io.DataInputStream;

/**
 * Created by William on 29/04/2018.
 *
 * Stores the currently selected TescoStore the user has chosen.
 */
public class UserStore
{
    /**
     * The instance of the singleton.
     */
    private static UserStore instance;

    /**
     * Allows the use of the database by passing the context.
     */
    private Context context;

    /**
     * The TescoStore the user has chosen to be their current TescoStore.
     */
    private TescoStore theTescoStore;

    /**
     * Initialises the storage for the user's TescoStore.
     *
     * @param context
     */
    public UserStore(Context context)
    {
        this.context = context;

        this.theTescoStore = DatabaseStorage.query(context).getTescoStore();
    }

    /**
     * Allows the class to act as a singleton.<br>
     * Returns the current UserStore object, or creates a new one if none exists.
     *
     * @return The UserStore object.
     */
    public static synchronized UserStore getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new UserStore(context);
        }

        return instance;
    }

    /**
     * Sets a new TescoStore as the user's preference.
     *
     * @param tescoStore The new TescoStore.
     */
    public void setStore(TescoStore tescoStore)
    {
        this.theTescoStore = tescoStore;

        if (DatabaseStorage.query(this.context).getTescoStore() == null)
            DatabaseStorage.query(this.context).addTescoStore(tescoStore);
        else
            DatabaseStorage.query(this.context).updateStore(tescoStore);

    }

    /**
     * Gets the current TescoStore the user has chosen.
     *
     * @return TescoStore.
     */
    public TescoStore getStore()
    {
        return this.theTescoStore;
    }
}
