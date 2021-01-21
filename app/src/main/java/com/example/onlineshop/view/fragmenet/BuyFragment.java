package com.example.onlineshop.view.fragmenet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.BuyProductAdapter;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.databinding.FragmentBuyBinding;
import com.example.onlineshop.view.activity.LocationActivity;
import com.example.onlineshop.viewmodel.CartViewModel;
import com.example.onlineshop.viewmodel.SettingViewModel;

import java.util.ArrayList;
import java.util.List;


public class BuyFragment extends Fragment {


    public static final int REQUEST_CODE_LOCATION = 0;
    private FragmentBuyBinding mBuyBinding;
    private SettingViewModel mSettingViewModel;
    private CartViewModel mCartViewModel;
    private LiveData<Product> mProductLiveData;
    private BuyProductAdapter mBuyProductAdapter;
    private List<Product> mProductList;

    public BuyFragment() {
        // Required empty public constructor
    }

    public static BuyFragment newInstance() {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mCartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        mProductList = new ArrayList<>();
        mCartViewModel.getOrderedProduct();
        mProductLiveData = mCartViewModel.getLiveDateProduct();
        observer();
    }

    private void observer() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProductList.add(product);
                mCartViewModel.setProductList(mProductList);
                mBuyProductAdapter = new BuyProductAdapter(getActivity(),getActivity(), mCartViewModel);
                mBuyBinding.recyclerCart.setAdapter(mBuyProductAdapter);
                int totalPrice = 0;
                for (int i = 0; i < mProductList.size(); i++) {
                    int price = Integer.parseInt(mProductList.get(i).getPrice());
                    int count = mCartViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
                    totalPrice += (price * count);
                }
                mBuyBinding.totalPrice.setText(String.valueOf(totalPrice));

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_LOCATION){
            setLocation();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBuyBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_buy,
                container,
                false);

        initView();
        listeners();
        return mBuyBinding.getRoot();
    }

    private void listeners() {
        mBuyBinding.editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(LocationActivity.newIntent(getActivity()), REQUEST_CODE_LOCATION);
            }
        });
        mBuyBinding.buttonContinueBuying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCartViewModel.onclickBuy();
            }
        });
    }

    private void initView() {
        setLocation();

        mBuyBinding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingViewModel.setContext(getActivity());
        mBuyBinding.setSettingViewModel(mSettingViewModel);

    }

    private void setLocation() {
        String[] name = mSettingViewModel.getSelectedAddress().getAddressName().split("\n");
        mBuyBinding.textViewAddressName.setText(name[0] + "\t" + name[1]);
    }
}