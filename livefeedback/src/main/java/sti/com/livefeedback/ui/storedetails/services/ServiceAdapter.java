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
import sti.com.livefeedback.ui.storedetails.category.CategoryAdapter;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    Context context;
    List<Service> services;
    public OnItemClickService onItemClickService;
    public ServiceAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services=services;
        this.onItemClickService = (OnItemClickService) context;
    }

    public interface OnItemClickService {
        void serviceSelected(Service service);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_services_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Service service=services.get(position);
        BindingUtils.setImageUrl(holder.imgLogo,service.getImage());
        holder.tvName.setText(service.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickService.serviceSelected(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
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
