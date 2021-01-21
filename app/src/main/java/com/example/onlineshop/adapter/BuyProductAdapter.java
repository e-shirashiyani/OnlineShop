package com.example.onlineshop.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Cart;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.repository.CartDBRepository;
import com.example.onlineshop.databinding.ItemBuyBinding;
import com.example.onlineshop.viewmodel.CartViewModel;

public class BuyProductAdapter extends RecyclerView.Adapter<BuyProductAdapter.ProductHolder> {
    private final CartViewModel mCartViewModel;
    private final LifecycleOwner mOwner;
    private Context mContext;
    public BuyProductAdapter(LifecycleOwner owner, Context context, CartViewModel cartViewModel) {
        mOwner = owner;
        mCartViewModel = cartViewModel;
        mCartViewModel.setContext(context);
        mContext = context;
    }
    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCartViewModel.getApplication());
        ItemBuyBinding itemBuyBinding= DataBindingUtil.inflate(
                inflater,
                R.layout.item_buy,
                parent,
                false);
        ProductHolder productHolder=new ProductHolder(itemBuyBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product=mCartViewModel.getProductList().get(position);
        holder.bindProduct(product);

    }

    @Override
    public int getItemCount() {
        return mCartViewModel.getProductList().size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        ItemBuyBinding mItemBuyBinding;
        CartDBRepository mCartDBRepository;
        public ProductHolder(ItemBuyBinding itemBuyBinding) {
            super(itemBuyBinding.getRoot());
            mItemBuyBinding=itemBuyBinding;
            mItemBuyBinding.setCartViewModel(mCartViewModel);
            mItemBuyBinding.setLifecycleOwner(mOwner);
            mCartDBRepository=CartDBRepository.getInstance(mContext);

        }
        public void bindProduct(Product product){
            Cart cart=mCartDBRepository.getCart(product.getId());
            if(cart!=null){
                mItemBuyBinding.setProductId(product.getId());
                mItemBuyBinding.textCartProductName.setText(product.getTitle());
                mItemBuyBinding.numberOfProduct
                        .setText(String.valueOf(cart.getProduct_count()));
            }
            mItemBuyBinding.textCartProductPrice.setText(product.getPrice());
            Glide.with(mItemBuyBinding.getRoot())
            .load(product.getImages().get(0).getSrc())
            .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .into(mItemBuyBinding.imageCartProduct);


        }
    }
}
