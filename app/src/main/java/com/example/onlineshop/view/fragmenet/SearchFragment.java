package com.example.onlineshop.view.fragmenet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.SearchProductAdapter;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.databinding.FragmentSearchBinding;
import com.example.onlineshop.view.BottomSheetFilter;
import com.example.onlineshop.view.BottomSheetSort;
import com.example.onlineshop.viewmodel.ProductViewModel;
import com.example.onlineshop.viewmodel.SearchViewModel;

import java.util.List;

public class SearchFragment extends Fragment {

    public static final String SEARCH_QUERY = "search_query";
    public static final int REQUEST_CODE_FILTER = 0;
    public static final String TAG_BOTTOM_SHEET_FILTER = "tag_Bottom_sheet_filter";
    public static final int REQUEST_CODE_SORT = 1;
    public static final String TAG_BOTTOM_SHEET_SORT = "tag_bottom_sheet_sort";
    public static final String REQUEST_CODE = "request_code";
    public static final int REQUEST_CODE_FILTER_CATEGORY = 5;
    public static final String PRODUCT_ID = "productId";
    private SearchViewModel mSearchViewModel;
    private SearchProductAdapter mSearchProductAdapter;
    private String mQuery;
    private String mRequestCode;
    private FragmentSearchBinding mFragmentSearchBinding;
    private LiveData<List<Product>> mLiveDataSearchProducts;
    private LiveData<List<Product>> mLiveDataSortedLowToHighSearchProducts;
    private LiveData<List<Product>> mLiveDataSortedHighToLowSearchProducts;
    private LiveData<List<Product>> mLiveDataTotalSalesSearchProducts;
    private String mProductId;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance(String query, String requestCode,String productId) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_QUERY, query);
        args.putString(REQUEST_CODE, requestCode);
        args.putString(PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuery = getArguments().getString(SEARCH_QUERY);
            mRequestCode = getArguments().getString(REQUEST_CODE);
            mProductId = getArguments().getString(PRODUCT_ID);
        }
        setHasOptionsMenu(true);

        mSearchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        mSearchViewModel.fetchSearchItemsAsync(mQuery);
        mSearchViewModel.setQueryInPreferences(mQuery);
        mLiveDataSearchProducts = mSearchViewModel.getSearchItemsLiveData();
        mLiveDataSortedLowToHighSearchProducts = mSearchViewModel.getSortedLowToHighSearchItemsLiveData();
        mLiveDataSortedHighToLowSearchProducts = mSearchViewModel.getSortedHighToLowSearchItemsLiveData();
        mLiveDataTotalSalesSearchProducts = mSearchViewModel.getSortedTotalSalesSearchItemsLiveData();
        observers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentSearchBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_search,
                container,
                false);

        initView();

        return mFragmentSearchBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        setSearchViewListeners(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_id:
                BottomSheetFilter bottomSheetFilter =
                        new BottomSheetFilter();
                if (mRequestCode.equalsIgnoreCase("category")) {
                    bottomSheetFilter.setTargetFragment(
                            SearchFragment.this,
                            REQUEST_CODE_FILTER_CATEGORY);
                    mSearchViewModel.setProductIdForFilterInPreferences(mProductId);
                } else {
                    String color = mSearchViewModel.getColorFromPreferences();
                    if (color != null)
                        mSearchViewModel.setColorInPreferences(color);
                    else
                        mSearchViewModel.setColorInPreferences("");
                    mSearchViewModel.setProductIdForFilterInPreferences("0");
                    bottomSheetFilter.setTargetFragment(
                            SearchFragment.this,
                            REQUEST_CODE_FILTER);
                }

                bottomSheetFilter.show(
                        getActivity().getSupportFragmentManager(),
                        TAG_BOTTOM_SHEET_FILTER);
                return true;
            case R.id.sort_id:
                BottomSheetSort bottomSheetSort =
                        new BottomSheetSort();
                bottomSheetSort.setTargetFragment(
                        SearchFragment.this,
                        REQUEST_CODE_SORT);

                bottomSheetSort.show(
                        getActivity().getSupportFragmentManager(),
                        TAG_BOTTOM_SHEET_SORT);
//                BottomSheetSort bottomSheetSort = new BottomSheetSort();
//                bottomSheetSort.show(getActivity().getSupportFragmentManager(), bottomSheetSort.getTag());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_FILTER) {
            Toast.makeText(getContext(), "filter", Toast.LENGTH_SHORT).show();
            String color = data.getStringExtra(BottomSheetFilter.EXTRA_FILTER_COLOR);
            mSearchViewModel.fetchSearchItemsAsync(mQuery + " " + color);
            mSearchViewModel.setQueryInPreferences(mQuery);
            observers();
        } else if (requestCode == REQUEST_CODE_SORT) {
            Toast.makeText(getContext(), "Sort", Toast.LENGTH_SHORT).show();
            checkSort(data.getIntExtra(BottomSheetSort.EXTRA_SORT_ID, 4));
        }

        //Todo....get data from bottomSheetFilter when filter in category
    }

    private void checkSort(int intExtra) {

        if (intExtra == BottomSheetSort.TOP_SELLERS) {

            mSearchViewModel.fetchSortedTotalSalesSearchItemsAsync(mQuery);
            mSearchViewModel.setQueryInPreferences(mQuery);

        } else if (intExtra == BottomSheetSort.PRICES_LOW_TO_HIGH) {

            mSearchViewModel.fetchSortedLowToHighSearchItemsAsync(mQuery);
            mSearchViewModel.setQueryInPreferences(mQuery);

        } else if (intExtra == BottomSheetSort.PRICES_HIGH_TO_LOW) {

            mSearchViewModel.fetchSortedHighToLowSearchItemsAsync(mQuery);
            mSearchViewModel.setQueryInPreferences(mQuery);

        } else if (intExtra == BottomSheetSort.THE_NEWEST) {

            mSearchViewModel.fetchSearchItemsAsync(mQuery);
            mSearchViewModel.setQueryInPreferences(mQuery);
        }
    }

    private void setSearchViewListeners(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                mSearchViewModel.fetchSearchItemsAsync(query);
                mSearchViewModel.setQueryInPreferences(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = mSearchViewModel.getQueryFromPreferences();
                if (query != null)
                    searchView.setQuery(query, false);
            }
        });
    }

    private void observers() {
        mLiveDataSearchProducts.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSearchViewModel.setSearchProduct(productList);
                Toast.makeText(getContext(), "Sort Newest", Toast.LENGTH_SHORT).show();
                setSearchAdapter();
            }
        });

        mLiveDataSortedLowToHighSearchProducts.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSearchViewModel.setSearchProduct(productList);
                Toast.makeText(getContext(), "Sort Low to High", Toast.LENGTH_SHORT).show();
                setSearchAdapter();
            }
        });

        mLiveDataSortedHighToLowSearchProducts.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                mSearchViewModel.setSearchProduct(productList);
                Toast.makeText(getContext(), "Sort High to Low", Toast.LENGTH_SHORT).show();
                setSearchAdapter();
            }
        });

        mLiveDataTotalSalesSearchProducts.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> productList) {
                Toast.makeText(getContext(), "Sort Top Seller", Toast.LENGTH_SHORT).show();
                mSearchViewModel.setSearchProduct(productList);
                setSearchAdapter();
            }
        });
    }

    private void setSearchAdapter() {
        mSearchProductAdapter = new SearchProductAdapter(this, getActivity(), mSearchViewModel);
        mFragmentSearchBinding.recyclerSearch.setAdapter(mSearchProductAdapter);
    }

    private void initView() {
        mFragmentSearchBinding.recyclerSearch.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
