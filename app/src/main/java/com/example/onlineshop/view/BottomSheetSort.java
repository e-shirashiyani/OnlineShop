package com.example.onlineshop.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.LayoutBottomSheetSortBinding;
import com.example.onlineshop.viewmodel.SearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomSheetSort extends BottomSheetDialogFragment {


    public static final String EXTRA_SORT_ID = "extra_sort_id";
    BottomSheetBehavior bottomSheetBehavior;
    public static final int THE_NEWEST = 0;
    public static final int PRICES_HIGH_TO_LOW = 1;
    public static final int PRICES_LOW_TO_HIGH = 2;
    public static final int TOP_SELLERS = 3;
    private SearchViewModel mSearchViewModel;
    private LayoutBottomSheetSortBinding mBottomSheetSortBinding;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        //inflating layout
        View view = View.inflate(getContext(), R.layout.layout_bottom_sheet_sort, null);

        //binding views to data binding.
        mBottomSheetSortBinding = DataBindingUtil.bind(view);

        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);


        //setting max height of bottom sheet
        mBottomSheetSortBinding.extraSpace.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(mBottomSheetSortBinding.appBarLayout, getActionBarSize());
                    hideAppBar(mBottomSheetSortBinding.layoutSortSellers);

                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(mBottomSheetSortBinding.appBarLayout);
                    showView(mBottomSheetSortBinding.layoutSortSellers, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        //aap bar cancel button clicked
        mBottomSheetSortBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        //hiding app bar at the start
        hideAppBar(mBottomSheetSortBinding.appBarLayout);

        mSearchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        mBottomSheetSortBinding.layoutSortSellers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchViewModel.setSort(TOP_SELLERS);
                checkVisibility();
                sendResult(TOP_SELLERS);
            }
        });

        mBottomSheetSortBinding.layoutSortHighToLow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchViewModel.setSort(PRICES_HIGH_TO_LOW);
                checkVisibility();
                sendResult(PRICES_HIGH_TO_LOW);
            }
        });

        mBottomSheetSortBinding.layoutSortLowToHigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchViewModel.setSort(PRICES_LOW_TO_HIGH);
                checkVisibility();
                sendResult(PRICES_LOW_TO_HIGH);
            }
        });

        mBottomSheetSortBinding.layoutNewest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchViewModel.setSort(THE_NEWEST);
                checkVisibility();
                sendResult(THE_NEWEST);
            }
        });

        checkVisibility();


        return bottomSheet;
    }

    private void checkVisibility() {
        if (mSearchViewModel.getSort() == TOP_SELLERS){
            mBottomSheetSortBinding.ivBestSellers.setVisibility(View.VISIBLE);
            mBottomSheetSortBinding.ivPriceHighToLow.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivPriceLowToHigh.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivNewest.setVisibility(View.GONE);
        }else if (mSearchViewModel.getSort() == PRICES_HIGH_TO_LOW){
            mBottomSheetSortBinding.ivBestSellers.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivPriceHighToLow.setVisibility(View.VISIBLE);
            mBottomSheetSortBinding.ivPriceLowToHigh.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivNewest.setVisibility(View.GONE);
        }else if (mSearchViewModel.getSort() == PRICES_LOW_TO_HIGH){
            mBottomSheetSortBinding.ivBestSellers.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivPriceHighToLow.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivPriceLowToHigh.setVisibility(View.VISIBLE);
            mBottomSheetSortBinding.ivNewest.setVisibility(View.GONE);
        }else if (mSearchViewModel.getSort() == THE_NEWEST){
            mBottomSheetSortBinding.ivBestSellers.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivPriceHighToLow.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivPriceLowToHigh.setVisibility(View.GONE);
            mBottomSheetSortBinding.ivNewest.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void hideAppBar(View view) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = 0;
        view.setLayoutParams(params);

    }

    private void showView(View view, int size) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = size;
        view.setLayoutParams(params);
    }

    private int getActionBarSize() {
        final TypedArray array = getContext().getTheme().obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        int size = (int) array.getDimension(0, 0);
        return size;
    }

    private void sendResult(int sortId) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SORT_ID, sortId);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}
