package com.example.onlineshop.view.fragmenet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.Comment;
import com.example.onlineshop.databinding.FragmentEditCommentBinding;
import com.example.onlineshop.viewmodel.CartViewModel;


public class EditCommentFragment extends Fragment {


    public static final String BUNDLE_EDIT_COMMENT = "Bundle_edit_comment";
    public static final String FRAGMENT_TAG_DELETE_COMMENT = "DeleteComment";
    public static final int REQUEST_CODE_DELETE_COMMENT = 0;
    private int mCommentId;
    private FragmentEditCommentBinding mEditCommentBinding;
    private CartViewModel mCartViewModel;
    private MutableLiveData<Comment> mLiveDataOneComment;
    private Comment mComment;
    private int mRate;
    private MutableLiveData<Integer> mLiveDataRate;

    public EditCommentFragment() {
        // Required empty public constructor
    }

    public static EditCommentFragment newInstance(int commentId) {
        EditCommentFragment fragment = new EditCommentFragment();
        Bundle args = new Bundle();
        args.putInt(BUNDLE_EDIT_COMMENT,commentId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            mCommentId = getArguments().getInt(BUNDLE_EDIT_COMMENT);

        mCartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        mCartViewModel.fetchOneComment(mCommentId);
        mLiveDataOneComment = mCartViewModel.getLiveDataOneComment();
        mRate = 0;
        mLiveDataRate = mCartViewModel.getLiveDataRate();
        observer();

    }

    private void observer() {
        mLiveDataOneComment.observe(this, new Observer<Comment>() {
            @Override
            public void onChanged(Comment comment) {
                mComment = comment;
                mEditCommentBinding.name.setText(comment.getReviewer());
                mEditCommentBinding.name.setEnabled(false);
                mEditCommentBinding.email.setText(comment.getReviewer_email());
                mEditCommentBinding.email.setEnabled(false);
                mEditCommentBinding.comment.setText(comment.getReview());
                mEditCommentBinding.comment.setEnabled(false);
                checkRate(comment.getRating());
                mEditCommentBinding.star1Edit.setEnabled(false);
                mEditCommentBinding.star2Edit.setEnabled(false);
                mEditCommentBinding.star3Edit.setEnabled(false);
                mEditCommentBinding.star4Edit.setEnabled(false);
                mEditCommentBinding.star5Edit.setEnabled(false);

            }
        });

        mLiveDataRate.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                mRate = integer;
                checkRate(integer);
            }
        });
    }

    private void checkRate(Integer integer) {
        if (integer == 1) {
            mEditCommentBinding.star1Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star2Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mEditCommentBinding.star3Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mEditCommentBinding.star4Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mEditCommentBinding.star5Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 2){
            mEditCommentBinding.star1Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star2Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star3Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mEditCommentBinding.star4Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mEditCommentBinding.star5Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 3){
            mEditCommentBinding.star1Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star2Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star3Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star4Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
            mEditCommentBinding.star5Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 4){
            mEditCommentBinding.star1Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star2Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star3Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star4Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star5Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_border));
        }else if (integer == 5){
            mEditCommentBinding.star1Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star2Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star3Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star4Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
            mEditCommentBinding.star5Edit.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_star_rate));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mEditCommentBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_edit_comment,
                container,
                false );

        mEditCommentBinding.setCartViewModel(mCartViewModel);
        mCartViewModel.setEditCommentBinding(mEditCommentBinding);
        listeners();
        return mEditCommentBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_DELETE_COMMENT){
            getActivity().finish();
        }
    }

    private void listeners() {
        mEditCommentBinding.btnSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    mComment.setReviewer(mEditCommentBinding.name.getText().toString());
                    mComment.setReviewer_email(mEditCommentBinding.email.getText().toString());
                    mComment.setReview(mEditCommentBinding.comment.getText().toString());
                    mComment.setRating(mRate);
                    mCartViewModel.fetchPutComment(mComment);
                    getActivity().finish();
                }
                else {
                    checkInput();
                }
            }
        });

        mEditCommentBinding.btnDeleteEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DeleteCommentFragment deleteCommentFragment = DeleteCommentFragment.newInstance(mCommentId);

                deleteCommentFragment.setTargetFragment(
                        EditCommentFragment.this,
                        REQUEST_CODE_DELETE_COMMENT);

                deleteCommentFragment.show(
                        getActivity().getSupportFragmentManager(),
                        FRAGMENT_TAG_DELETE_COMMENT);
            }
        });
    }

    private boolean validateInput() {
        return !mEditCommentBinding.name.getText().toString().trim().isEmpty() &&
                !mEditCommentBinding.email.getText().toString().trim().isEmpty() &&
                !mEditCommentBinding.comment.getText().toString().trim().isEmpty();
    }

    private void checkInput() {
        mEditCommentBinding.nameForm.setErrorEnabled(false);
        mEditCommentBinding.emailForm.setErrorEnabled(false);
        mEditCommentBinding.commentForm.setErrorEnabled(false);
        if (mEditCommentBinding.name.getText().toString().trim().isEmpty()) {
            mEditCommentBinding.nameForm.setErrorEnabled(true);
            mEditCommentBinding.nameForm.setError("Field cannot be empty!");
        }
        if (mEditCommentBinding.email.getText().toString().trim().isEmpty()) {
            mEditCommentBinding.emailForm.setErrorEnabled(true);
            mEditCommentBinding.emailForm.setError("Field cannot be empty!");
        }
        if (mEditCommentBinding.comment.getText().toString().trim().isEmpty()) {
            mEditCommentBinding.commentForm.setErrorEnabled(true);
            mEditCommentBinding.commentForm.setError("Field cannot be empty!");
        }
    }
}