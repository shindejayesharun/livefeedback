package sti.com.livefeedback.ui.storedetails.gallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Page_;
import sti.com.livefeedback.data.model.api.storedetails.Service;
import sti.com.livefeedback.ui.imagegallery.ImageGalleryActivity;
import sti.com.livefeedback.utils.BindingUtils;

import java.io.Serializable;
import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    Context context;
    List<String> images;
    public GalleryAdapter(Context context, List<String> images) {
        this.context = context;
        this.images=images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_gallery_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageId=images.get(position);
        BindingUtils.setImageUrl(holder.imgLogo,imageId);
        holder.imgLogo.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageGalleryActivity.class);
            intent.putExtra("images", (Serializable) images);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.gallery_image);
        }
    }
}
