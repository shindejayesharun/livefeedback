package sti.com.livefeedback.ui.productdetails.tabbleddetails;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.TabbedDetail;

import java.util.List;

public class TabbedDetailsAdapter extends RecyclerView.Adapter<TabbedDetailsAdapter.ViewHolder> {

    Context context;
    List<TabbedDetail> tabbedDetails;
    public TabbedDetailsAdapter(Context context, List<TabbedDetail> tabbedDetails) {
        this.context = context;
        this.tabbedDetails=tabbedDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_timing_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TabbedDetail tabbedDetail=tabbedDetails.get(position);
        holder.tvKey.setText(tabbedDetail.getLabel());
        holder.tvValue.setText(Html.fromHtml(tabbedDetail.getDesc()));
    }

    @Override
    public int getItemCount() {
        return tabbedDetails.size();
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
