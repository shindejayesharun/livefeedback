package sti.com.livefeedback.ui.storedetails.testimonial;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Testimonial;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class TestimonialAdapter extends RecyclerView.Adapter<TestimonialAdapter.ViewHolder> {

    Context context;
    List<Testimonial> products;
    public TestimonialAdapter(Context context, List<Testimonial> products) {
        this.context = context;
        this.products=products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_testimonial_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Testimonial testimonial=products.get(position);
        BindingUtils.setImageUrl(holder.imgLogo,testimonial.getImage());
        holder.tvName.setText(testimonial.getName());
        holder.tvReview.setText(Html.fromHtml(testimonial.getDescription()));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvName,tvReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.person_image);
            tvName=itemView.findViewById(R.id.person_name);
            tvReview=itemView.findViewById(R.id.person_review);
        }
    }
}
