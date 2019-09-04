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

package sti.com.livefeedback.ui.register;

import android.telephony.SmsManager;
import android.util.Log;
import sti.com.livefeedback.data.DataManager;
import sti.com.livefeedback.data.model.api.LoginRequest;
import sti.com.livefeedback.data.model.api.SignUpRequest;
import sti.com.livefeedback.ui.base.BaseViewModel;
import sti.com.livefeedback.utils.rx.SchedulerProvider;

import java.security.SecureRandom;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class RegisterViewModel extends BaseViewModel<RegisterNavigator> {

    public RegisterViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }



    public boolean onSendOtp(String mobile) {
        setIsLoading(true);
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mobile, null, String.valueOf(generateRandomNumber()), null, null);
            setIsLoading(false);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }



    private int generateRandomNumber() {
        int randomNumber;
        SecureRandom secureRandom = new SecureRandom();
        String s = "";
        for (int i = 0; i < 6; i++) {
            int number = secureRandom.nextInt(9);
            if (number == 0 && i == 0) { // to prevent the Zero to be the first number as then it will reduce the length of generated pin to three or even more if the second or third number came as zeros
                i = -1;
                continue;
            }
            s = s + number;
        }
        randomNumber = Integer.parseInt(s);
        return randomNumber;
    }

    public void SendMessage(String strMobileNo, String strMessage) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);
        } catch (Exception ex) {

        }
    }

    public void onServerLoginClick() {
        getNavigator().login();
    }

    public void signUpUser(SignUpRequest signUpRequest) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .doSignUserApiCall(new SignUpRequest(signUpRequest))
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
}
