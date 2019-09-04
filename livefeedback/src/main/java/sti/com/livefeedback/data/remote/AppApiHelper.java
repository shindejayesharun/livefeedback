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

import com.rx2androidnetworking.Rx2ANRequest;
import sti.com.livefeedback.data.model.api.*;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import io.reactivex.Single;
import sti.com.livefeedback.data.model.api.storedetails.StoreDetailsResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Singleton
public class AppApiHelper implements ApiHelper {

    private ApiHeader mApiHeader;

    @Inject
    public AppApiHelper(ApiHeader apiHeader) {
        mApiHeader = apiHeader;
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_FACEBOOK_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_GOOGLE_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_LOGOUT)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(LogoutResponse.class);
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        return Rx2AndroidNetworking.put(ApiEndPoint.ENDPOINT_SERVER_LOGIN)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addApplicationJsonBody(request)
                .addBodyParameter(request)
                .build()
                .getObjectSingle(LoginResponse.class);
    }

    @Override
    public Single<SignUpRequest> doSignUserApiCall(SignUpRequest request) {
        return Rx2AndroidNetworking.post(ApiEndPoint.ENDPOINT_STORE_DETAILS)
                .addHeaders(mApiHeader.getPublicApiHeader())
                .addApplicationJsonBody(request)
                .addBodyParameter(request)
                .build()
                .getObjectSingle(SignUpRequest.class);
    }


    @Override
    public ApiHeader getApiHeader() {
        return mApiHeader;
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_BLOG)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(BlogResponse.class);
    }

    @Override
    public Single<StoreResponse> getStoreApiCall(String latlong) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STORE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .addQueryParameter("geoLocation",latlong)
                .build()
                .getObjectSingle(StoreResponse.class);
    }

    @Override
    public Single<StoreDetailsResponse> getStoreDetailsApiCall(String storeId) {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STORE_DETAILS+storeId)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(StoreDetailsResponse.class);
    }


  /*  public Single<StoreDetailsResponse> getStoreDetailsApiCall1(String storeId,Boolean tokenExpired) {
        Rx2ANRequest.GetRequestBuilder rx2AndroidNetworking= Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_STORE_DETAILS+storeId);
                if(tokenExpired) {
                    rx2AndroidNetworking.addHeaders(mApiHeader.getProtectedApiRefreshHeader());
                }else {
                    rx2AndroidNetworking.addHeaders(mApiHeader.getProtectedApiHeader());
                }
                rx2AndroidNetworking.build();
        return rx2AndroidNetworking.getObjectSingle(StoreDetailsResponse.class);
    }*/

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_OPEN_SOURCE)
                .addHeaders(mApiHeader.getProtectedApiHeader())
                .build()
                .getObjectSingle(OpenSourceResponse.class);
    }
}
