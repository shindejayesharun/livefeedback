package sti.com.livefeedback.ui.storedetails.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.ContactNumber;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    Context context;
    List<ContactNumber> contactNumbers;

    public ContactsAdapter(Context context, List<ContactNumber> contactNumbers) {
        this.context = context;
        this.contactNumbers = contactNumbers;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contact_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactNumber contactNumber = contactNumbers.get(position);
        holder.tvName.setText(contactNumber.getNumber());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactNumber.getNumber()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(contactNumbers==null)
            return 0;
        return contactNumbers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ConstraintLayout constraintLayout;
      
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.category_name);
            constraintLayout = itemView.findViewById(R.id.clRootView);
        }
    }
}
