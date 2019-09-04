package sti.com.livefeedback.ui.cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Product;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<Product> productList;

    public CartAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_cart_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=productList.get(position);
        if(product.getImages()!=null) {
            if(product.getImages().size()>0) {
                BindingUtils.setImageUrl(holder.imgLogo, product.getImages().get(0));
            }
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.totalPrice.setText(product.getPrice());
        holder.tvOffer.setText(product.getOffer());
        holder.cardView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        AtomicInteger qty= new AtomicInteger(Integer.parseInt(holder.qty.getText().toString()));
        AtomicInteger price= new AtomicInteger(Integer.parseInt(product.getPrice()));
        holder.qtyMinus.setOnClickListener(v -> {
            qty.set(qty.get() - 1);
            holder.qty.setText(""+qty);
            double totalprice= Double.parseDouble(String.valueOf(qty))*Double.parseDouble(String.valueOf(price));
            holder.totalPrice.setText(""+totalprice);

            if (holder.qty.getText().toString().equals("0")){
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Remove product");
                builder.setMessage("Are you sure remove item from cart");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    notifyItemRemoved(position);
                });
                builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
        holder.qtyPlus.setOnClickListener(v -> {
            qty.set(qty.get() + 1);
            holder.qty.setText(""+qty);
            double totalprice= Double.parseDouble(String.valueOf(qty))*Double.parseDouble(String.valueOf(price));
            holder.totalPrice.setText(""+totalprice);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvName,tvPrice,tvOffer;
        CardView cardView;
        TextView qtyMinus,qtyPlus,qty,totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.product_image);
            tvName=itemView.findViewById(R.id.product_name);
            tvPrice=itemView.findViewById(R.id.product_price);
            tvOffer=itemView.findViewById(R.id.product_offer);
            cardView=itemView.findViewById(R.id.cv_item);
            qtyMinus=itemView.findViewById(R.id.qtyminus);
            qtyPlus=itemView.findViewById(R.id.qtyplus);
            qty=itemView.findViewById(R.id.qty);
            totalPrice=itemView.findViewById(R.id.total_price);
        }
    }
}
