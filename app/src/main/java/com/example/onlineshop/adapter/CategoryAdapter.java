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
import com.example.onlineshop.data.model.ProductCategory;
import com.example.onlineshop.databinding.ItemCategoryBinding;
import com.example.onlineshop.viewmodel.CategoryViewModel;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private final CategoryViewModel mCategoryViewModel;
    private final LifecycleOwner mOwner;

    public CategoryAdapter(LifecycleOwner owner, Context context, CategoryViewModel categoryViewModel) {
        mOwner = owner;
        mCategoryViewModel = categoryViewModel;
        mCategoryViewModel.setContext(context);
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCategoryViewModel.getApplication());
        ItemCategoryBinding itemCategoryBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_category,
                parent,
                false);

        CategoryHolder categoryHolder = new CategoryHolder(itemCategoryBinding);
        return categoryHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        ProductCategory category=mCategoryViewModel.getCategoryList().get(position);
        holder.bindProduct(category);
    }

    @Override
    public int getItemCount() {
        return mCategoryViewModel.getCategoryList().size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        ItemCategoryBinding mItemCategoryBinding;

        public CategoryHolder(@NonNull ItemCategoryBinding itemCategoryBinding) {
            super(itemCategoryBinding.getRoot());
            mItemCategoryBinding = itemCategoryBinding;
            mItemCategoryBinding.setCategoryViewModel(mCategoryViewModel);
            mItemCategoryBinding.setState("category");
            mItemCategoryBinding.setLifecycleOwner(mOwner);
        }
        public void bindProduct(ProductCategory category) {
            mItemCategoryBinding.setParentId(category.getId());
            mItemCategoryBinding.textCategory.setText(category.getName());
            Glide.with(itemView)
                    .load(category.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.ic_shopping_basket)
                    .into(mItemCategoryBinding.imageCategory);
        }
    }
}
