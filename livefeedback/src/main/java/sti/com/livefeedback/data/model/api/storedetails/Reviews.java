package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Reviews {
  @SerializedName("reviews")
  @Expose
  private List<Review> reviews = null;

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }
}
