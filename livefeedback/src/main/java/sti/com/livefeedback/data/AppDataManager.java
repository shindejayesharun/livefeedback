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

package sti.com.livefeedback.data;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.reflect.TypeToken;
import sti.com.livefeedback.data.local.db.DbHelper;
import sti.com.livefeedback.data.local.prefs.PreferencesHelper;
import sti.com.livefeedback.data.model.api.*;
import sti.com.livefeedback.data.model.api.storedetails.StoreDetailsResponse;
import sti.com.livefeedback.data.model.db.*;
import sti.com.livefeedback.data.model.others.QuestionCardData;
import sti.com.livefeedback.data.remote.ApiHeader;
import sti.com.livefeedback.data.remote.ApiHelper;
import sti.com.livefeedback.utils.AppConstants;
import sti.com.livefeedback.utils.CommonUtils;
import io.reactivex.Observable;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */
@Singleton
public class AppDataManager implements DataManager {

    private static final String TAG = "AppDataManager";

    private final ApiHelper mApiHelper;

    private final Context mContext;

    private final DbHelper mDbHelper;

    private final Gson mGson;

    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper, ApiHelper apiHelper, Gson gson) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
        mGson = gson;
    }

    @Override
    public Single<LoginResponse> doFacebookLoginApiCall(LoginRequest.FacebookLoginRequest request) {
        return mApiHelper.doFacebookLoginApiCall(request);
    }

    @Override
    public Single<LoginResponse> doGoogleLoginApiCall(LoginRequest.GoogleLoginRequest request) {
        return mApiHelper.doGoogleLoginApiCall(request);
    }

    @Override
    public Single<LogoutResponse> doLogoutApiCall() {
        return mApiHelper.doLogoutApiCall();
    }

    @Override
    public Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request) {
        setCurrentUserLoggedInMode(LoggedInMode.LOGGED_IN_MODE_SERVER);
        return mApiHelper.doServerLoginApiCall(request);
    }

    @Override
    public Single<SignUpRequest> doSignUserApiCall(SignUpRequest request) {
        return mApiHelper.doSignUserApiCall(request);
    }

    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

   /* @Override
    public String getRefreshToken() {
        return mPreferencesHelper.getRefreshToken();
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        mPreferencesHelper.setRefreshToken(refreshToken);
        mApiHelper.getApiHeader().getProtectedApiHeader().setmRefreshToken(refreshToken);
    }*/

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mDbHelper.getAllQuestions();
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public ApiHeader getApiHeader() {
        return mApiHelper.getApiHeader();
    }

    @Override
    public Single<BlogResponse> getBlogApiCall() {
        return mApiHelper.getBlogApiCall();
    }

    @Override
    public Single<StoreResponse> getStoreApiCall(String latlong) {
        return mApiHelper.getStoreApiCall(latlong);
    }

    @Override
    public Single<StoreDetailsResponse> getStoreDetailsApiCall(String storeId) {
        return mApiHelper.getStoreDetailsApiCall(storeId);
    }


    @Override
    public String getCurrentUserEmail() {
        return mPreferencesHelper.getCurrentUserEmail();
    }

    @Override
    public void setCurrentUserEmail(String email) {
        mPreferencesHelper.setCurrentUserEmail(email);
    }

    @Override
    public Long getCurrentUserId() {
        return mPreferencesHelper.getCurrentUserId();
    }

    @Override
    public void setCurrentUserId(Long userId) {
        mPreferencesHelper.setCurrentUserId(userId);
    }

    @Override
    public int getCurrentUserLoggedInMode() {
        return mPreferencesHelper.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public String getCurrentUserName() {
        return mPreferencesHelper.getCurrentUserName();
    }

    @Override
    public void setCurrentUserName(String userName) {
        mPreferencesHelper.setCurrentUserName(userName);
    }

    @Override
    public String getCurrentUserProfilePicUrl() {
        return mPreferencesHelper.getCurrentUserProfilePicUrl();
    }

    @Override
    public void setCurrentUserProfilePicUrl(String profilePicUrl) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl);
    }

    @Override
    public Single<OpenSourceResponse> getOpenSourceApiCall() {
        return mApiHelper.getOpenSourceApiCall();
    }

    @Override
    public Observable<List<Option>> getOptionsForQuestionId(Long questionId) {
        return mDbHelper.getOptionsForQuestionId(questionId);
    }

    @Override
    public Observable<List<QuestionCardData>> getQuestionCardData() {
        return mDbHelper.getAllQuestions()
                .flatMap(questions -> Observable.fromIterable(questions))
                .flatMap(question -> Observable.zip(
                        mDbHelper.getOptionsForQuestionId(question.id),
                        Observable.just(question),
                        (options, question1) -> new QuestionCardData(question1, options)))
                .toList()
                .toObservable();
    }

    @Override
    public Observable<Boolean> insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mDbHelper.isOptionEmpty();
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mDbHelper.isQuestionEmpty();
    }

    @Override
    public Observable<Boolean> saveOption(Option option) {
        return mDbHelper.saveOption(option);
    }

    @Override
    public Observable<Boolean> saveOptionList(List<Option> optionList) {
        return mDbHelper.saveOptionList(optionList);
    }

    @Override
    public Observable<Boolean> saveQuestion(Question question) {
        return mDbHelper.saveQuestion(question);
    }

    @Override
    public Observable<Boolean> saveQuestionList(List<Question> questionList) {
        return mDbHelper.saveQuestionList(questionList);
    }

    @Override
    public Observable<Boolean> addCart(Cart cart) {
        return mDbHelper.addCart(cart);
    }

    @Override
    public Observable<Boolean> removeCart(Cart cart) {
        return mDbHelper.removeCart(cart);
    }

    @Override
    public Observable<Cart> findInCart(String productName) {
        return mDbHelper.findInCart(productName);
    }

    @Override
    public List<Cart> getAllCart() {
        return mDbHelper.getAllCart();
    }

   /* @Override
    public Observable<List<Favorite>> getAllFavorite() {
        return mDbHelper.getAllFavorite();
    }

    @Override
    public Observable<Boolean> insertFavorite(Favorite favorite) {
        return mDbHelper.insertFavorite(favorite);
    }*/

    @Override
    public Observable<Boolean> seedDatabaseOptions() {
        return mDbHelper.isOptionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        Type type = new TypeToken<List<Option>>() {
                        }.getType();
                        List<Option> optionList = mGson.fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_OPTIONS), type);
                        return saveOptionList(optionList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public Observable<Boolean> seedDatabaseQuestions() {
        return mDbHelper.isQuestionEmpty()
                .concatMap(isEmpty -> {
                    if (isEmpty) {
                        Type type = $Gson$Types.newParameterizedTypeWithOwner(null, List.class, Question.class);
                        List<Question> questionList = mGson
                                .fromJson(CommonUtils.loadJSONFromAsset(mContext, AppConstants.SEED_DATABASE_QUESTIONS), type);
                        return saveQuestionList(questionList);
                    }
                    return Observable.just(false);
                });
    }

    @Override
    public void setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null);
    }

    @Override
    public void updateApiHeader(Long userId, String accessToken) {
        mApiHelper.getApiHeader().getProtectedApiHeader().setUserId(userId);
        mApiHelper.getApiHeader().getProtectedApiHeader().setAccessToken(accessToken);
    }

    @Override
    public void updateUserInfo(
            String accessToken,
            String refreshToken) {

        setAccessToken(accessToken);

        updateApiHeader(1l, accessToken);
    }
}
