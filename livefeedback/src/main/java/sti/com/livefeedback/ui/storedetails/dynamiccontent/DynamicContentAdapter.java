package sti.com.livefeedback.ui.storedetails.dynamiccontent;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.DynamicContent;
import sti.com.livefeedback.data.model.api.storedetails.DynamicContent_;
import sti.com.livefeedback.data.model.api.storedetails.Value;
import sti.com.livefeedback.data.model.api.storedetails.Value_;
import sti.com.livefeedback.utils.BindingUtils;
import sti.com.livefeedback.utils.CommonUtils;

import java.util.List;

public class DynamicContentAdapter extends RecyclerView.Adapter<DynamicContentAdapter.ViewHolder> {

    Context context;
    List<Value_> dynamicContents;
    //public OnItemClickCategory onItemClickCategory;

    public DynamicContentAdapter(Context context, List<Value_> dynamicContents) {
        this.context = context;
        this.dynamicContents = dynamicContents;
        //this.onItemClickCategory = (OnItemClickCategory) context;
    }

  /*  public interface OnItemClickCategory {
        void categoryChanged(String category);
    }*/

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dynamiccontent_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Value_ dynamicContent = dynamicContents.get(position);
        holder.title.setText(dynamicContent.getTitle());
        holder.webview.setText(Html.fromHtml(dynamicContent.getContent()));
        holder.imgLogo.setVisibility(View.VISIBLE);
        if(dynamicContent.getImage()!=null && !dynamicContent.getImage().isEmpty()) {
            BindingUtils.setImageUrl(holder.imgLogo, dynamicContent.getImage());
        }else {
            holder.imgLogo.setVisibility(View.GONE);
        }
        holder.webview.setMaxLines(4);
        holder.tvArrowAction.setText("Expand");
        holder.tvArrowAction.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_arrow_down, 0, 0, 0);
        //CommonUtils.collapse( holder.webview);

        holder.itemView.setOnClickListener(v -> {
            if(holder.webview.getMaxLines()==4) {
                holder.webview.setMaxLines(Integer.MAX_VALUE);
                holder.tvArrowAction.setText("Collapse");
                holder.tvArrowAction.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_arrow_up, 0, 0, 0);            }else {
                holder.webview.setMaxLines(4);
                holder.tvArrowAction.setText("Expand");
                holder.tvArrowAction.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_arrow_down, 0, 0, 0);            }
        });
    }

    @Override
    public int getItemCount() {
        if(dynamicContents==null){
            return 0;
        }
        return dynamicContents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgLogo;
        TextView title,webview;
        TextView tvArrowAction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.dynamic_image);
            title = itemView.findViewById(R.id.dynamic_name);
            webview = itemView.findViewById(R.id.tvWebView);
            tvArrowAction = itemView.findViewById(R.id.tvArrowAction);
        }
    }
}
