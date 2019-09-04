package sti.com.livefeedback.ui.main.stores.tags;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;

import java.util.List;

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.ViewHolder> {
  List<String> tags;
  Context context;
  public TagsAdapter(Context context, List<String> tags) {
    this.context=context;
    this.tags=tags;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.item_tag_view,parent,false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String tag=tags.get(position);
    holder.tvTitle.setText(tag);
  }

  @Override
  public int getItemCount() {
    if(tags==null)
      return 0;
    return tags.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvTitle=itemView.findViewById(R.id.category_name);
    }
  }
}
