package com.wjbaker.gocart.ui.fragments.shopping;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wjbaker.gocart.R;

public class ShoppingListFragment extends Fragment {

    public ShoppingListFragment() {}

    public static ShoppingListFragment create() {
        ShoppingListFragment fragment = new ShoppingListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shopping_list, container, false);
    }
}
