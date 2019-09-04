package sti.com.livefeedback.ui.storedetails.services;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Service;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class ServiceFullAdapter extends RecyclerView.Adapter<ServiceFullAdapter.ViewHolder> {

    Context context;
    Service services;
    public ServiceFullAdapter(Context context, Service services) {
        this.context = context;
        this.services=services;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_service_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service=services;
        BindingUtils.setImageUrl(holder.imgLogo,service.getImage());
        holder.tvName.setText(service.getDescription());
    }

    @Override
    public int getItemCount() {
        if(services!=null){
            return 1;
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo=itemView.findViewById(R.id.service_image);
            tvName=itemView.findViewById(R.id.service_name);
        }
    }
}
