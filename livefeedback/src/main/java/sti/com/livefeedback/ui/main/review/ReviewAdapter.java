package sti.com.livefeedback.ui.main.review;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import sti.com.livefeedback.R;
import sti.com.livefeedback.data.model.api.storedetails.Review;
import sti.com.livefeedback.utils.CommonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<Review> reviews;
    Context context;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context=context;
        this.reviews=reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_review_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Review review=reviews.get(position);
        holder.name.setText(CommonUtils.toUpperCaseFirstChar(review.getName()));

        if(review.getDate().length()>20) {
            holder.date.setText(dateConvert(review.getDate().substring(0, 19).replace("T", " ")));
        }
        if(review.getElements()!=null && review.getElements().size()>0) {
           for(int i=0;i<review.getElements().size();i++) {
               View view=LayoutInflater.from(context).inflate(R.layout.item_review_question_view,null);
               View viewCircle=view.findViewById(R.id.view);
               Random rnd = new Random();
               int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
               viewCircle.setBackgroundTintList(ColorStateList.valueOf(color));

               TextView tvQuestion=view.findViewById(R.id.qustion);
               LinearLayout llOptions=view.findViewById(R.id.llOptions);
               tvQuestion.setText(CommonUtils.toUpperCaseFirstChar(review.getElements().get(i).getLabel()));
               if(review.getElements().get(i).getOptions()!=null && review.getElements().get(i).getOptions().size()>0) {
                   for (int j = 0; j < review.getElements().get(i).getOptions().size(); j++) {
                       View viewOption=LayoutInflater.from(context).inflate(R.layout.item_review_option_view,null);
                       TextView tvOption = viewOption.findViewById(R.id.review_option);
                       tvOption.setText(CommonUtils.toUpperCaseFirstChar(review.getElements().get(i).getOptions().get(j).getLabel()));
                       llOptions.addView(viewOption);
                   }
               }
               holder.llQuestion.addView(view);
           }
        }

    }

    private String dateConvert(String dateString)  {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM hh:mm";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str="";
        try {
            date = inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        str = outputFormat.format(date);
        return str;
    }

    @Override
    public int getItemCount() {
        if(reviews==null)
            return 0;
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,review,date;
        LinearLayout llQuestion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.person_name);
            date=itemView.findViewById(R.id.date);
            review=itemView.findViewById(R.id.person_review);
            llQuestion=itemView.findViewById(R.id.llquestion);
        }
    }


}
