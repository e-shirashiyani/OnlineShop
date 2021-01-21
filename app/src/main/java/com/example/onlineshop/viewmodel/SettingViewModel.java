package com.example.onlineshop.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.R;
import com.example.onlineshop.adapter.AddressAdapter;
import com.example.onlineshop.data.model.MapAddress;
import com.example.onlineshop.data.repository.AddressDBRepository;
import com.example.onlineshop.data.repository.OnlineStoreRepository;
import com.example.onlineshop.databinding.FragmentNotificationBinding;
import com.example.onlineshop.utilities.QueryPreferences;
import com.example.onlineshop.view.activity.LocationActivity;
import com.example.onlineshop.view.activity.MapActivity;
import com.example.onlineshop.view.activity.NotificationActivity;
import com.example.onlineshop.view.fragmenet.MapFragment;
import com.example.onlineshop.worker.PollWorker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;


public class SettingViewModel extends AndroidViewModel {
    private AddressDBRepository mRepository;
    private Context mContext;
    private FragmentNotificationBinding mNotificationBinding;
    private MutableLiveData<Location> mMyLocation = new MutableLiveData<>();
    private MutableLiveData<List<MapAddress>> mLiveDataAddress = new MutableLiveData<>();
    private FusedLocationProviderClient mFusedLocationClient;
    private AddressAdapter mAddressAdapter;


    public void setNotificationBinding(FragmentNotificationBinding notificationBinding) {
        mNotificationBinding = notificationBinding;
    }

    public SettingViewModel(@NonNull Application application) {
        super(application);
        mRepository = AddressDBRepository.getInstance(application);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplication());
        mLiveDataAddress = mRepository.getListMutableLiveData();

    }

    public MutableLiveData<List<MapAddress>> getLiveDataAddress() {
        return mRepository.getListMutableLiveData();
    }

    public void setLiveDataAddress(MutableLiveData<List<MapAddress>> mutableLiveData) {
        mRepository.setListMutableLiveData(mutableLiveData);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void onClickNotificationItem() {
        mContext.startActivity(NotificationActivity.newIntent(mContext));
    }

    public void onClickLocationItem() {
        mContext.startActivity(LocationActivity.newIntent(mContext));
    }

    public void onClickAddLocationItem() {
        mContext.startActivity(MapActivity.newIntent(mContext));
    }

    public void togglePolling() {
        boolean isOn = PollWorker.isWorkEnqueued(getApplication());
        long time = QueryPreferences.getNotificationTime(getApplication());
        PollWorker.enqueueWork(getApplication(), !isOn, time);
    }

    public boolean isTaskScheduled() {
        return PollWorker.isWorkEnqueued(getApplication());
    }

    public void onClickNotificationSwitch() {
        togglePolling();
        if (isTaskScheduled()) {
            mNotificationBinding.switchNotification.setText(R.string.on);
            mNotificationBinding.switchNotification.setChecked(true);
            mNotificationBinding.radioGroupNotification.setVisibility(View.VISIBLE);
            mNotificationBinding.buttonSaveNotification.setVisibility(View.VISIBLE);
        } else {
            mNotificationBinding.switchNotification.setText(R.string.off);
            mNotificationBinding.switchNotification.setChecked(false);
            mNotificationBinding.radioGroupNotification.setVisibility(View.GONE);
            mNotificationBinding.buttonSaveNotification.setVisibility(View.GONE);
        }
    }

    public void onClickNotificationRadioButtonTime() {
        mNotificationBinding.editTextTime.setVisibility(View.GONE);
    }

    public void onClickNotificationTime() {
        mNotificationBinding.editTextTime.setVisibility(View.VISIBLE);
    }

    public void onClickNotificationSave() {
        if (mNotificationBinding.editTextTime.getVisibility() == View.VISIBLE) {
            String userTime = mNotificationBinding.editTextTime.getText().toString();
            if (userTime.equals("")) {
                Toast.makeText(mContext, "Enter Time for show notification", Toast.LENGTH_SHORT).show();
            } else {
                QueryPreferences.setNotificationTime(getApplication(), Long.parseLong(userTime));
                Toast.makeText(mContext, "Notification Time is:  " + userTime, Toast.LENGTH_SHORT).show();
            }
        } else {
            if (mNotificationBinding.radioButton3.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 3, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(), 3);
            } else if (mNotificationBinding.radioButton5.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 5, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(), 5);
            } else if (mNotificationBinding.radioButton8.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 8, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(), 8);
            } else if (mNotificationBinding.radioButton12.isChecked()) {
                Toast.makeText(mContext, "Notification Time is:  " + 12, Toast.LENGTH_SHORT).show();
                QueryPreferences.setNotificationTime(getApplication(), 12);
            }
        }
    }

    public long getNotificationTime() {
        return QueryPreferences.getNotificationTime(getApplication());
    }

    public void setNotificationTime(long notificationTime) {
        QueryPreferences.setNotificationTime(getApplication(), notificationTime);
    }

    public LiveData<Location> getMyLocation() {
        return mMyLocation;
    }

    @SuppressLint("MissingPermission")
    public void requestLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);
                Log.d(MapFragment.TAG,
                        "lat: " + location.getLatitude() + ", lon: " + location.getLongitude());

                mMyLocation.setValue(location);
            }
        };

        mFusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    public void insertAddress(MapAddress mapAddress) {
        mRepository.insertAddress(mapAddress);
    }

    public MapAddress getSelectedAddress() {
        return mRepository.getAddress();
    }

    public void updateAddress(MapAddress mapAddress) {
        mRepository.updateAddress(mapAddress);
    }

    public List<MapAddress> getAddresses() {
        return mRepository.getMapAddresses();
    }

    public MapAddress getAddressWithId(long addressId) {
        return mRepository.getAddressWithId(addressId);
    }

    public AddressAdapter getAddressAdapter() {
        return mAddressAdapter;
    }

    public void setAddressAdapter(AddressAdapter addressAdapter) {
        mAddressAdapter = addressAdapter;
    }

    public void onClickSelectedAddress(long addressId) {
        MapAddress prev_address = getSelectedAddress();
        if (prev_address != null) {
            prev_address.setSelected_address(0);
            updateAddress(prev_address);
        }
        MapAddress new_address = getAddressWithId(addressId);
        new_address.setSelected_address(1);
        updateAddress(new_address);
        List<MapAddress> mapAddressList = getAddresses();

        mLiveDataAddress.setValue(mapAddressList);
        mRepository.setListMutableLiveData(mLiveDataAddress);


    }

    public void deleteLocationAddress(MapAddress mapAddress){
        mRepository.deleteAddress(mapAddress);
    }
}

