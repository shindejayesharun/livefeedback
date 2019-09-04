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

package sti.com.livefeedback.data.remote;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sti.com.livefeedback.di.ApiInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Singleton
public class ApiHeader {

    private ProtectedApiHeader mProtectedApiHeader;

    private PublicApiHeader mPublicApiHeader;
    private ProtectedApiRefreshHeader mProtectedApiRefreshHeader;

    @Inject
    public ApiHeader(PublicApiHeader publicApiHeader, ProtectedApiHeader protectedApiHeader) {
        mPublicApiHeader = publicApiHeader;
        mProtectedApiHeader = protectedApiHeader;
    }

    public ProtectedApiHeader getProtectedApiHeader() {
        return mProtectedApiHeader;
    }

    public PublicApiHeader getPublicApiHeader() {
        return mPublicApiHeader;
    }

    public ProtectedApiRefreshHeader getProtectedApiRefreshHeader() {
        return mProtectedApiRefreshHeader;
    }

    public static final class ProtectedApiHeader {

        @Expose
        @SerializedName("token")
        private String mAccessToken;

        @Expose
        @SerializedName("refresh")
        private String mRefreshToken;

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Expose
        @SerializedName("user_id")
        private Long mUserId;

        @Expose
        @SerializedName("Content-Type")
        private String ContextType;

        public ProtectedApiHeader(String mApiKey, Long mUserId, String mAccessToken) {
            this.mApiKey = mApiKey;
            this.mUserId = mUserId;
            this.mAccessToken = mAccessToken;
            this.ContextType= "application/json;charset=UTF-8";
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }

        public Long getUserId() {
            return mUserId;
        }

        public void setUserId(Long mUserId) {
            this.mUserId = mUserId;
        }

        public String getmContextType() {
            return ContextType;
        }

        public void setmContextType(String mContextType) {
            this.ContextType = mContextType;
        }

        public String getmRefreshToken() {
            return mRefreshToken;
        }

        public void setmRefreshToken(String mRefreshToken) {
            this.mRefreshToken = mRefreshToken;
        }
    }

    public static final class ProtectedApiRefreshHeader {

        @Expose
        @SerializedName("token")
        private String mAccessToken;


        @Expose
        @SerializedName("refresh")
        private String mRefreshToken;

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Expose
        @SerializedName("user_id")
        private Long mUserId;

        @Expose
        @SerializedName("Content-Type")
        private String ContextType;

        public ProtectedApiRefreshHeader(String mApiKey, Long mUserId, String mAccessToken) {
            this.mApiKey = mApiKey;
            this.mUserId = mUserId;
            this.mAccessToken = mAccessToken;
            this.ContextType= "application/json;charset=UTF-8";
        }

        public String getAccessToken() {
            return mAccessToken;
        }

        public void setAccessToken(String accessToken) {
            mAccessToken = accessToken;
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }

        public Long getUserId() {
            return mUserId;
        }

        public void setUserId(Long mUserId) {
            this.mUserId = mUserId;
        }

        public String getmContextType() {
            return ContextType;
        }

        public void setmContextType(String mContextType) {
            this.ContextType = mContextType;
        }

        public String getmRefreshToken() {
            return mRefreshToken;
        }

        public void setmRefreshToken(String mRefreshToken) {
            this.mRefreshToken = mRefreshToken;
        }
    }

    public static final class PublicApiHeader {

        @Expose
        @SerializedName("api_key")
        private String mApiKey;

        @Expose
        @SerializedName("Content-Type")
        private String mContextType;

        @Inject
        public PublicApiHeader(@ApiInfo String apiKey,@ApiInfo String contentType) {
            mApiKey = apiKey;
            mContextType= "application/json;charset=UTF-8";
        }

        public String getApiKey() {
            return mApiKey;
        }

        public void setApiKey(String apiKey) {
            mApiKey = apiKey;
        }

        public String getmContextType() {
            return mContextType;
        }

        public void setmContextType(String mContextType) {
            this.mContextType = mContextType;
        }
    }

    @NonNull
    @SuppressLint("HardwareIds")
    private HashMap<String, String> getDeviceInfo() {
        HashMap<String, String> info = new HashMap<>();
        info.put("device_version", "" + Build.VERSION.SDK_INT);
        /*info.put("device_id", "" + Settings.Secure.getString(getcontext.getContentResolver(),
                Settings.Secure.ANDROID_ID));*/
        info.put("serial", Build.USER);
        info.put("model", Build.MODEL);
        info.put("id", Build.ID);
        info.put("manufacture", Build.MANUFACTURER);
        info.put("sdk", Build.VERSION.SDK);
        info.put("version_code", Build.VERSION.RELEASE);

        return info;
    }
}
