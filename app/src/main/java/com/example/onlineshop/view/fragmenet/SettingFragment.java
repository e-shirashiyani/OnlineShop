package com.example.onlineshop.view.fragmenet;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.databinding.FragmentSettingsBinding;
import com.example.onlineshop.databinding.SettingFragmentBinding;
import com.example.onlineshop.viewmodel.SettingViewModel;

public class SettingFragment extends Fragment {
    private FragmentSettingsBinding mSettingsBinding;
    private SettingViewModel mSettingViewModel;

    public SettingFragment() {
        // Required empty public constructor
    }
    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mSettingsBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_settings,
                container,
                false);

        initView();
        return mSettingsBinding.getRoot();
    }

    private void initView() {
        mSettingViewModel.setContext(getActivity());
        mSettingsBinding.setSettingViewModel(mSettingViewModel);
    }
}