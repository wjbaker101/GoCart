package com.wjbaker.gocart.datastore;

import com.google.firebase.auth.FirebaseUser;
import com.wjbaker.gocart.firebase.Authentication;
import com.wjbaker.gocart.firebase.FirebaseDatabase;
import com.wjbaker.gocart.shopping.Product;

public class ProductDatastore {

    private static final String KEY_COLLECTION_PRODUCTS = "products";
    private static final String KEY_DOCUMENT_PRODUCTS = "products";

    private FirebaseDatabase database;

    public ProductDatastore(FirebaseDatabase database) {
        this.database = database;
    }

    public void addProduct(Product product) {
        FirebaseUser currentUser = Authentication.getInstance().getFirebaseAuth().getCurrentUser();

        if (currentUser == null) {
            return;
        }

        this.database.getFirestore()
                .collection(KEY_COLLECTION_PRODUCTS)
                .document(currentUser.getUid())
                .set(product)
                .addOnSuccessListener(result -> {
                    System.out.println("Successfully added Product.");
                })
                .addOnFailureListener(result -> {
                    System.out.println("Unable to add Product.");
                });
    }

    public void getProducts() {

    }
}
