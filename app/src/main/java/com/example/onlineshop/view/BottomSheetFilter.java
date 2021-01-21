package com.example.onlineshop.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Attributes;
import com.example.onlineshop.data.model.ColorAttribute;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.databinding.LayoutBottomSheetFilterBinding;
import com.example.onlineshop.databinding.LayoutBottomSheetFilterCategoryBinding;
import com.example.onlineshop.viewmodel.SearchViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetFilter extends BottomSheetDialogFragment {




    public static final String EXTRA_FILTER_COLOR = "extra_filter_color";
    BottomSheetBehavior bottomSheetBehavior;
    LayoutBottomSheetFilterBinding mFilterBinding;
    LayoutBottomSheetFilterCategoryBinding mFilterCategoryBinding;
    private int REQUEST_CODE;
    private SearchViewModel mSearchViewModel;
    private LiveData<List<ColorAttribute>> mColorsLiveData;
    private String mColor;
    private List<ColorAttribute> mColorAttributes;
    private int mProductId;
    private LiveData<Product> mProductLiveData;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheet = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);


        REQUEST_CODE = getTargetRequestCode();
        mSearchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        //inflating layout
        if (REQUEST_CODE == 0) {

            setLayoutForFilterHome(bottomSheet);

            mSearchViewModel.fetchColorAttributeAsync();
            mColorAttributes = new ArrayList<>();
            mColorsLiveData = mSearchViewModel.getColorsLiveData();

            observer();

            updateUI();

            //aap bar cancel button clicked
            listeners();


            //hiding app bar at the start
            hideAppBar(mFilterBinding.appBarLayout);

        }else {
            setLayoutForFilter(bottomSheet);
            //aap bar cancel button clicked
            String id = mSearchViewModel.getProductIdForFilterFromPreferences();
            mProductId = Integer.parseInt(id);
            mSearchViewModel.fetchProductItems(mProductId);
            mProductLiveData = mSearchViewModel.getLiveDateProduct();
            observerCategory();
            listenersForFilterCategory();

            //hiding app bar at the start
            hideAppBar(mFilterCategoryBinding.appBarLayout);

        }

        return bottomSheet;
    }

    private void observerCategory() {
        if (mProductLiveData == null)
            return;
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                List<Attributes> attributesList = product.getAttributes();
                StringBuilder attribute = new StringBuilder();
                for (int i = 0; i < attributesList.size(); i++) {
                    attribute.append(attributesList.get(i).getName());
                }
                mFilterCategoryBinding.userName.setText(attribute + "Attributes");
            }
        });
    }

    private void observer() {
        mColorsLiveData.observe(this, new Observer<List<ColorAttribute>>() {
            @Override
            public void onChanged(List<ColorAttribute> colorAttributes) {
                mColorAttributes.addAll(colorAttributes);
                setColorToView();
                updateUI();
            }
        });
    }

    private void setColorToView() {
        mFilterBinding.blue.setText(mColorAttributes.get(0).getName());
        mFilterBinding.white.setText(mColorAttributes.get(1).getName());
        mFilterBinding.pink.setText(mColorAttributes.get(2).getName());
        mFilterBinding.coral.setText(mColorAttributes.get(3).getName());
        mFilterBinding.black.setText(mColorAttributes.get(4).getName());
        mFilterBinding.orange.setText(mColorAttributes.get(5).getName());

        setColorDrawable(0, mFilterBinding.color1);
        setColorDrawable(1, mFilterBinding.color2);
        setColorDrawable(2, mFilterBinding.color3);
        setColorDrawable(3, mFilterBinding.color4);
        setColorDrawable(4, mFilterBinding.color5);
        setColorDrawable(5, mFilterBinding.color6);
    }

    private void setColorDrawable(int i, ImageView p) {
        if (mColorAttributes.get(i).getName().equals("سفید"))
            p.setImageDrawable(getResources().getDrawable(R.drawable.ic__color_white));
        else if (mColorAttributes.get(i).getName().equals("آبی"))
            p.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_blue));
        else if (mColorAttributes.get(i).getName().equals("صورتی"))
            p.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_pink));
        else if (mColorAttributes.get(i).getName().equals("مرجانی"))
            p.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_coral));
        else if (mColorAttributes.get(i).getName().equals("مشکی"))
            p.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_black));
        else if (mColorAttributes.get(i).getName().equals("نارنجی"))
            p.setImageDrawable(getResources().getDrawable(R.drawable.ic_color_orange));
    }

    private void updateUI() {
        String color = mSearchViewModel.getColorFromPreferences();
        if (color != null){
            if (color.equalsIgnoreCase("سفید"))
                mFilterBinding.white.setChecked(true);
            else if (color.equalsIgnoreCase("آبی"))
                mFilterBinding.blue.setChecked(true);
            else if (color.equalsIgnoreCase("صورتی"))
                mFilterBinding.pink.setChecked(true);
            else if (color.equalsIgnoreCase("مرجانی"))
                mFilterBinding.coral.setChecked(true);
            else if (color.equalsIgnoreCase("مشکی"))
                mFilterBinding.black.setChecked(true);
            else if (color.equalsIgnoreCase("نارنجی"))
                mFilterBinding.orange.setChecked(true);
            else if (color.equalsIgnoreCase(""))
                mFilterBinding.noFilter.setChecked(true);
        }
    }

    private void setLayoutForFilterHome(BottomSheetDialog bottomSheet) {
        View view = View.inflate(getContext(), R.layout.layout_bottom_sheet_filter, null);

        //binding views to data binding.
        mFilterBinding = DataBindingUtil.bind(view);

        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //setting max height of bottom sheet
        mFilterBinding.extraSpace.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(mFilterBinding.appBarLayout, getActionBarSize());
                    hideAppBar(mFilterBinding.colorLayout);

                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(mFilterBinding.appBarLayout);
                    showView(mFilterBinding.colorLayout, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void setLayoutForFilter(BottomSheetDialog bottomSheet) {
        View view = View.inflate(getContext(), R.layout.layout_bottom_sheet_filter_category, null);

        //binding views to data binding.
        mFilterCategoryBinding = DataBindingUtil.bind(view);

        //setting layout with bottom sheet
        bottomSheet.setContentView(view);

        bottomSheetBehavior = BottomSheetBehavior.from((View) (view.getParent()));


        //setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.PEEK_HEIGHT_AUTO);

        //setting max height of bottom sheet
        mFilterCategoryBinding.extraSpace.setMinimumHeight((Resources.getSystem().getDisplayMetrics().heightPixels) / 2);


        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_EXPANDED == i) {
                    showView(mFilterCategoryBinding.appBarLayout, getActionBarSize());
                    hideAppBar(mFilterCategoryBinding.colorLayout);

                }
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    hideAppBar(mFilterCategoryBinding.appBarLayout);
                    showView(mFilterCategoryBinding.colorLayout, getActionBarSize());
                }

                if (BottomSheetBehavior.STATE_HIDDEN == i) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void listenersForFilterCategory() {

    }

    private void listeners() {
        mFilterBinding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColor();
                dismiss();
                sendResult(mColor);
            }
        });
        mFilterBinding.black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "مشکی";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });
        mFilterBinding.white.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "سفید";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });
        mFilterBinding.coral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "مرجانی";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });
        mFilterBinding.orange.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "نارنجی";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });
        mFilterBinding.blue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "آبی";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });
        mFilterBinding.pink.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "صورتی";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });
        mFilterBinding.noFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mColor = "";
                    mSearchViewModel.setColorInPreferences(null);
                    mSearchViewModel.setColorInPreferences(mColor);
                    sendResult(mColor);
                }
            }
        });

    }

    private void setColor() {
        if (mFilterBinding.white.isChecked())
            mColor = "سفید";
        else if (mFilterBinding.blue.isChecked())
            mColor = "آبی";
        else if (mFilterBinding.pink.isChecked())
            mColor = "صورتی";
        else if (mFilterBinding.coral.isChecked())
            mColor = "مرجانی";
        else if (mFilterBinding.black.isChecked())
            mColor = "مشکی";
        else if (mFilterBinding.orange.isChecked())
            mColor = "نارنجی";
        else if (mFilterBinding.noFilter.isChecked())
            mColor = "";
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

    private void sendResult(String color) {
        mSearchViewModel.setColorInPreferences(color);
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_FILTER_COLOR, color);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}
