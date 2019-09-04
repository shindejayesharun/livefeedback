
package sti.com.livefeedback.data.model.api.storedetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class I18nName {

    @SerializedName("language")
    @Expose
    private Integer language;
    @SerializedName("label")
    @Expose
    private String label;

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
