package sti.com.livefeedback.ui.imagegallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.ArrayList;
import java.util.List;

public class ImageIndicatorAdapter extends RecyclerView.Adapter<ImageIndicatorAdapter.ViewHolder> {
    private List<String> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageIndicatorAdapter(Context context, List<String> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View imageLayout = inflater.inflate(R.layout.item_image_indicator, parent, false);
        return new ViewHolder(imageLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BindingUtils.setImageUrl(holder.imageView,images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image);
        }
    }
}
