/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package sti.com.livefeedback.ui.main.stores;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.data.model.api.LocationLatLong;
import sti.com.livefeedback.data.model.api.StoreResponse;
import sti.com.livefeedback.databinding.FragmentStoreBinding;
import sti.com.livefeedback.ui.base.BaseFragment;
import sti.com.livefeedback.ui.main.MainActivity;
import sti.com.livefeedback.ui.main.maincategory.MainCategoryAdapter;
import sti.com.livefeedback.utils.locationTracker.GPSTracker;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class StoreFragment extends BaseFragment<FragmentStoreBinding, StoreViewModel>
        implements StoreNavigator, StoreAdapter.StoreAdapterListener{

    @Inject
    StoreAdapter mBlogAdapter;
    FragmentStoreBinding mFragmentBlogBinding;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewModelProviderFactory factory;
    private StoreViewModel mBlogViewModel;
    StoreListAdapter storeListAdapter;




    public static StoreFragment newInstance() {
        Bundle args = new Bundle();
        StoreFragment fragment = new StoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static StoreFragment getInstance()
    {
        return new StoreFragment();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_store;
    }

    @Override
    public StoreViewModel getViewModel() {
        mBlogViewModel = ViewModelProviders.of(this, factory).get(StoreViewModel.class);
        return mBlogViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBlogViewModel.setNavigator(this);
        mBlogAdapter.setListener(this);
    }

    @Override
    public void onRetryClick() {
        mBlogViewModel.fetchStores(fetchLocation());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentBlogBinding = getViewDataBinding();

        getViewModel().fetchStores(fetchLocation());

    }

    @Override
    public void updateBlog(List<StoreResponse.Blog> blogList) {
        mBlogAdapter.addItems(blogList);
    }

    @Override
    public void setCategory() {
        LiveData<List<StoreResponse.Blog>> storeData=  getViewModel().getStoreListLiveData();
        List<String> categories = new ArrayList<>();
        categories.add("All");
        for(int i=0;i<storeData.getValue().size();i++){
            categories.add(storeData.getValue().get(i).getCategory());
        }
        Log.e("category List",categories.toString());
        mFragmentBlogBinding.categoryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        MainCategoryAdapter categoryAdapter=new MainCategoryAdapter(this.getContext(),categories);
        mFragmentBlogBinding.categoryRecyclerview.setAdapter(categoryAdapter);
        mCallback.passStoreDetailsToMap(storeData);

        mFragmentBlogBinding.blogRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        storeListAdapter=new StoreListAdapter(getContext(),storeData.getValue());
        mFragmentBlogBinding.blogRecyclerView.setAdapter(storeListAdapter);
    }




    private String fetchLocation() {
        LocationLatLong locationLatLong=getLocation();
        Log.d("Location",locationLatLong.toString());

        HashMap<String,String> requestParam=new HashMap<>();
        requestParam.put("geoLocation",locationLatLong.getLatitude()+","+locationLatLong.getLongitude());

        Log.e("Location ",requestParam.toString());
        return locationLatLong.getLongitude()+","+locationLatLong.getLatitude();
    }
    private LocationLatLong getLocation(){
        GPSTracker gps = new GPSTracker(getContext());

        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            return new LocationLatLong(latitude,longitude);
        }else{
            //gps.showSettingsAlert();
        }
        return new LocationLatLong();
    }

    public void categoryChanged(String category)
    {
        getViewModel().setIsLoading(true);
        if(category.equals("All")){
            storeListAdapter.getFilter().filter("");
        }else {
            storeListAdapter.getFilter().filter(category);
        }
        getViewModel().setIsLoading(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (MapFragmentCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement TextClicked");
        }
    }
    public interface MapFragmentCallback {
        void passStoreDetailsToMap(LiveData<List<StoreResponse.Blog>> storeData);
    }
    private MapFragmentCallback mCallback;
}
