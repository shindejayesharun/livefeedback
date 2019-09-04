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

import sti.com.livefeedback.data.local.db.DbHelper;
import sti.com.livefeedback.data.local.prefs.PreferencesHelper;
import sti.com.livefeedback.data.model.db.Cart;
import sti.com.livefeedback.data.model.others.QuestionCardData;
import sti.com.livefeedback.data.remote.ApiHelper;
import io.reactivex.Observable;

import java.util.List;

/**
 * Created by amitshekhar on 07/07/17.
 */

public interface DataManager extends DbHelper, PreferencesHelper, ApiHelper {

    Observable<List<QuestionCardData>> getQuestionCardData();

    Observable<Boolean> seedDatabaseOptions();

    Observable<Boolean> addCart(final Cart cart);

    Observable<Boolean> removeCart(final Cart cart);

    Observable<Cart> findInCart(String productName);

    List<Cart> getAllCart();

    Observable<Boolean> seedDatabaseQuestions();

    void setUserAsLoggedOut();

    void updateApiHeader(Long userId, String accessToken);

    void updateUserInfo(
            String accessToken,
            String refreshToken);

  /*  Observable<List<Favorite>> getAllFavorite();

    Observable<Boolean> insertFavorite(final Favorite favorite);*/


    enum LoggedInMode {

        LOGGED_IN_MODE_LOGGED_OUT(0),
        LOGGED_IN_MODE_GOOGLE(1),
        LOGGED_IN_MODE_FB(2),
        LOGGED_IN_MODE_SERVER(3);

        private final int mType;

        LoggedInMode(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
