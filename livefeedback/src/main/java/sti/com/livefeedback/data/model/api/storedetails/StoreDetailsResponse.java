
package sti.com.livefeedback.data.model.api.storedetails;

import java.util.HashMap;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

public class StoreDetailsResponse {



    @SerializedName("offeredProducts")
    @Expose
    private List<OfferedProduct> offeredProducts = null;
    @SerializedName("testimonials")
    @Expose
    private List<Testimonial> testimonials = null;
    @SerializedName("page")
    @Expose
    private Page page;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("products")
    @Expose
    private Object products;

    @SerializedName("dynamicContents")
    @Expose
    private List<DynamicContent_> dynamicContents = null;

    public List<OfferedProduct> getOfferedProducts() {
        return offeredProducts;
    }

    public void setOfferedProducts(List<OfferedProduct> offeredProducts) {
        this.offeredProducts = offeredProducts;
    }

    public List<Testimonial> getTestimonials() {
        return testimonials;
    }

    public void setTestimonials(List<Testimonial> testimonials) {
        this.testimonials = testimonials;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<DynamicContent_> getDynamicContents() {
        return dynamicContents;
    }

    public void setDynamicContents(List<DynamicContent_> dynamicContents) {
        this.dynamicContents = dynamicContents;
    }

    public Object getProducts() {
        return products;
    }

    public void setProducts(Object products) {
        this.products = products;
    }
}
