package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Images;
import com.example.onlineshop.databinding.ItemHighestScoreBinding;
import com.example.onlineshop.viewmodel.ProductViewModel;


public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.ProductHolder> {

    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;

    public ProductDetailAdapter(LifecycleOwner owner,ProductViewModel productViewModel) {
        mOwner = owner;
        mProductViewModel = productViewModel;
    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getDetailedProduct().getImages().size();
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

        Images images = mProductViewModel.getDetailedProduct().getImages().get(position);
        holder.bindProduct(images);
    }

    class ProductHolder extends RecyclerView.ViewHolder {

        private final ItemHighestScoreBinding mItemHighestScoreBinding;

        public ProductHolder(ItemHighestScoreBinding itemHighestScoreBinding) {
            super(itemHighestScoreBinding.getRoot());

            mItemHighestScoreBinding = itemHighestScoreBinding;
            mItemHighestScoreBinding.setLifecycleOwner(mOwner);
        }

        public void bindProduct(Images image) {

            Glide.with(itemView)
                    .load(image.getSrc())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(mItemHighestScoreBinding.imageHighestScore);
        }
    }
}
