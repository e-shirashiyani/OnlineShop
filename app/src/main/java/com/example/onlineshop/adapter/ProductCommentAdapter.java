package com.example.onlineshop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Comment;
import com.example.onlineshop.databinding.ItemCommentBinding;
import com.example.onlineshop.viewmodel.ProductViewModel;

public class ProductCommentAdapter extends RecyclerView.Adapter<ProductCommentAdapter.ProductHolder> {
    private final ProductViewModel mProductViewModel;
    private final LifecycleOwner mOwner;
    public ProductCommentAdapter(LifecycleOwner owner, ProductViewModel productViewModel) {
        mOwner = owner;
        mProductViewModel = productViewModel;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(mProductViewModel.getApplication());
        ItemCommentBinding itemCommentBinding= DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_comment,
                parent,
                false);
        ProductHolder productHolder=new ProductHolder(itemCommentBinding);
        return productHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Comment comment=mProductViewModel.getCommentList().get(position);
        holder.bindProduct(comment);

    }

    @Override
    public int getItemCount() {
        return mProductViewModel.getCommentList().size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private final ItemCommentBinding mItemCommentBinding;
        public ProductHolder(ItemCommentBinding itemCommentBinding) {
            super(itemCommentBinding.getRoot());
            mItemCommentBinding=itemCommentBinding;
            mItemCommentBinding.setLifecycleOwner(mOwner);
        }
        public void bindProduct(Comment comment){
            mItemCommentBinding.userComment.setText(comment.getReviewer());
            mItemCommentBinding.userEmail.setText(comment.getReviewer_email());
            mItemCommentBinding.userRate.setText(String.valueOf(comment.getRating()));
            mItemCommentBinding.userComment.setText(comment.getReview());
        }
    }
}
