package sti.com.livefeedback.ui.imagegallery;

import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import sti.com.livefeedback.R;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private List<String> images ;
    private RecyclerView recyclerViewImageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        init();
    }

    private void init() {

        mPager = (ViewPager) findViewById(R.id.pager);

        images = (List<String>) getIntent().getSerializableExtra("images");


        mPager.setAdapter(new SlidingImageAdapter(ImageGalleryActivity.this,images));

        recyclerViewImageIndicator=findViewById(R.id.image_indicator_recyclerview);
        recyclerViewImageIndicator.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        ImageIndicatorAdapter imageIndicatorAdapter=new ImageIndicatorAdapter(this,images);
        recyclerViewImageIndicator.setAdapter(imageIndicatorAdapter);


    }

}
