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
import com.example.onlineshop.databinding.ItemMostVisitedBinding;
import com.example.onlineshop.viewmodel.ProductViewModel;


public class MostVisitedProductAdapter extends RecyclerView.Adapter<MostVisitedProductAdapter.ProductHolder> {

    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;
    private OnBottomReachedListener mOnBottomReachedListener;

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        mOnBottomReachedListener = onBottomReachedListener;
    }

    public MostVisitedProductAdapter(LifecycleOwner owner,Context context,ProductViewModel productViewModel) {
        mOwner = owner;
        mProductViewModel = productViewModel;
        mProductViewModel.setContext(context);
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getProductListMostVisited().size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mProductViewModel.getApplication());
        ItemMostVisitedBinding mostVisitedBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_most_visited,
                parent,
                false);

        ProductHolder productHolder = new ProductHolder(mostVisitedBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mProductViewModel.getProductListMostVisited().get(position);
        /*if (position == (mProducts.size() - 1)){

            mOnBottomReachedListener.onBottomReached(position);

        }*/
        holder.bindProduct(product);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private final ItemMostVisitedBinding mItemMostVisitedBinding;

        public ProductHolder(ItemMostVisitedBinding itemMostVisitedBinding) {
            super(itemMostVisitedBinding.getRoot());

            mItemMostVisitedBinding = itemMostVisitedBinding;
            mItemMostVisitedBinding.setProductViewModel(mProductViewModel);
            mItemMostVisitedBinding.setLifecycleOwner(mOwner);
        }

        public void bindProduct(Product product) {
            mItemMostVisitedBinding.setProductId(product.getId());
            mItemMostVisitedBinding.textViewNameMostVisited.setText(product.getTitle());
            mItemMostVisitedBinding.textViewPriceMostVisited.setText(product.getPrice());
            Glide.with(mItemMostVisitedBinding.getRoot())
                    .load(product.getImages().get(0).getSrc())
                    .centerCrop()
                    .placeholder(R.drawable.ic_shopping_cart__3_)
                    .into(mItemMostVisitedBinding.imageMostVisited);
        }
    }
}
