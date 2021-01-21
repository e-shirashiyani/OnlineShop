package com.example.onlineshop.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;



import com.example.onlineshop.data.model.MapAddress;
import com.example.onlineshop.data.room.CartDatabase;
import com.example.onlineshop.data.room.CartDatabaseDAO;

import java.util.List;

public class AddressDBRepository implements IAddressRepository {

    private static AddressDBRepository sInstance;

    private CartDatabaseDAO mCartDAO;
    private Context mContext;
    private MutableLiveData<List<MapAddress>> mListMutableLiveData;

    public static AddressDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new AddressDBRepository(context);

        return sInstance;
    }

    private AddressDBRepository(Context context) {
        mContext = context.getApplicationContext();
        CartDatabase cartDatabase = Room.databaseBuilder(mContext,
                CartDatabase.class,
                "cart.db")
                .allowMainThreadQueries()
                .build();

        mCartDAO = cartDatabase.getCartDatabaseDAO();
        mListMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<MapAddress>> getListMutableLiveData() {
        return mListMutableLiveData;
    }

    public void setListMutableLiveData(MutableLiveData<List<MapAddress>> listMutableLiveData) {
        mListMutableLiveData = listMutableLiveData;
    }

    @Override
    public void updateAddress(MapAddress mapAddress) {
        mCartDAO.updateAddress(mapAddress);
    }

    @Override
    public void insertAddress(MapAddress mapAddress) {
        mCartDAO.insertAddress(mapAddress);
    }

    @Override
    public void insertAddresses(List<MapAddress> mapAddresses) {
        mCartDAO.insertAddresses(mapAddresses);
    }

    @Override
    public void deleteAddress(MapAddress mapAddress) {
        mCartDAO.deleteAddress(mapAddress);
    }

    @Override
    public List<MapAddress> getMapAddresses() {
        return mCartDAO.getAddresses();
    }

    @Override
    public MapAddress getAddress() {
        return mCartDAO.getAddress();
    }

    @Override
    public MapAddress getAddressWithId(long addressId) {
        return mCartDAO.getAddressWithId(addressId);
    }
}
