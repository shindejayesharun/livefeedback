
package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Carts {

    @SerializedName("products")
    @Expose
    private List<Product> carts = null;

    public List<Product> getCarts() {
        return carts;
    }

    public void setCarts(List<Product> carts) {
        this.carts = carts;
    }
}
