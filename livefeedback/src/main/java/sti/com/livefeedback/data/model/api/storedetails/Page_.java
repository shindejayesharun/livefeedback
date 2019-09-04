
package sti.com.livefeedback.data.model.api.storedetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page_ {

    @SerializedName("headerImage")
    @Expose
    private String headerImage;
    @SerializedName("highlights")
    @Expose
    private List<Highlight> highlights = null;
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @SerializedName("banners")
    @Expose
    private List<String> banners = null;
    @SerializedName("socialLinks")
    @Expose
    private List<SocialLink> socialLinks = null;

    @SerializedName("thiredrdPartyBookings")
    @Expose
    private List<ThiredrdPartyBooking> thiredrdPartyBookings = null;

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public List<Highlight> getHighlights() {
        return highlights;
    }

    public void setHighlights(List<Highlight> highlights) {
        this.highlights = highlights;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    public List<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(List<SocialLink> socialLinks) {
        this.socialLinks = socialLinks;
    }

    public List<ThiredrdPartyBooking> getThiredrdPartyBookings() {
        return thiredrdPartyBookings;
    }

    public void setThiredrdPartyBookings(List<ThiredrdPartyBooking> thiredrdPartyBookings) {
        this.thiredrdPartyBookings = thiredrdPartyBookings;
    }
}
