package sti.com.livefeedback.ui.storedetails.social;

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
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.SocialLink;
import sti.com.livefeedback.data.model.api.storedetails.ThiredrdPartyBooking;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class SocialLinkAdapter extends RecyclerView.Adapter<SocialLinkAdapter.ViewHolder> {

    Context context;
    List<SocialLink> socialLinks;

    public SocialLinkAdapter(Context context, List<SocialLink> socialLinks) {
        this.context = context;
        this.socialLinks = socialLinks;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SocialLink socialLink = socialLinks.get(position);
        holder.orderName.setText(socialLink.getName());
        BindingUtils.setImageUrl(holder.imgLogo,socialLink.getImage());
        holder.itemView.setOnClickListener(v -> {
            try {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(socialLink.getLink()));
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
        if(socialLinks==null)
            return 0;
        return socialLinks.size();
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
