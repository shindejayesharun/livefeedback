package sti.com.livefeedback.ui.main.maincategory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Product;

import java.util.ArrayList;
import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder>  {

    Context context;
    List<String> categories;
    public OnItemClickMainCategory onItemClickMainCategory;
    private int checkedPosition = -1;
    public MainCategoryAdapter(Context context, List<String> categories) {
        this.context = context;
        this.categories = categories;
        this.onItemClickMainCategory= (OnItemClickMainCategory) context;
    }

    public interface OnItemClickMainCategory {
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

        if (checkedPosition == position) {
            holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
        } else {
            holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        //holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
        holder.itemView.setOnClickListener(v -> {
            checkedPosition = position;
            notifyDataSetChanged();
            onItemClickMainCategory.categoryChanged(category);
            //notifyItemChanged(position);
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
