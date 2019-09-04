
package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart {

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("enable")
    @Expose
    private Boolean enable;
    @SerializedName("isSpecial")
    @Expose
    private Boolean isSpecial;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("tabbedDetails")
    @Expose
    private List<TabbedDetail> tabbedDetails = null;
    @SerializedName("images")
    @Expose
    private List<String> images = null;
    @SerializedName("nxnd")
    @Expose
    private String nxnd;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("properties")
    @Expose
    private Object properties;
    @SerializedName("testimonials")
    @Expose
    private List<Testimonial> testimonials = null;
    private boolean isSelected = false;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<TabbedDetail> getTabbedDetails() {
        return tabbedDetails;
    }

    public void setTabbedDetails(List<TabbedDetail> tabbedDetails) {
        this.tabbedDetails = tabbedDetails;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getNxnd() {
        return nxnd;
    }

    public void setNxnd(String nxnd) {
        this.nxnd = nxnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public List<Testimonial> getTestimonials() {
        return testimonials;
    }

    public void setTestimonials(List<Testimonial> testimonials) {
        this.testimonials = testimonials;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
