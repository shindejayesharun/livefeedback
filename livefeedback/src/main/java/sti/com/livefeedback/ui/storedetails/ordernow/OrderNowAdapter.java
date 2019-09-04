package sti.com.livefeedback.ui.storedetails.ordernow;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.ContactNumber;
import sti.com.livefeedback.data.model.api.storedetails.ThiredrdPartyBooking;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class OrderNowAdapter extends RecyclerView.Adapter<OrderNowAdapter.ViewHolder> {

    Context context;
    List<ThiredrdPartyBooking> thiredrdPartyBookings;

    public OrderNowAdapter(Context context, List<ThiredrdPartyBooking> thiredrdPartyBookings) {
        this.context = context;
        this.thiredrdPartyBookings = thiredrdPartyBookings;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThiredrdPartyBooking thiredrdPartyBooking = thiredrdPartyBookings.get(position);
        holder.orderName.setText(thiredrdPartyBooking.getName());
        BindingUtils.setImageUrl(holder.imgLogo,thiredrdPartyBooking.getImage());
        holder.itemView.setOnClickListener(v -> {
            try {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(thiredrdPartyBooking.getLink()));
                context.startActivity(myIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context, "No application can handle this request."
                        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(thiredrdPartyBookings==null)
            return 0;
        return thiredrdPartyBookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderName;
        ImageView imgLogo;
      
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.order_name);
            imgLogo = itemView.findViewById(R.id.order_image);
          

        }
    }
}
