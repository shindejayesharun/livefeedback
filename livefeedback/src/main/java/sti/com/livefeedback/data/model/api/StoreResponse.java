/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package sti.com.livefeedback.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sti.com.livefeedback.data.model.api.storedetails.Review;

import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

public class StoreResponse {

    @Expose
    @SerializedName("stores")
    private List<Blog> data;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("status_code")
    private String statusCode;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StoreResponse)) {
            return false;
        }

        StoreResponse that = (StoreResponse) o;

        if (!statusCode.equals(that.statusCode)) {
            return false;
        }
        if (!message.equals(that.message)) {
            return false;
        }
        return data.equals(that.data);

    }

    @Override
    public int hashCode() {
        int result = statusCode.hashCode();
        result = 31 * result + message.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    public List<Blog> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static class Blog {

        @Expose
        @SerializedName("storeId")
        private String storeId;
        @Expose
        @SerializedName("name")
        private String name;

        @Expose
        @SerializedName("area")
        private String area;

        @Expose
        @SerializedName("logo")
        private String logo;

        @Expose
        @SerializedName("category")
        private String category;

        @Expose
        @SerializedName("happyCustomers")
        private int happyCustomers;

        @Expose
        @SerializedName("urlKey")
        private String urlKey;

        @SerializedName("reviews")
        @Expose
        private List<Review> reviews = null;

        @SerializedName("services")
        @Expose
        private List<String> services = null;

        @SerializedName("tags")
        @Expose
        private List<String> tags = null;

        @SerializedName("coordinates")
        @Expose
        private List<Object> coordinates = null;

        @SerializedName("distance")
        @Expose
        private Double distance = null;

        public String getName() {
            return name;
        }

        public String getStoreId() {
            return storeId;
        }

        public String getArea() {
            return area;
        }

        public String getLogo() {
            return logo;
        }

        public String getCategory() {
            return category;
        }

        public int getHappyCustomers() {
            return happyCustomers;
        }

        public String getUrlKey() {
            return urlKey;
        }

        public List<Review> getReviews() {
            return reviews;
        }

        public List<String> getServices() {
            return services;
        }

        public List<String> getTags() {
            return tags;
        }

        public List<Object> getCoordinates() {
            return coordinates;
        }

        public Double getDistance() {
            return distance;
        }
    }
}