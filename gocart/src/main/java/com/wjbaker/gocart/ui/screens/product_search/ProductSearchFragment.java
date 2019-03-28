package com.wjbaker.gocart.ui.screens.product_search;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.VolleyError;
import com.wjbaker.gocart.R;
import com.wjbaker.gocart.model.ProductSearchResponse;
import com.wjbaker.gocart.model.TescoProduct;
import com.wjbaker.gocart.request.VolleyResponse;
import com.wjbaker.gocart.service.TescoService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class ProductSearchFragment extends Fragment {

    private static final Logger LOGGER = getLogger(ProductSearchFragment.class.getName());

    private EditText searchTextBox;
    private ProgressBar loadingIcon;
    private ProductSearchResultAdapter productSearchResultAdapter;

    private ArrayList<TescoProduct> productSearchResultItems;

    public ProductSearchFragment() {}

    public static ProductSearchFragment create() {
        ProductSearchFragment fragment = new ProductSearchFragment();
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

        View layout = inflater
                .inflate(R.layout.fragment_product_search, container, false);

        this.searchTextBox = layout.findViewById(R.id.product_search_textbox);
        this.searchTextBox.setOnEditorActionListener(this.getSearchAction());

        this.loadingIcon = layout.findViewById(R.id.product_search_loading_icon);

        RecyclerView productSearchResultContainer = layout
                .findViewById(R.id.product_search_result_container);

        productSearchResultContainer.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        productSearchResultContainer.setLayoutManager(linearLayoutManager);

        this.productSearchResultItems = new ArrayList<>();

        this.productSearchResultAdapter = new ProductSearchResultAdapter(
                this.productSearchResultItems,
                this.getActivity().getSupportFragmentManager());

        productSearchResultContainer.setAdapter(this.productSearchResultAdapter);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.setLoadingIconEnabled(false);

        if (this.getArguments() != null) {
            this.productSearchResultAdapter.setItems(this.getArguments()
                    .getParcelableArrayList("product_search_result_items"));

            this.productSearchResultAdapter.notifyDataSetChanged();
        }

        System.out.println(this.productSearchResultItems.size());

        if (this.searchTextBox.getText().length() == 0) {
            this.setKeyboardVisible(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (this.getArguments() == null) {
            this.setArguments(new Bundle());
        }

        this.getArguments().putParcelableArrayList(
                "product_search_result_items",
                this.productSearchResultItems);
    }

    private EditText.OnEditorActionListener getSearchAction() {
        return (textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                String searchTerm = textView.getText().toString();

                TescoService.get(this.getContext())
                        .searchProduct(searchTerm, 0, this.onSearchRequest());

                setLoadingIconEnabled(true);

                return true;
            }

            return false;
        };
    }

    private VolleyResponse<ProductSearchResponse> onSearchRequest() {
        return new VolleyResponse<ProductSearchResponse>() {
            @Override
            public void onSuccess(ProductSearchResponse response) {
                List<TescoProduct> searchResultItems = response
                        .getUk()
                        .getGhs()
                        .getProducts()
                        .getResults();

                System.out.println(String.format(
                        "Successfully received %s products from search request.",
                        searchResultItems.size()));

                setLoadingIconEnabled(false);
                setKeyboardVisible(false);

                productSearchResultAdapter.setItems(searchResultItems);
            }

            @Override
            public void onFailure(VolleyError error) {
                LOGGER.info("Unable to retrieve products in search request.");
                setLoadingIconEnabled(false);
            }
        };
    }

    private void setKeyboardVisible(final boolean isVisible) {
        if (isVisible) {
            this.searchTextBox.requestFocus();
        }

        int visibility = isVisible
                ? InputMethodManager.SHOW_IMPLICIT
                : InputMethodManager.HIDE_IMPLICIT_ONLY;

        if (this.getContext() == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager)this.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm == null) {
            return;
        }

        imm.showSoftInput(this.searchTextBox, visibility);
    }

    private void setLoadingIconEnabled(final boolean isEnabled) {
        this.loadingIcon.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
    }
}
