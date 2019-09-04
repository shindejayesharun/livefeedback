package sti.com.livefeedback.ui.storedetails.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Product;
import sti.com.livefeedback.ui.productdetails.ProductDetailsActivity;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Product> products;
    private List<Product> productFiltredList;
    public OnItemClickProduct onItemClickProduct ;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products=products;
        this.productFiltredList = products;
        this.onItemClickProduct= (OnItemClickProduct) context;
    }

    public interface OnItemClickProduct {
        void addedCart(Product product, View view);
        void deleteCart(Product product, View view);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_product_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product=productFiltredList.get(position);
        if(product.getImages()!=null) {
            if(product.getImages().size()>0) {
                BindingUtils.setImageUrl(holder.imgLogo, product.getImages().get(0));
            }else {
                holder.imgLogo.setVisibility(View.GONE);
                holder.imgLogo.setBackground(context.getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark));
            }
        }
        holder.tvName.setText(product.getName());
        holder.tvPrice.setText(product.getPrice());
        holder.tvOffer.setText(product.getOffer());
        holder.imgCart.setBackground(product.isSelected() ? context.getResources().getDrawable(R.drawable.ic_remove_shopping) : context.getResources().getDrawable(R.drawable.ic_shopping_cart));
        holder.cardView.setCardBackgroundColor(product.isSelected() ? context.getResources().getColor(R.color.light_gray) : Color.WHITE);
        holder.cardView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        holder.itemView.setOnClickListener(v -> {
            Intent i=new Intent(context, ProductDetailsActivity.class);
            Gson gson = new Gson();
            String mProduct = gson.toJson(product);
            i.putExtra("product",mProduct);
            context.startActivity(i);
        });

        holder.imgCart.setOnClickListener(v -> {
            int e[] = new int[2];
            holder.imgCart.getLocationInWindow(e);
            if(product.isSelected()) {
                onItemClickProduct.deleteCart(product,holder.cardView);
            }else {
                onItemClickProduct.addedCart(product,holder.cardView);
            }
            product.setSelected(!product.isSelected());
            holder.imgCart.setBackground(product.isSelected() ? context.getResources().getDrawable(R.drawable.ic_remove_shopping) : context.getResources().getDrawable(R.drawable.ic_shopping_cart));
            holder.cardView.setCardBackgroundColor(product.isSelected() ? context.getResources().getColor(R.color.light_gray) : Color.WHITE);
        });

        holder.imgFavorite.setOnClickListener(v -> {
            Toast.makeText(context, "clicked on Favorite", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        if(productFiltredList==null)
            return 0;
        return productFiltredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo,imgCart,imgFavorite;
        TextView tvName,tvPrice,tvOffer;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.product_image);
            tvName=itemView.findViewById(R.id.product_name);
            tvPrice=itemView.findViewById(R.id.product_price);
            tvOffer=itemView.findViewById(R.id.product_offer);
            imgCart=itemView.findViewById(R.id.imgCart);
            cardView=itemView.findViewById(R.id.cv_item);
            imgFavorite= itemView.findViewById(R.id.imgFavorite);
        }
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productFiltredList = products;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product row : products) {
                        if (row.getCategory().toLowerCase().equals(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    productFiltredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = productFiltredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                productFiltredList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
