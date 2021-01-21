package com.example.onlineshop.view.fragmenet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.CategoryAdapter;
import com.example.onlineshop.data.model.ProductCategory;
import com.example.onlineshop.databinding.FragmentCategoryBinding;
import com.example.onlineshop.viewmodel.CategoryViewModel;

import java.util.List;

public class CategoryFragment extends VisibleFragment {
    private CategoryAdapter mCategoryAdapter;
    private CategoryViewModel mCategoryViewModel;
    private FragmentCategoryBinding mCategoryBinding;
    private LiveData<List<ProductCategory>> mCategoryItemsLiveData;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getCategoryFromCategoryViewModel();
        setObserver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mCategoryBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_category,
                container,
                false);
        initView();
        return mCategoryBinding.getRoot();
    }

    private void getCategoryFromCategoryViewModel() {
        mCategoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        mCategoryViewModel.fetchCategoryItems();
        mCategoryItemsLiveData = mCategoryViewModel.getLiveDataCategoryItems();
    }

    private void setObserver() {
        mCategoryItemsLiveData.observe(this, new Observer<List<ProductCategory>>() {
            @Override
            public void onChanged(List<ProductCategory> categories) {
                mCategoryViewModel.setCategoryList(categories);
                setAdapter();
            }
        });
    }

    private void setAdapter() {
        mCategoryAdapter = new CategoryAdapter(this,getActivity(),mCategoryViewModel);
        mCategoryBinding.recyclerCategory.setAdapter(mCategoryAdapter);
    }

    private void initView() {
        mCategoryBinding.recyclerCategory.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
