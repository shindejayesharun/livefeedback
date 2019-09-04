
package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Navigation {

    @SerializedName("home")
    @Expose
    private String home;
    @SerializedName("contactus")
    @Expose
    private String contactus;
    @SerializedName("gallery")
    @Expose
    private String gallery;
    @SerializedName("services")
    @Expose
    private String services;
    @SerializedName("aboutus")
    @Expose
    private String aboutus;
    @SerializedName("categoryNav")
    @Expose
    private String categoryNav;
    @SerializedName("offers")
    @Expose
    private String offers;
    @SerializedName("testimonial")
    @Expose
    private String testimonial;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getContactus() {
        return contactus;
    }

    public void setContactus(String contactus) {
        this.contactus = contactus;
    }

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getAboutus() {
        return aboutus;
    }

    public void setAboutus(String aboutus) {
        this.aboutus = aboutus;
    }

    public String getCategoryNav() {
        return categoryNav;
    }

    public void setCategoryNav(String categoryNav) {
        this.categoryNav = categoryNav;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getTestimonial() {
        return testimonial;
    }

    public void setTestimonial(String testimonial) {
        this.testimonial = testimonial;
    }
}
