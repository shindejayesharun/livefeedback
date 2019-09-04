package sti.com.livefeedback.ui.main.stores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.StoreResponse;
import sti.com.livefeedback.data.model.api.storedetails.Review;
import sti.com.livefeedback.ui.main.stores.reviews.ReviewSingleAdapter;
import sti.com.livefeedback.ui.main.stores.services.ServicesAdapter;
import sti.com.livefeedback.ui.main.stores.tags.TagsAdapter;
import sti.com.livefeedback.ui.storedetails.StoreDetailsActivity;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.ViewHolder> implements Filterable {

    List<StoreResponse.Blog> value;
    private List<StoreResponse.Blog> mStoreResponseList;
    private List<StoreResponse.Blog> mStoreResponseParticularList;
    Context context;


    public StoreListAdapter(Context context, List<StoreResponse.Blog> value) {
        this.context = context;
        this.value = value;
        this.mStoreResponseList = value;
        this.mStoreResponseParticularList = value;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_store_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoreResponse.Blog store = mStoreResponseParticularList.get(position);
        BindingUtils.setImageUrl(holder.img, store.getLogo());
        holder.name.setText(store.getName());
        holder.distance.setText(store.getDistance().intValue() + " km");
        holder.address.setText(store.getArea());

        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, StoreDetailsActivity.class);
            i.putExtra("storeId", store.getStoreId());
            context.startActivity(i);
        });

        if (store.getServices() != null) {
            if (store.getServices().size() < 4) {
                holder.servicesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            } else {
                holder.servicesRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            }
            ServicesAdapter servicesAdapter = new ServicesAdapter(context, store.getServices());
            holder.servicesRecyclerview.setAdapter(servicesAdapter);
        }

        if (store.getTags() != null) {
            if (store.getTags().size() < 4) {
                holder.tagsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

            } else {
                holder.tagsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            }
            TagsAdapter tagsAdapter = new TagsAdapter(context, store.getTags());
            holder.tagsRecyclerview.setAdapter(tagsAdapter);
        }


        if (store.getReviews() != null && store.getReviews().size() > 0) {
            List<Review> reviews = new ArrayList<>();
            reviews.add(store.getReviews().get(0));
            ReviewSingleAdapter reviewAdapter = new ReviewSingleAdapter(context, reviews, store.getReviews());
            holder.reviewsRecyclerview.setAdapter(reviewAdapter);


        }

    }

    @Override
    public int getItemCount() {
        if (mStoreResponseParticularList != null && mStoreResponseParticularList.size() > 0) {
            return mStoreResponseParticularList.size();
        } else {
            return 1;
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
                    mStoreResponseParticularList = mStoreResponseList;
                } else {
                    List<StoreResponse.Blog> filteredList = new ArrayList<>();
                    for (StoreResponse.Blog row : mStoreResponseList) {
                        if (row.getCategory().toLowerCase().equals(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mStoreResponseParticularList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mStoreResponseParticularList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                mStoreResponseParticularList = (ArrayList<StoreResponse.Blog>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, distance, address;
        RecyclerView servicesRecyclerview, tagsRecyclerview, reviewsRecyclerview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.product_name);
            distance = itemView.findViewById(R.id.distance);
            address = itemView.findViewById(R.id.address);
            servicesRecyclerview = itemView.findViewById(R.id.servicesTextRecyclerview);
            tagsRecyclerview = itemView.findViewById(R.id.tagTextRecyclerview);
            reviewsRecyclerview = itemView.findViewById(R.id.reviewsRecyclerview);

            //servicesRecyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));

            //tagsRecyclerview.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            reviewsRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
