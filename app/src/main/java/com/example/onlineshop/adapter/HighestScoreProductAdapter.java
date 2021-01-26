package com.example.onlineshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Product;

import com.example.onlineshop.databinding.ItemHighestScoreBinding;
import com.example.onlineshop.viewmodel.ProductViewModel;

public class HighestScoreProductAdapter extends RecyclerView.Adapter<HighestScoreProductAdapter.ProductHolder> {
    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;
    private OnBottomReachedListener mOnBottomReachedListener;

    public void setOnBottomReachedListener(OnBottomReachedListener onBottomReachedListener){

        mOnBottomReachedListener = onBottomReachedListener;
    }

    public HighestScoreProductAdapter(LifecycleOwner owner,Context context,ProductViewModel productViewModel) {
        mOwner = owner;
        mProductViewModel = productViewModel;
        mProductViewModel.setContext(context);
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getProductListHighestScore().size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mProductViewModel.getApplication());
        ItemHighestScoreBinding highestScoreBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_highest_score,
                parent,
                false);

        ProductHolder productHolder = new ProductHolder(highestScoreBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {

        Product product = mProductViewModel.getProductListHighestScore().get(position);
        /*if (position == (mProducts.size() - 1)){

            mOnBottomReachedListener.onBottomReached(position);

        }*/
        holder.bindProduct(product);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private final ItemHighestScoreBinding mItemHighestScoreBinding;

        public ProductHolder(ItemHighestScoreBinding itemHighestScoreBinding) {
            super(itemHighestScoreBinding.getRoot());

            mItemHighestScoreBinding = itemHighestScoreBinding;
            mItemHighestScoreBinding.setProductViewModel(mProductViewModel);
            mItemHighestScoreBinding.setLifecycleOwner(mOwner);
        }

        public void bindProduct(Product product) {
            mItemHighestScoreBinding.setProductId(product.getId());
            mItemHighestScoreBinding.textViewNameHighestScore.setText(product.getTitle());
            mItemHighestScoreBinding.textViewPriceHighestScore.setText(product.getPrice());

//            mTextView.setText(product.getTitle());
            Glide.with(mItemHighestScoreBinding.getRoot())
                    .load(product.getImages().get(0).getSrc())
                    .centerCrop()
                    .placeholder(R.drawable.ic_shopping_cart__3_)
                    .into(mItemHighestScoreBinding.imageHighestScore);
        }
    }
}
