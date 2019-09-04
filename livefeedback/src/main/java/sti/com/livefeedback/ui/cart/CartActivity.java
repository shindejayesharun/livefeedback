package sti.com.livefeedback.ui.cart;

import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Carts;
import sti.com.livefeedback.data.model.api.storedetails.Product;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    TextView tvPrice,tvTotalOffer,tvTotalPrice;
    Carts carts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Gson gson = new Gson();
        Carts carts = gson.fromJson(getIntent().getStringExtra("cart"), Carts.class);
        Log.e("sort list",carts.toString());
        init();
        recyclerView=findViewById(R.id.cartRecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter=new CartAdapter(this,carts.getCarts());
        recyclerView.setAdapter(cartAdapter);

        if(carts.getCarts()!=null) {
            //setPriceValues();
        }

    }

    private void setPriceValues() {
        double price =0.0;
        for(int i=0;i<carts.getCarts().size();i++){
            //price= price+Double.parseDouble(carts.getCarts().get(i).getPrice());
        }
        tvPrice.setText(""+price);
    }

    private void init() {
        tvPrice=findViewById(R.id.price);
        tvTotalOffer=findViewById(R.id.totaloffer);
        tvTotalPrice=findViewById(R.id.totalprice);
    }
}
