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

package sti.com.livefeedback.ui.storedetails;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.data.model.api.storedetails.*;
import sti.com.livefeedback.databinding.ActivityStoreDetailsBinding;
import sti.com.livefeedback.ui.base.BaseActivity;
import sti.com.livefeedback.ui.cart.CartActivity;
import sti.com.livefeedback.ui.imagegallery.ImageGalleryActivity;
import sti.com.livefeedback.ui.imagegallery.SlidingImageAdapter;
import sti.com.livefeedback.ui.main.MainActivity;
import sti.com.livefeedback.ui.storedetails.branches.BranchesLinkAdapter;
import sti.com.livefeedback.ui.storedetails.category.CategoryAdapter;
import sti.com.livefeedback.ui.storedetails.contacts.ContactsAdapter;
import sti.com.livefeedback.ui.storedetails.dynamiccontent.DynamicContentAdapter;
import sti.com.livefeedback.ui.storedetails.gallery.GalleryAdapter;
import sti.com.livefeedback.ui.storedetails.highlight.HighlightAdapter;
import sti.com.livefeedback.ui.storedetails.offers.OffersAdapter;
import sti.com.livefeedback.ui.storedetails.ordernow.OrderNowAdapter;
import sti.com.livefeedback.ui.storedetails.product.ProductAdapter;
import sti.com.livefeedback.ui.storedetails.services.ServiceFullAdapter;
import sti.com.livefeedback.ui.storedetails.testimonial.TestimonialAdapter;
import sti.com.livefeedback.ui.storedetails.services.ServiceAdapter;
import sti.com.livefeedback.ui.storedetails.social.SocialLinkAdapter;
import sti.com.livefeedback.ui.storedetails.timing.TimingAdapter;
import sti.com.livefeedback.utils.BindingUtils;
import sti.com.livefeedback.utils.CommonUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class StoreDetailsActivity extends BaseActivity<ActivityStoreDetailsBinding, StoreDetailsViewModel> implements StoreDetailsNavigator , ProductAdapter.OnItemClickProduct ,CategoryAdapter.OnItemClickCategory, View.OnClickListener
, ServiceAdapter.OnItemClickService {

    @Inject
    ViewModelProviderFactory factory;
    private StoreDetailsViewModel mStoreDetailsViewModel;
    private ActivityStoreDetailsBinding mActivityStoreDetailsBinding;
    private String TAG = "STORE DETAILS ACTIVITY";
    private int cartCount=0;
    Carts carts;
    List<Product> cartsList;
    ProductAdapter productAdapter;
    private BottomSheetBehavior sheetBehavior;
    private RecyclerView bottomSheetRecyclerview;
    private TextView tvBottomSheetText;
    private LinearLayoutManager llmServices;

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.


    public static Intent newIntent(Context context) {
        return new Intent(context, StoreDetailsActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_store_details;
    }

    @Override
    public StoreDetailsViewModel getViewModel() {
        mStoreDetailsViewModel = ViewModelProviders.of(this,factory).get(StoreDetailsViewModel.class);
        return mStoreDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(this, "Login Error "+ CommonUtils.networkError(TAG,throwable), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login() {

    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(StoreDetailsActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openImageGallery() {

    }

    @Override
    public void setStoreInformation(StoreDetailsResponse storeDetailResponse) {
        Log.e("data",storeDetailResponse.toString());

        if(storeDetailResponse.getPage().getPage().getHeaderImage()!=null) {
            BindingUtils.setImageUrl(mActivityStoreDetailsBinding.detailImage, storeDetailResponse.getPage().getPage().getHeaderImage());
        }else {
            mActivityStoreDetailsBinding.appbar.setVisibility(View.GONE);
        }

        if(storeDetailResponse.getPage().getLogo()!=null) {
            BindingUtils.setImageUrl(mActivityStoreDetailsBinding.storeLogo, storeDetailResponse.getPage().getLogo());
        }

        mActivityStoreDetailsBinding.storeName.setText(storeDetailResponse.getPage().getStoreName());
        mActivityStoreDetailsBinding.storeTag.setText(storeDetailResponse.getPage().getTagLine());
        mActivityStoreDetailsBinding.storeAddress.setText(storeDetailResponse.getPage().getAddress());

        if(storeDetailResponse.getPage().getProducts()!=null && storeDetailResponse.getPage().getProducts().size()>0) {
            mActivityStoreDetailsBinding.cvProducts.setVisibility(View.VISIBLE);
            mActivityStoreDetailsBinding.productRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            productAdapter = new ProductAdapter(this, storeDetailResponse.getPage().getProducts());
            mActivityStoreDetailsBinding.productRecyclerview.setAdapter(productAdapter);
        }

        if(storeDetailResponse.getPage().getServices()!=null && storeDetailResponse.getPage().getServices().size()>0) {
            mActivityStoreDetailsBinding.cvServices.setVisibility(View.VISIBLE);
            llmServices=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
            mActivityStoreDetailsBinding.serviceRecyclerview.setLayoutManager(llmServices);
            ServiceAdapter serviceAdapter = new ServiceAdapter(this, storeDetailResponse.getPage().getServices());
            mActivityStoreDetailsBinding.serviceRecyclerview.setAdapter(serviceAdapter);
        }

        if(storeDetailResponse.getPage().getPage().getPhotos()!=null && storeDetailResponse.getPage().getPage().getPhotos().size()>0) {
            mActivityStoreDetailsBinding.cvGallery.setVisibility(View.VISIBLE);
            mActivityStoreDetailsBinding.galleryRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
            GalleryAdapter galleryAdapter = new GalleryAdapter(this, storeDetailResponse.getPage().getPage().getPhotos());
            mActivityStoreDetailsBinding.galleryRecyclerview.setAdapter(galleryAdapter);
        }

        if(storeDetailResponse.getPage().getPage().getBanners()!=null && storeDetailResponse.getPage().getPage().getBanners().size()>0) {
            mActivityStoreDetailsBinding.cvBanner.setVisibility(View.VISIBLE);
            SlidingImageAdapter slidingImageAdapter = new SlidingImageAdapter(this, storeDetailResponse.getPage().getPage().getBanners());
            mActivityStoreDetailsBinding.pager.setAdapter(slidingImageAdapter);
        }

        mActivityStoreDetailsBinding.galleryViewImage.setOnClickListener(v -> openGallery(storeDetailResponse));
        mActivityStoreDetailsBinding.pager.setCurrentItem(0, true);

        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage ==  storeDetailResponse.getPage().getPage().getBanners().size()) {
                currentPage = 0;
            }
            mActivityStoreDetailsBinding.pager.setCurrentItem(currentPage++, true);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        mActivityStoreDetailsBinding.pager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });


        if(storeDetailResponse.getPage().getTestimonials()!=null && storeDetailResponse.getPage().getTestimonials().size()>0) {
            mActivityStoreDetailsBinding.cvTestominial.setVisibility(View.VISIBLE);
            mActivityStoreDetailsBinding.reviewRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            TestimonialAdapter reviewAdapter = new TestimonialAdapter(this, storeDetailResponse.getPage().getTestimonials());
            mActivityStoreDetailsBinding.reviewRecyclerview.setAdapter(reviewAdapter);
        }

        if(storeDetailResponse.getPage().getTimings()!=null && storeDetailResponse.getPage().getTimings().size()>0) {
            mActivityStoreDetailsBinding.cvTiming.setVisibility(View.VISIBLE);
            mActivityStoreDetailsBinding.timingRecyclerview.setLayoutManager(new LinearLayoutManager(this));
            TimingAdapter timingAdapter = new TimingAdapter(this, storeDetailResponse.getPage().getTimings());
            mActivityStoreDetailsBinding.timingRecyclerview.setAdapter(timingAdapter);
        }


        mActivityStoreDetailsBinding.categoryRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayout.HORIZONTAL,false));
        CategoryAdapter categoryAdapter=new CategoryAdapter(this,storeDetailResponse.getCategories());
        mActivityStoreDetailsBinding.categoryRecyclerview.setAdapter(categoryAdapter);

        if(storeDetailResponse.getOfferedProducts()!=null && storeDetailResponse.getOfferedProducts().size()>0) {
            mActivityStoreDetailsBinding.cvOffers.setVisibility(View.VISIBLE);
            mActivityStoreDetailsBinding.offersRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
            OffersAdapter offersAdapter = new OffersAdapter(this, storeDetailResponse.getOfferedProducts());
            mActivityStoreDetailsBinding.offersRecyclerview.setAdapter(offersAdapter);
        }

        if(storeDetailResponse.getPage().getPage().getHighlights()!=null && storeDetailResponse.getPage().getPage().getHighlights().size()>0) {
            mActivityStoreDetailsBinding.cvHighlight.setVisibility(View.VISIBLE);
            mActivityStoreDetailsBinding.highlightRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
            HighlightAdapter highlightAdapter = new HighlightAdapter(this, storeDetailResponse.getPage().getPage().getHighlights());
            mActivityStoreDetailsBinding.highlightRecyclerview.setAdapter(highlightAdapter);
        }

        for(int i=0;i<storeDetailResponse.getDynamicContents().size();i++){
            for(int j=0;j<storeDetailResponse.getDynamicContents().get(i).getValues().size();j++){
                //for(int k=0;k<storeDetailResponse.getDynamicContents().get(i).getValues().get(j).getValues().size();k++){
                    View view= LayoutInflater.from(this).inflate(R.layout.item_dynamics_content,null);
                    RecyclerView recyclerView=view.findViewById(R.id.dynamic_recyclerview);
                    TextView title=view.findViewById(R.id.dynamic_title);
                    title.setText(storeDetailResponse.getDynamicContents().get(i).getValues().get(j).getTitle());
                    mActivityStoreDetailsBinding.llDynamic.setVisibility(View.VISIBLE);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    DynamicContentAdapter dynamicContentAdapter=new DynamicContentAdapter(this,storeDetailResponse.getDynamicContents().get(i).getValues().get(j).getValues());
                    recyclerView.setAdapter(dynamicContentAdapter);
                    mActivityStoreDetailsBinding.llDynamic.addView(view);
                //}
            }
        }
        mActivityStoreDetailsBinding.llExtra.setVisibility(View.VISIBLE);

        setNavigationValues(storeDetailResponse.getPage().getNavigation());

    }

    private void setNavigationValues(Navigation navigation) {
        mActivityStoreDetailsBinding.tvServices.setText(navigation.getServices());
        mActivityStoreDetailsBinding.tvGallery.setText(navigation.getGallery());
        mActivityStoreDetailsBinding.tvProduct.setText(navigation.getCategoryNav());
        mActivityStoreDetailsBinding.tvOffers.setText(navigation.getOffers());
        mActivityStoreDetailsBinding.tvTestominial.setText(navigation.getTestimonial());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityStoreDetailsBinding = getViewDataBinding();
        mStoreDetailsViewModel.setNavigator(this);

        carts=new Carts();
        cartsList=new ArrayList<>();
        getViewModel().fetchStoreDetails(getIntent().getStringExtra("storeId"));
        mActivityStoreDetailsBinding.cartCount.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cartIcon.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), CartActivity.class);
            carts.setCarts(cartsList);
            Gson gson = new Gson();
            String mcartList = gson.toJson(this.carts);
            intent.putExtra("cart", mcartList);
            startActivity(intent);
        });

        hideAllViews();

        initBottomSheet();

        horizontalArrow();

    }

    private void horizontalArrow() {
        mActivityStoreDetailsBinding.imgLeftScroll.setOnClickListener(v -> {
            if (llmServices.findFirstVisibleItemPosition() > 0) {
                mActivityStoreDetailsBinding.serviceRecyclerview.smoothScrollToPosition(llmServices.findFirstVisibleItemPosition() - 1);
            } else {
                mActivityStoreDetailsBinding.serviceRecyclerview.smoothScrollToPosition(0);
            }
        });

        mActivityStoreDetailsBinding.imgRightScroll.setOnClickListener(v -> mActivityStoreDetailsBinding.serviceRecyclerview.smoothScrollToPosition(llmServices.findLastVisibleItemPosition() + 1));
    }

    private void initBottomSheet() {
        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        llBottomSheet.setOnClickListener(v -> {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        ImageView imgClose=findViewById(R.id.imgClose);
        imgClose.setOnClickListener(v -> sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN));
        bottomSheetRecyclerview=findViewById(R.id.bottom_sheet_recyclerview);
        tvBottomSheetText=findViewById(R.id.tvBottomSheet);


        mActivityStoreDetailsBinding.llCall.setOnClickListener(this);
        mActivityStoreDetailsBinding.llOrderNow.setOnClickListener(this);
        mActivityStoreDetailsBinding.llBranches.setOnClickListener(this);
        mActivityStoreDetailsBinding.llSocial.setOnClickListener(this);
        mActivityStoreDetailsBinding.navBackBtn.setOnClickListener(this);
    }

    private void hideAllViews() {
        mActivityStoreDetailsBinding.cvTiming.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvBanner.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvOffers.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvProducts.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvServices.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvGallery.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvHighlight.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.cvTestominial.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.llDynamic.setVisibility(View.GONE);
        mActivityStoreDetailsBinding.llExtra.setVisibility(View.GONE);
    }

    private void openGallery(StoreDetailsResponse storeDetailResponse){
        Intent intent = new Intent(getBaseContext(), ImageGalleryActivity.class);
        intent.putExtra("images", (Serializable) storeDetailResponse.getPage().getPage().getPhotos());
        startActivity(intent);
    }


    @Override
    public void addedCart(Product product, View view) {
        cartsList.add(product);
        mActivityStoreDetailsBinding.cartCount.setVisibility(View.VISIBLE);
        cartCount=cartCount+1;
        mActivityStoreDetailsBinding.cartCount.setText(cartCount+"");
    }

    @Override
    public void deleteCart(Product product, View view) {
        cartsList.remove(product);
        mActivityStoreDetailsBinding.cartCount.setVisibility(View.VISIBLE);
        cartCount=cartCount-1;
        mActivityStoreDetailsBinding.cartCount.setText(cartCount+"");
    }


    private Bitmap createBitmapFromLayout(View tv) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        tv.measure(spec, spec);
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(tv.getMeasuredWidth(), tv.getMeasuredWidth(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate((-tv.getScrollX()), (-tv.getScrollY()));
        tv.draw(c);
        return b;
    }

    private void animateView(View view, Bitmap b) {

        int p1[] = new int[2];
        int p2[] = new int[2];
        view.getLocationInWindow(p1);
        mActivityStoreDetailsBinding.cartCount.getLocationInWindow(p2);

        mActivityStoreDetailsBinding.dummyImage.setImageBitmap(b);
        mActivityStoreDetailsBinding.dummyImage.setVisibility(View.VISIBLE);
        AnimatorSet animSetXY = new AnimatorSet();
        ObjectAnimator y = ObjectAnimator.ofFloat(mActivityStoreDetailsBinding.dummyImage, "translationY", p1[1], p2[1] - p1[1]);
        ObjectAnimator x = ObjectAnimator.ofFloat(mActivityStoreDetailsBinding.dummyImage, "translationX",p1[0], p2[0] - p1[0]);
        ObjectAnimator sy = ObjectAnimator.ofFloat(mActivityStoreDetailsBinding.dummyImage, "scaleY", 0.8f, 0.1f);
        ObjectAnimator sx = ObjectAnimator.ofFloat(mActivityStoreDetailsBinding.dummyImage, "scaleX", 0.8f, 0.1f);
        animSetXY.playTogether(x, y, sx, sy);
        animSetXY.setDuration(650);
        animSetXY.start();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true);
            PathInterpolator pathInterpolator = new PathInterpolator(path);
        }
    }


    @Override
    protected void onDestroy() {
        if(timer!=null) {
            timer.cancel();
        }
        super.onDestroy();
    }
    @Override
    public void categoryChanged(String category) {
        productAdapter.getFilter().filter(category);
    }


    private void test() throws IOException {
        String url = "";

        Document doc = Jsoup.connect(url).get();
        Elements link= doc.select("a[href]");

        link.get(0).attr("href");
        link.get(0).text();

        Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.llCall:
                tvBottomSheetText.setText("Contacts");
                bottomSheetRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
                ContactsAdapter contactsAdapter=new ContactsAdapter(this,mStoreDetailsViewModel.getStoreListLiveData().getValue().getPage().getContactNumbers());
                bottomSheetRecyclerview.setAdapter(contactsAdapter);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llOrderNow:
                tvBottomSheetText.setText("Place Order");
                bottomSheetRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
                OrderNowAdapter orderNowAdapter=new OrderNowAdapter(this,mStoreDetailsViewModel.getStoreListLiveData().getValue().getPage().getPage().getThiredrdPartyBookings());
                bottomSheetRecyclerview.setAdapter(orderNowAdapter);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llBranches:
                tvBottomSheetText.setText("Branches");
                bottomSheetRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
                BranchesLinkAdapter branchesLinkAdapter=new BranchesLinkAdapter(this,mStoreDetailsViewModel.getStoreListLiveData().getValue().getPage().getBranches());
                bottomSheetRecyclerview.setAdapter(branchesLinkAdapter);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.llSocial:
                tvBottomSheetText.setText("Social Links");
                bottomSheetRecyclerview.setLayoutManager(new GridLayoutManager(this,2));
                SocialLinkAdapter socialLinkAdapter=new SocialLinkAdapter(this,mStoreDetailsViewModel.getStoreListLiveData().getValue().getPage().getPage().getSocialLinks());
                bottomSheetRecyclerview.setAdapter(socialLinkAdapter);
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.navBackBtn:
                onBackPressed();
                break;



        }
    }

    @Override
    public void serviceSelected(Service service) {
        tvBottomSheetText.setText(service.getName());
        bottomSheetRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        ServiceFullAdapter serviceFullAdapter=new ServiceFullAdapter(this,service);
        bottomSheetRecyclerview.setAdapter(serviceFullAdapter);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
}
