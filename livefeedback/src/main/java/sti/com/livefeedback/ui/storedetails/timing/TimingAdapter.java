package sti.com.livefeedback.ui.storedetails.timing;

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
import sti.com.livefeedback.data.model.api.storedetails.Timing;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class TimingAdapter extends RecyclerView.Adapter<TimingAdapter.ViewHolder> {

    Context context;
    List<Timing> timings;
    public TimingAdapter(Context context, List<Timing> timings) {
        this.context = context;
        this.timings=timings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_timing_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Timing timing=timings.get(position);
        holder.tvKey.setText(timing.getLabel());
        holder.tvValue.setText(timing.getValue());
    }

    @Override
    public int getItemCount() {
        return timings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKey,tvValue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKey=itemView.findViewById(R.id.timing_key);
            tvValue=itemView.findViewById(R.id.timing_value);
        }
    }
}
