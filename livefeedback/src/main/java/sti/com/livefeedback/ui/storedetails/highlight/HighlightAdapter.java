package sti.com.livefeedback.ui.storedetails.highlight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Highlight;
import sti.com.livefeedback.data.model.api.storedetails.OfferedProduct;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class HighlightAdapter extends RecyclerView.Adapter<HighlightAdapter.ViewHolder> {

    Context context;
    List<Highlight> highlights;
    public HighlightAdapter(Context context, List<Highlight> highlights) {
        this.context = context;
        this.highlights=highlights;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_highlight_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Highlight highlight=highlights.get(position);
        BindingUtils.setImageUrl(holder.imgLogo,highlight.getImage());
        holder.tvHighlight.setText(highlight.getName());
        holder.tvCount.setText(highlight.getCount());
    }

    @Override
    public int getItemCount() {
        return highlights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvHighlight,tvCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.highlight_image);
            tvHighlight=itemView.findViewById(R.id.tvHighlight);
            tvCount=itemView.findViewById(R.id.tvCount);
        }
    }
}
