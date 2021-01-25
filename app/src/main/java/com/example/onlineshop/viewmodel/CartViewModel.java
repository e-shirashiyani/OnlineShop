package com.example.onlineshop.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.onlineshop.adapter.OrderedProductAdapter;
import com.example.onlineshop.data.model.BillingAddress;
import com.example.onlineshop.data.model.Cart;
import com.example.onlineshop.data.model.Comment;
import com.example.onlineshop.data.model.Coupons;
import com.example.onlineshop.data.model.Customer;
import com.example.onlineshop.data.model.Product;
import com.example.onlineshop.data.model.ShippingAddress;
import com.example.onlineshop.data.repository.CartDBRepository;
import com.example.onlineshop.data.repository.OnlineStoreRepository;
import com.example.onlineshop.databinding.FragmentCartBinding;
import com.example.onlineshop.databinding.FragmentEditCommentBinding;
import com.example.onlineshop.view.activity.BuyActivity;
import com.example.onlineshop.view.activity.CartActivity;
import com.example.onlineshop.view.activity.ProductDetailActivity;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class CartViewModel extends AndroidViewModel {



    private CartDBRepository mCartDBRepository;
    private OnlineStoreRepository mStoreRepository;
    private LiveData<Customer> mCustomerLiveData;
    private List<Product> mProductList;
    private OrderedProductAdapter mOrderedProductAdapter;
    private FragmentCartBinding mFragmentCartBinding;
    private FragmentEditCommentBinding mEditCommentBinding;

    public void setEditCommentBinding(FragmentEditCommentBinding editCommentBinding) {
        mEditCommentBinding = editCommentBinding;
    }

    private Context mContext;
    private MutableLiveData<Integer> mLiveDataRate = new MutableLiveData<>();


    public CartViewModel(@NonNull Application application) {
        super(application);
        mCartDBRepository = CartDBRepository.getInstance(application);
        mStoreRepository = new OnlineStoreRepository();

    }

    public void insertToCart(Cart cart) {
        mCartDBRepository.insertCart(cart);
    }

    public void getOrderedProduct() {

        List<Cart> carts = mCartDBRepository.getCarts();
        for (int i = 0; i < carts.size(); i++) {
            mStoreRepository.fetchProductItemAsync(carts.get(i).getProduct_id());
        }

    }

    public LiveData<Product> getLiveDateProduct() {
        return mStoreRepository.getProductLiveData();
    }

    public LiveData<Customer> getLiveDateCustomer() {
        return mStoreRepository.getCustomerLiveData();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<Product> getProductList() {
        return mProductList;
    }

    public void setProductList(List<Product> productList) {
        mProductList = productList;
    }

    public void onClickListItem(int productId) {
        mContext.startActivity(ProductDetailActivity.newIntent(mContext, productId));
    }

    public void onClickToGoToCart() {
        mContext.startActivity(CartActivity.newIntent(getApplication()));
    }

    public void onClickToBuy(int productId) {
        Cart cart = mCartDBRepository.getCart(productId);
        if (cart == null) {
            insertToCart(new Cart(productId,1));
        }else {
            int count = cart.getProduct_count() + 1;
            cart.setProduct_count(count);
            mCartDBRepository.updateCart(cart);
        }
        Toast.makeText(mContext, "add to cart", Toast.LENGTH_SHORT).show();

    }

    public void onClickToBuyAgain(int productId) {
        onClickToBuy(productId);
        mOrderedProductAdapter.notifyDataSetChanged();
        mFragmentCartBinding.totalPrice.setText(String.valueOf(getTotalPrice()));
    }

    public void onClickToDelete(int productId) {
        if (mCartDBRepository.getCart(productId).getProduct_count() == 1) {
            mCartDBRepository.deleteCart(mCartDBRepository.getCart(productId));
            for (int i = 0; i < mProductList.size(); i++) {
                if (mProductList.get(i).getId() == productId)
                    mProductList.remove(i);
            }
        }
        else {
            Cart updateCart = mCartDBRepository.getCart(productId);
            int count = updateCart.getProduct_count() - 1;
            updateCart.setProduct_count(count);
            mCartDBRepository.updateCart(updateCart);

        }
        if (mProductList.size() == 0){
            mFragmentCartBinding.recyclerCart.setVisibility(View.GONE);
            mFragmentCartBinding.layoutEmptyCart.setVisibility(View.VISIBLE);
        }
        mFragmentCartBinding.totalPrice.setText(String.valueOf(getTotalPrice()));
        mOrderedProductAdapter.notifyDataSetChanged();
    }

    public void setOrderedProductAdapter(OrderedProductAdapter orderedProductAdapter) {
        mOrderedProductAdapter = orderedProductAdapter;
    }

    public OrderedProductAdapter getOrderedProductAdapter() {
        return mOrderedProductAdapter;
    }

    public void setFragmentCartBinding(FragmentCartBinding fragmentCartBinding) {
        mFragmentCartBinding = fragmentCartBinding;
    }

    public Cart getCart(int productId){
        return mCartDBRepository.getCart(productId);
    }

    public int getTotalPrice (){
        int totalPrice = 0;
        for (int i = 0; i < mProductList.size(); i++) {
            int price = Integer.parseInt(mProductList.get(i).getPrice());
            int count = getCart(mProductList.get(i).getId()).getProduct_count();
            totalPrice += (price * count);
        }
        return totalPrice;
    }

    public void onClickContinueBuy(){
        if (mProductList.size()==0){
            Toast.makeText(mContext,"Your cart is Empty!",Toast.LENGTH_SHORT).show();
        }else {
            mContext.startActivity(BuyActivity.newIntent(mContext));
        }
    }

    public void onclickBuy () {
        Random random = new Random();
        BillingAddress[] billingAddresses = new BillingAddress[1];
        billingAddresses[0] = new BillingAddress("sana","nazari","maktab",
                "rodaki","andishe","kermanshah","west","6714844718",
                "iran","sana.nazari@gmail.com","9187287311");

        ShippingAddress[] shippingAddresses = new ShippingAddress[1];
        shippingAddresses[0] = new ShippingAddress("sana","nazari","maktab",
                "rodaki","andishe","kermanshah","west","6714844718",
                "iran");
        Customer customer = new Customer(random.nextInt(),new Date().toString(),
                "Sana.nazari@gmail.com","sana","nazari","sana.nazari",
                "1234",0,"",0,0,"",
                null,null);

        mStoreRepository.fetchCreateCustomerAsync(customer);
    }

    public void onClickAddComment(Comment comment){
        mStoreRepository.fetchAddCommentAsync(comment);
    }

    public void fetchOneComment(int commentId){
        mStoreRepository.fetchOneCommentAsync(commentId);
    }

    public void fetchPutComment(Comment comment){
        mStoreRepository.fetchPUTCommentAsync(comment);
    }

    public void fetchDeleteComment(int commentId){
        mStoreRepository.fetchDeleteCommentAsync(commentId);
    }

    public void onClickAddRate(int rate){
        mLiveDataRate.setValue(rate);
    }

    public void onClickEditComment(){
        mEditCommentBinding.name.setEnabled(true);
        mEditCommentBinding.email.setEnabled(true);
        mEditCommentBinding.comment.setEnabled(true);
        mEditCommentBinding.star1Edit.setEnabled(true);
        mEditCommentBinding.star2Edit.setEnabled(true);
        mEditCommentBinding.star3Edit.setEnabled(true);
        mEditCommentBinding.star4Edit.setEnabled(true);
        mEditCommentBinding.star5Edit.setEnabled(true);
    }

    public MutableLiveData<Integer> getLiveDataRate() {
        return mLiveDataRate;
    }

    public MutableLiveData<Comment> getLiveDataOneComment() {
        return mStoreRepository.getLiveDataOneComment();
    }

    public void fetchCoupons() {
        mStoreRepository.fetchCouponsAsync();
    }

    public LiveData<List<Coupons>> getLiveDataCoupons() {
        return mStoreRepository.getLiveDataCoupons();
    }
}

