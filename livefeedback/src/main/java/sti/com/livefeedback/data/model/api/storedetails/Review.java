package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review   {
  @SerializedName("date")
  @Expose
  private String date;
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("elements")
  @Expose
  private List<Element> elements = null;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Element> getElements() {
    return elements;
  }

  public void setElements(List<Element> elements) {
    this.elements = elements;
  }
}
