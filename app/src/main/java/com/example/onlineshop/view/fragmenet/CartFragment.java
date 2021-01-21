package com.example.onlineshop.view.fragmenet;

import android.os.Bundle;

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
import com.example.onlineshop.adapter.OrderedProductAdapter;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.viewmodel.CartViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends VisibleFragment {



    FragmentCartBinding mFragmentCartBinding;
    private CartViewModel mCartViewModel;
    private LiveData<Product> mProductLiveData;
    private List<Product> mProductList;
    private OrderedProductAdapter mOrderedProductAdapter;
    private LiveData<Customer> mCustomerLiveData;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductList = new ArrayList<>();
        mCartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        mCartViewModel.getOrderedProduct();
        mProductLiveData = mCartViewModel.getLiveDateProduct();
        mCustomerLiveData = mCartViewModel.getLiveDateCustomer();
        observer();
    }

    private void observer() {
        mProductLiveData.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                mProductList.add(product);
                mCartViewModel.setProductList(mProductList);
                mOrderedProductAdapter = new OrderedProductAdapter(getActivity(),getActivity(), mCartViewModel);
                mFragmentCartBinding.recyclerCart.setAdapter(mOrderedProductAdapter);
                mCartViewModel.setOrderedProductAdapter(mOrderedProductAdapter);
                int totalPrice = 0;
                for (int i = 0; i < mProductList.size(); i++) {
                    if (!mProductList.get(i).getPrice().equals("")) {
                        int price = Integer.parseInt(mProductList.get(i).getPrice());
                        int count = mCartViewModel.getCart(mProductList.get(i).getId()).getProduct_count();
                        totalPrice += (price * count);
                    }
                }
                mFragmentCartBinding.totalPrice.setText(String.valueOf(totalPrice));
                if (mProductList.size() == 0) {
                    mFragmentCartBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
                    mFragmentCartBinding.constraintLayoutContinue.setVisibility(View.GONE);
                }
                else {
                    mFragmentCartBinding.layoutEmptyCart.setVisibility(View.GONE);
                    mFragmentCartBinding.constraintLayoutContinue.setVisibility(View.VISIBLE);
                }
            }
        });

        /*mCustomerLiveData.observe(this, new Observer<Customer>() {
            @Override
            public void onChanged(Customer customer) {
                Toast.makeText(getActivity(),customer.getFirst_name() + "\t" + customer.getLast_name()
                + "\t" + customer.getEmail() + "\t" , Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mFragmentCartBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_cart,
                container,
                false);

        mCartViewModel.setFragmentCartBinding(mFragmentCartBinding);

        initView();
        return mFragmentCartBinding.getRoot();
    }

    private void initView() {
        mFragmentCartBinding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        mFragmentCartBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
        mFragmentCartBinding.constraintLayoutContinue.setVisibility(View.GONE);
        mFragmentCartBinding.setCartViewModel(mCartViewModel);
    }
}