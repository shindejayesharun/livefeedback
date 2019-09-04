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

package sti.com.livefeedback.ui.productdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.data.model.api.storedetails.Product;
import sti.com.livefeedback.data.model.api.storedetails.TabbedDetail;
import sti.com.livefeedback.databinding.ActivityProductDetailsBinding;
import sti.com.livefeedback.ui.base.BaseActivity;
import sti.com.livefeedback.ui.imagegallery.SlidingImageAdapter;
import sti.com.livefeedback.ui.productdetails.tabbleddetails.TabbedDetailsAdapter;
import sti.com.livefeedback.utils.CommonUtils;
import sti.com.livefeedback.utils.CustomPager;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class ProductDetailsActivity extends BaseActivity<ActivityProductDetailsBinding, ProductDetailsViewModel> implements ProductDetailsNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private ProductDetailsViewModel mStoreDetailsViewModel;
    private ActivityProductDetailsBinding mActivityStoreDetailsBinding;
    private String TAG = "STORE DETAILS ACTIVITY";

    public static Intent newIntent(Context context) {
        return new Intent(context, ProductDetailsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public ProductDetailsViewModel getViewModel() {
        mStoreDetailsViewModel = ViewModelProviders.of(this, factory).get(ProductDetailsViewModel.class);
        return mStoreDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(this, "Login Error " + CommonUtils.networkError(TAG, throwable), Toast.LENGTH_SHORT).show();
    }

    public void setProductInformation(Product product) {
        SlidingImageAdapter slidingImageAdapter = new SlidingImageAdapter(this, product.getImages());
        mActivityStoreDetailsBinding.pager.setAdapter(slidingImageAdapter);
        mActivityStoreDetailsBinding.productName.setText(product.getName());

        mActivityStoreDetailsBinding.tabbedDetailsRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        TabbedDetailsAdapter tabbedDetailsAdapter = new TabbedDetailsAdapter(this, product.getTabbedDetails());
        mActivityStoreDetailsBinding.tabbedDetailsRecyclerview.setAdapter(tabbedDetailsAdapter);
        createPriceOfferView(product);
        createKeyValueViews(product.getProperties());
        createTabView(product.getTabbedDetails());
    }

    private void createTabView(List<TabbedDetail> tabbedDetails) {
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        for (int i = 0; i < tabbedDetails.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(tabbedDetails.get(i).getLabel()));
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPager viewPager = findViewById(R.id.viewPager);

        viewPager.setAdapter(new TabbedPagerAdapter(this,tabbedDetails));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void createKeyValueViews(Object properties) {

        if (properties != null) {
            LinkedTreeMap<String, String> propertiesLinkedTreeMap = (LinkedTreeMap<String, String>) properties;
            Set<String> keys = propertiesLinkedTreeMap.keySet();
            for (String key : keys) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_product_key_value_view, null);
                TextView tvKey = view.findViewById(R.id.tv_key);
                TextView tvValue = view.findViewById(R.id.tv_desc);
                tvKey.setText(key);
                tvValue.setText(propertiesLinkedTreeMap.get(key));
                mActivityStoreDetailsBinding.llKeyValues.addView(view);
            }
        }
    }

    private void createPriceOfferView(Product product) {
        if(product.getPrice()!=null){
           addKeyValueView("Price",product.getPrice());
        }
        if(product.getOffer()!=null){
            addKeyValueView("Offer",product.getPrice());
        }
    }

    private void addKeyValueView(String label, String value) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_product_key_value_view, null);
        TextView tvKey = view.findViewById(R.id.tv_key);
        TextView tvValue = view.findViewById(R.id.tv_desc);
        tvKey.setText(label);
        tvValue.setText(value);
        mActivityStoreDetailsBinding.llKeyValues.addView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityStoreDetailsBinding = getViewDataBinding();
        mStoreDetailsViewModel.setNavigator(this);

        Gson gson = new Gson();
        Product product = gson.fromJson(getIntent().getStringExtra("product"), Product.class);
        setProductInformation(product);


    }
}
