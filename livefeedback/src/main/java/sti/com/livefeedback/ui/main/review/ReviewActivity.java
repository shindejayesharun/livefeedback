package sti.com.livefeedback.ui.main.review;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Review;
import sti.com.livefeedback.data.model.api.storedetails.Reviews;
import sti.com.livefeedback.ui.main.stores.reviews.ReviewSingleAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

  RecyclerView reviewRecyclerView;
  List<Review> reviewsList = new ArrayList<>();
  ReviewAdapter reviewAdapter;
  Reviews reviews;
  ImageButton imgBack;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_review);
   /* Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);*/


   imgBack=findViewById(R.id.navBackBtn);
    Gson gson = new Gson();
    String  data=getIntent().getStringExtra("review");
    try {
      JSONArray jsonArray=new JSONArray(data);
      JSONObject jsonObject=new JSONObject();
      jsonObject.put("reviews",jsonArray);
      reviews= gson.fromJson(jsonObject.toString(), Reviews.class);
      Log.e("Data",data);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    reviewRecyclerView=findViewById(R.id.review_recyclerview);
    reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    reviewAdapter=new ReviewAdapter(this,reviews.getReviews());
    reviewRecyclerView.setAdapter(reviewAdapter);
    imgBack.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    int id=v.getId();
    switch (id){
      case R.id.navBackBtn:
        onBackPressed();
        break;
    }
  }
}
