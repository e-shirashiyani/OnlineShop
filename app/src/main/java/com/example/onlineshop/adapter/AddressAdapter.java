package com.example.onlineshop.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshop.R;
import com.example.onlineshop.data.model.MapAddress;
import com.example.onlineshop.databinding.ItemAddressBinding;
import com.example.onlineshop.viewmodel.SettingViewModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {
    private final SettingViewModel mSettingViewModel;
    private final LifecycleOwner mOwner;
    private List<MapAddress> mMapAddresses;

    public List<MapAddress> getMapAddresses() {
        return mMapAddresses;
    }

    public void setMapAddresses(List<MapAddress> mapAddresses) {
        mMapAddresses = mapAddresses;
    }

    public AddressAdapter(LifecycleOwner owner, Context context, SettingViewModel settingViewModel) {
        mOwner = owner;
        mSettingViewModel = settingViewModel;
        mSettingViewModel.setContext(context);
    }
    @Override
    public int getItemCount() {
        return mMapAddresses.size();
    }

    @NonNull
    @Override
    public AddressHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mSettingViewModel.getApplication());
        ItemAddressBinding itemAddressBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.item_address,
                parent,
                false);

        AddressHolder addressHolder = new AddressHolder(itemAddressBinding);
        return addressHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressHolder holder, int position) {

        MapAddress address = mMapAddresses.get(position);
        holder.bindProduct(address);
    }

    class AddressHolder extends RecyclerView.ViewHolder {

        ItemAddressBinding mItemAddressBinding;
        private MapAddress mMapAddress;

        public AddressHolder(ItemAddressBinding itemAddressBinding) {
            super(itemAddressBinding.getRoot());
            mItemAddressBinding = itemAddressBinding;
            mItemAddressBinding.setLifecycleOwner(mOwner);
            mItemAddressBinding.setSettingViewModel(mSettingViewModel);


        }

        public void bindProduct(MapAddress address) {

            mMapAddress = mSettingViewModel.getSelectedAddress();
            mItemAddressBinding.setAddressId(address.getPrimaryId());
            if (address.equals(mMapAddress))
                mItemAddressBinding.imageViewSelectedAddress.setVisibility(View.VISIBLE);
            else
                mItemAddressBinding.imageViewSelectedAddress.setVisibility(View.GONE);
            String mapAddress = address.getAddressName() + "\n\n"
                    + "Latitude: " + address.getAddress_lat() + "\n"
                    + "Longitude: " + address.getAddress_lng();
            mItemAddressBinding.textViewAddressName.setText(mapAddress);

        }
    }
}
