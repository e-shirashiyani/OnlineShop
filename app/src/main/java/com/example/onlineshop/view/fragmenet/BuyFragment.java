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
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.BuyProductAdapter;
import com.example.onlineshop.data.model.Coupons;
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
    private LiveData<List<Coupons>> mCouponsLiveData;
    private BuyProductAdapter mBuyProductAdapter;
    private List<Product> mProductList;
    private String mCode = "";
    boolean flagCheck = false;

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
                mBuyProductAdapter = new BuyProductAdapter(getActivity(), getActivity(), mCartViewModel);
                mBuyBinding.recyclerCart.setAdapter(mBuyProductAdapter);
                setTotalPrice();

            }
        });
    }

    private void setTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < mProductList.size(); i++) {
            int price = Integer.parseInt(mProductList.get(i).getPrice());
            int count = mCartViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
            totalPrice += (price * count);
        }
        mBuyBinding.totalPrice.setText(String.valueOf(totalPrice));
    }

    public void codeObserver() {
        mCouponsLiveData.observe(this, new Observer<List<Coupons>>() {
            @Override
            public void onChanged(List<Coupons> coupons) {
                boolean flag = false;
                for (int i = 0; i < coupons.size(); i++) {
                    if (mBuyBinding.editTextCode.getText().toString().equals(coupons.get(i).getCode())) {
                        mCode = coupons.get(i).getCode();
                        applyCode(coupons.get(i).getAmount());
                        flag = true;
                    }
                }
                if (!flag) {
                    mBuyBinding.textViewCheckCode.setText(R.string.discount_code_is_wrong);
                    mBuyBinding.textViewCheckCode.setTextColor(getResources().getColor(R.color.warning));
                    mCode = "";
                    setTotalPrice();
                }
            }
        });
    }

    private void applyCode(String amount) {
        String[] codeAmountArray = amount.split("\\.");
        String codeAmount = codeAmountArray[0];

        if (Integer.parseInt(mBuyBinding.totalPrice.getText().toString()) > Integer.parseInt(codeAmount)) {
            double newPrice = Integer.parseInt(mBuyBinding.totalPrice.getText().toString()) - Integer.parseInt(codeAmount);
            mBuyBinding.totalPrice.setText(String.valueOf(newPrice).split("\\.")[0]);
            String discountCodeReport = getString(
                    R.string.discount_code_is_correct,
                    codeAmount);
            flagCheck = true;
            mBuyBinding.textViewCheckCode.setText(discountCodeReport);
            mBuyBinding.textViewCheckCode.setTextColor(getResources().getColor(R.color.teal_200));
        } else if (!flagCheck){
            mBuyBinding.textViewCheckCode.setText(R.string.discount_code_is_correct_but_higher);
            mBuyBinding.textViewCheckCode.setTextColor(getResources().getColor(R.color.purple_500));
            flagCheck = false;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_LOCATION) {
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
                if (mSettingViewModel.getSelectedAddress() == null)
                    Toast.makeText(getContext(), R.string.enter_address, Toast.LENGTH_SHORT).show();
                else
                    mCartViewModel.onclickBuy();
            }
        });
        mBuyBinding.buttonCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBuyBinding.editTextCode.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),R.string.enter_code,Toast.LENGTH_SHORT).show();
                    mBuyBinding.textViewCheckCode.setText("");
                    setTotalPrice();
                }
                else if (mBuyBinding.editTextCode.getText().toString().equals(mCode)) {
                    Toast toast = Toast.makeText(getContext(), R.string.discount_code_is_applied_once,
                            Toast.LENGTH_LONG);
                    TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                    textView.setTextColor(getResources().getColor(R.color.warning));
                    toast.show();
                }else if (!mBuyBinding.editTextCode.getText().toString().equals(mCode)){
                    mCartViewModel.fetchCoupons();
                    mCouponsLiveData = mCartViewModel.getLiveDataCoupons();
                    codeObserver();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setLocation();
    }

    private void initView() {
        setLocation();
        mBuyBinding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        mSettingViewModel.setContext(getActivity());
        mBuyBinding.setSettingViewModel(mSettingViewModel);

    }

    private void setLocation() {
        if (mSettingViewModel.getSelectedAddress() != null) {
            String[] name = mSettingViewModel.getSelectedAddress().getAddressName().split("\n");
            mBuyBinding.textViewAddressName.setText(getString(R.string.address, name[0], name[1]));
        }
    }
}