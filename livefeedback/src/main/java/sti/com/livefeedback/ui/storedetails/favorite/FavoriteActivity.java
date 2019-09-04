package sti.com.livefeedback.ui.storedetails.favorite;

import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;

import static sti.com.livefeedback.utils.AppLogger.init;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

  RecyclerView favoriteRecyclerview;
  ImageButton imgBack;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_favorite);


    init();

    favoriteRecyclerview.setLayoutManager(new LinearLayoutManager(this));

  }

  private void init() {
    imgBack=findViewById(R.id.navBackBtn);
    favoriteRecyclerview=findViewById(R.id.favoriteRecyclerview);

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
