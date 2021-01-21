package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.repository.CartDBRepository;
import com.example.onlineshop.databinding.ItemCartBinding;
import com.example.onlineshop.viewmodel.CartViewModel;

public class OrderedProductAdapter extends RecyclerView.Adapter<OrderedProductAdapter.ProductHolder> {
    private final CartViewModel mCartViewModel;
    private final LifecycleOwner mOwner;
    private Context mContext;
    public OrderedProductAdapter(LifecycleOwner owner, Context context, CartViewModel cartViewModel) {
        mOwner = owner;
        mCartViewModel = cartViewModel;
        mCartViewModel.setContext(context);
        mContext = context;
    }
    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCartViewModel.getApplication());
        ItemCartBinding itemCartBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_cart,
                parent,
                false);

        ProductHolder productHolder = new ProductHolder(itemCartBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = mCartViewModel.getProductList().get(position);
        holder.bindProduct(product);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        ItemCartBinding mItemCartBinding;
        CartDBRepository mCartDBRepository;

        public ProductHolder(ItemCartBinding itemCartBinding) {
            super(itemCartBinding.getRoot());
            mItemCartBinding = itemCartBinding;
            mItemCartBinding.setCartViewModel(mCartViewModel);
            mItemCartBinding.setLifecycleOwner(mOwner);
            mCartDBRepository = CartDBRepository.getInstance(mContext);


        }

        public void bindProduct(Product product) {
            mItemCartBinding.setProductId(product.getId());
            mItemCartBinding.textCartProductName.setText(product.getTitle());
            mItemCartBinding.textCartProductPrice.setText(product.getPrice());
            Glide.with(itemView)
                    .load(product.getImages().get(0).getSrc())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mItemCartBinding.imageCartProduct);
        }
    }

    @Override
    public int getItemCount() {
        return mCartViewModel.getProductList().size();
    }


}
