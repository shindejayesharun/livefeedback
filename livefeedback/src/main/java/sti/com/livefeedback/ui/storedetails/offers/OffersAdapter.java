package sti.com.livefeedback.ui.storedetails.offers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.OfferedProduct;
import sti.com.livefeedback.data.model.api.storedetails.Product;
import sti.com.livefeedback.ui.productdetails.ProductDetailsActivity;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    Context context;
    List<OfferedProduct> offeredProducts;
    public OffersAdapter(Context context, List<OfferedProduct> offeredProducts) {
        this.context = context;
        this.offeredProducts=offeredProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_offers_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OfferedProduct offeredProduct=offeredProducts.get(position);
        BindingUtils.setImageUrl(holder.imgLogo,offeredProduct.getImages().get(0));
        holder.tvOffer.setText(offeredProduct.getOffer());

        holder.itemView.setOnClickListener(v -> {
            Intent i=new Intent(context, ProductDetailsActivity.class);
            Gson gson = new Gson();
            String mProduct = gson.toJson(offeredProduct);
            i.putExtra("product",mProduct);
            context.startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return offeredProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvOffer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.offered_image);
            tvOffer=itemView.findViewById(R.id.tvOffer);
        }
    }
}
