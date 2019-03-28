package com.wjbaker.gocart;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.wjbaker.gocart.datastore.ProductDatastore;
import com.wjbaker.gocart.firebase.FirebaseDatabase;

/**
 * Acts as a global object which activities can access for app-wide information.
 */
public class GoCartApp extends Application {

    public GoCartApp() {}
}
