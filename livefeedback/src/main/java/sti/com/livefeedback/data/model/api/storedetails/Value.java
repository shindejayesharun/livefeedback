
package sti.com.livefeedback.data.model.api.storedetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("values")
    @Expose
    private List<Value_> values = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Value_> getValues() {
        return values;
    }

    public void setValues(List<Value_> values) {
        this.values = values;
    }

}
