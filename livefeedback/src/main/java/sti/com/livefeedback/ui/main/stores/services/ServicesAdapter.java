package sti.com.livefeedback.ui.main.stores.services;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;

import java.util.List;
import java.util.Random;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {
  List<String> services;
  Context context;
  public ServicesAdapter(Context context, List<String> services) {
    this.context=context;
    this.services=services;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.item_services_tag_view,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String service=services.get(position);
    holder.tvTitle.setText(service);
    Random rnd = new Random();
    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    holder.cardView.setCardBackgroundColor(color);
  }

  @Override
  public int getItemCount() {
    if(services==null)
      return 0;
    return services.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    CardView cardView;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTitle=itemView.findViewById(R.id.category_name);
      cardView=itemView.findViewById(R.id.cardServices);
    }
  }
}
