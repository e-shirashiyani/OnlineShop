package com.example.onlineshop.view.fragmenet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshop.R;
import com.example.onlineshop.SwipeableRecyclerView;
import com.example.onlineshop.adapter.AddressAdapter;
import com.example.onlineshop.data.model.MapAddress;
import com.example.onlineshop.databinding.FragmentLocationBinding;
import com.example.onlineshop.viewmodel.SettingViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class LocationFragment extends Fragment {

    private FragmentLocationBinding mLocationBinding;
    private SettingViewModel mSettingViewModel;
    private AddressAdapter mAddressAdapter;
    private MutableLiveData<List<MapAddress>> mLiveDataAddress;
    private List<MapAddress> mMapAddressList;
    private MapAddress mAddressUndo;

    public LocationFragment() {
        // Required empty public constructor
    }

    public static LocationFragment newInstance() {
        LocationFragment fragment = new LocationFragment();
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

        mLocationBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_location,
                container,
                false);

        mLocationBinding.setSettingViewModel(mSettingViewModel);
        mSettingViewModel.setContext(getActivity());
        mLiveDataAddress = mSettingViewModel.getLiveDataAddress();
        if (mSettingViewModel.getAddresses().size() != 0)
            setProductAdapter();

        initView();
        observer();
        return mLocationBinding.getRoot();
    }

    private void observer() {
        mLiveDataAddress.observe(getActivity(), new Observer<List<MapAddress>>() {
            @Override
            public void onChanged(List<MapAddress> mapAddresses) {
                mAddressAdapter.setMapAddresses(mapAddresses);
                mAddressAdapter.notifyDataSetChanged();
                mMapAddressList = mapAddresses;
                setResult();
            }
        });
    }

    private void setResult() {

        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        getActivity().setResult(resultCode, intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        setProductAdapter();
    }

    @Override
    public void onPause() {
        super.onPause();
        setProductAdapter();
    }

    private void setProductAdapter() {
        mAddressAdapter = new AddressAdapter(this, getActivity(), mSettingViewModel);
        mAddressAdapter.setMapAddresses(mSettingViewModel.getAddresses());
        mLocationBinding.recyclerLocation.setAdapter(mAddressAdapter);
        mSettingViewModel.setAddressAdapter(mAddressAdapter);
    }

    private void initView() {
        mLocationBinding.recyclerLocation
                .setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRecycler();
    }

    private void swipeRecycler() {
        /*  set swipe touch listener */
        SwipeableRecyclerView swipeTouchListener = new
                SwipeableRecyclerView(mLocationBinding.recyclerLocation,
                new SwipeableRecyclerView.SwipeListener() {

                    @Override
                    public boolean canSwipeRight(int position) {
                        //enable/disable right swipe on checkbox base else use true/false
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        //on recycler view swipe right dismiss update adapter
                        onRecyclerViewDismiss(reverseSortedPositions, mMapAddressList);
                    }
                });

        //add item touch listener to recycler view
        mLocationBinding.recyclerLocation.addOnItemTouchListener(swipeTouchListener);
    }

    private void onRecyclerViewDismiss(int[] reverseSortedPositions, List<MapAddress> mapAddressList) {
        for (int position : reverseSortedPositions) {
            mAddressUndo = mapAddressList.get(position);
            mSettingViewModel.deleteLocationAddress(mapAddressList.get(position));
        }
        List<MapAddress> newAddressList = mSettingViewModel.getAddresses();
        mLiveDataAddress.setValue(newAddressList);
        mSettingViewModel.setLiveDataAddress(mLiveDataAddress);
        showSnackBar();
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(mLocationBinding.mainLayoutConstraint,
                R.string.location_dismiss_success, Snackbar.LENGTH_SHORT);
        snackbar.setAction(R.string.location_dismiss_undo, new MyUndoListener());
        snackbar.show();
    }

    public class MyUndoListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mSettingViewModel.insertAddress(mAddressUndo);
            List<MapAddress> newAddressList = mSettingViewModel.getAddresses();
            mLiveDataAddress.setValue(newAddressList);
            mSettingViewModel.setLiveDataAddress(mLiveDataAddress);
        }
    }

}