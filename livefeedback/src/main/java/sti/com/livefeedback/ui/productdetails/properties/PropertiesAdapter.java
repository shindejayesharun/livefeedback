package sti.com.livefeedback.ui.productdetails.properties;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;

import java.util.List;

public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesAdapter.ViewHolder> {

    Context context;
    List<Object> properties;
    public PropertiesAdapter(Context context, List<Object> properties) {
        this.context = context;
        this.properties=properties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_timing_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object propertie=properties.get(position);
       /* holder.tvKey.setText(propertie.);
        holder.tvValue.setText(propertie.getValue());*/
    }

    @Override
    public int getItemCount() {
        return properties.size();
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
