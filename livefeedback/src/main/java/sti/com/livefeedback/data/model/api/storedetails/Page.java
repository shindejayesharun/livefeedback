
package sti.com.livefeedback.data.model.api.storedetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Page {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("templateType")
    @Expose
    private String templateType;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("tagLine")
    @Expose
    private String tagLine;
    @SerializedName("urlKey")
    @Expose
    private String urlKey;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("i18nNames")
    @Expose
    private List<I18nName> i18nNames = null;
    @SerializedName("timings")
    @Expose
    private List<Timing> timings = null;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("contactNumbers")
    @Expose
    private List<ContactNumber> contactNumbers = null;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;
    @SerializedName("branches")
    @Expose
    private List<Branch> branches = null;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("loc")
    @Expose
    private Loc loc;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("dynamicContents")
    @Expose
    private List<DynamicContent> dynamicContents = null;
    @SerializedName("page")
    @Expose
    private Page_ page;
    @SerializedName("shortUrlkey")
    @Expose
    private String shortUrlkey;
    @SerializedName("testimonials")
    @Expose
    private List<Testimonial> testimonials = null;
    @SerializedName("domainRediretion")
    @Expose
    private Boolean domainRediretion;
    @SerializedName("navigation")
    @Expose
    private Navigation navigation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<I18nName> getI18nNames() {
        return i18nNames;
    }

    public void setI18nNames(List<I18nName> i18nNames) {
        this.i18nNames = i18nNames;
    }

    public List<Timing> getTimings() {
        return timings;
    }

    public void setTimings(List<Timing> timings) {
        this.timings = timings;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<ContactNumber> getContactNumbers() {
        return contactNumbers;
    }

    public void setContactNumbers(List<ContactNumber> contactNumbers) {
        this.contactNumbers = contactNumbers;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Loc getLoc() {
        return loc;
    }

    public void setLoc(Loc loc) {
        this.loc = loc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<DynamicContent> getDynamicContents() {
        return dynamicContents;
    }

    public void setDynamicContents(List<DynamicContent> dynamicContents) {
        this.dynamicContents = dynamicContents;
    }

    public Page_ getPage() {
        return page;
    }

    public void setPage(Page_ page) {
        this.page = page;
    }

    public String getShortUrlkey() {
        return shortUrlkey;
    }

    public void setShortUrlkey(String shortUrlkey) {
        this.shortUrlkey = shortUrlkey;
    }

    public List<Testimonial> getTestimonials() {
        return testimonials;
    }

    public void setTestimonials(List<Testimonial> testimonials) {
        this.testimonials = testimonials;
    }

    public Boolean getDomainRediretion() {
        return domainRediretion;
    }

    public void setDomainRediretion(Boolean domainRediretion) {
        this.domainRediretion = domainRediretion;
    }

    public Navigation getNavigation() {
        return navigation;
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

}
