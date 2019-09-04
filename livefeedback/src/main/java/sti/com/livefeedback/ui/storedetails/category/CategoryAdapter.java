package sti.com.livefeedback.ui.storedetails.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Product;
import sti.com.livefeedback.data.model.api.storedetails.Timing;
import sti.com.livefeedback.ui.storedetails.product.ProductAdapter;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    Context context;
    List<String> categories;
    public OnItemClickCategory onItemClickCategory;

    public CategoryAdapter(Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
        this.onItemClickCategory = (OnItemClickCategory) context;
    }

    public interface OnItemClickCategory {
        void categoryChanged(String category);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String category = categories.get(position);
        holder.tvName.setText(category);
        holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        holder.itemView.setOnClickListener(v -> {
            onItemClickCategory.categoryChanged(category);
            holder.constraintLayout.setTag("1");
            holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ConstraintLayout constraintLayout;
      
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.category_name);
            constraintLayout = itemView.findViewById(R.id.clRootView);
          

        }
    }
}
