package com.wjbaker.gocart.firebase;

import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseDatabase {

    private FirebaseFirestore firebaseFirestore;

    public FirebaseDatabase() {
        this.firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getFirestore() {
        return this.firebaseFirestore;
    }
}
