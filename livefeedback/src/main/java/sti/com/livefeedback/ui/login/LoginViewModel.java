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

package sti.com.livefeedback.ui.login;

import android.text.TextUtils;
import sti.com.livefeedback.data.DataManager;
import sti.com.livefeedback.data.model.api.LoginRequest;
import sti.com.livefeedback.data.model.api.SignUpRequest;
import sti.com.livefeedback.ui.base.BaseViewModel;
import sti.com.livefeedback.utils.rx.SchedulerProvider;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class LoginViewModel extends BaseViewModel<LoginNavigator> {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }



    public boolean isEmailAndPasswordValid(String mobile, String password) {
        // validate email and password
        if (TextUtils.isEmpty(mobile)) {
            return false;
        }
        if (mobile.length()!=10) {
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void login(String mobile, String password,boolean forceLogin) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doServerLoginApiCall(new LoginRequest.ServerLoginRequest(mobile, password,forceLogin))
                .doOnSuccess(response -> getDataManager()
                        .updateUserInfo(
                                response.getToken(),
                                response.getRefreshToken()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onFbLoginClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doFacebookLoginApiCall(new LoginRequest.FacebookLoginRequest("test3", "test4"))
                .doOnSuccess(response -> getDataManager()
                        .updateUserInfo(
                                response.getToken(),
                                response.getRefreshToken()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onGoogleLoginClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doGoogleLoginApiCall(new LoginRequest.GoogleLoginRequest("test1", "test1"))
                .doOnSuccess(response -> getDataManager()
                        .updateUserInfo(
                                response.getToken(),
                                response.getRefreshToken()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    setIsLoading(false);
                    getNavigator().openMainActivity();
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void onRegisterActivity(){
        getNavigator().openRegisterActivity();
    }


}
