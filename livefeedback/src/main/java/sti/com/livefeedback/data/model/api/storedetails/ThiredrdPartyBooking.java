package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ThiredrdPartyBooking {

  @SerializedName("image")
  @Expose
  private String image;
  @SerializedName("link")
  @Expose
  private String link;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("id")
  @Expose
  private String id;

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
