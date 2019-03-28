package com.wjbaker.gocart.firebase;

import com.google.firebase.auth.FirebaseAuth;

public class Authentication {

    private static Authentication instance;

    private FirebaseAuth firebaseAuth;

    private Authentication() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public static Authentication getInstance() {
        if (instance == null) {
            instance = new Authentication();
        }

        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return this.firebaseAuth;
    }
}
