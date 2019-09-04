package sti.com.livefeedback.ui.storedetails.branches;

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
import sti.com.livefeedback.data.model.api.storedetails.Branch;
import sti.com.livefeedback.data.model.api.storedetails.SocialLink;
import sti.com.livefeedback.utils.BindingUtils;

import java.util.List;

public class BranchesLinkAdapter extends RecyclerView.Adapter<BranchesLinkAdapter.ViewHolder> {

    Context context;
    List<Branch> branches;

    public BranchesLinkAdapter(Context context, List<Branch> branches) {
        this.context = context;
        this.branches = branches;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Branch branch = branches.get(position);
        holder.orderName.setText(branch.getName());
        BindingUtils.setImageUrl(holder.imgLogo,branch.getLink());
        holder.itemView.setOnClickListener(v -> {
            try {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(branch.getLink()));
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
        if(branches==null)
            return 0;
        return branches.size();
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
