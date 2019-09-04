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

package sti.com.livefeedback.data.local.db;

import sti.com.livefeedback.data.model.db.*;
import io.reactivex.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by amitshekhar on 07/07/17.
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<Question>> getAllQuestions() {
        return mAppDatabase.questionDao().loadAll()
                .toObservable();
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return mAppDatabase.userDao().loadAll();
            }
        });
    }


    @Override
    public Observable<List<Option>> getOptionsForQuestionId(final Long questionId) {
        return mAppDatabase.optionDao().loadAllByQuestionId(questionId)
                .toObservable();
    }

    @Override
    public Observable<Boolean> insertUser(final User user) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.userDao().insert(user);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> addCart(final Cart cart) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.cartDao().insert(cart);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> removeCart(final Cart cart) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.cartDao().delete(cart);
                return true;
            }
        });
    }


    @Override
    public Observable<Cart> findInCart(final String productName) {
        return mAppDatabase.cartDao().findByName(productName)
                .toObservable();
    }

    @Override
    public List<Cart> getAllCart() {
        return mAppDatabase.cartDao().loadAll();
    }

   /* @Override
    public Observable<List<Favorite>> getAllFavorite() {
        return Observable.fromCallable(new Callable<List<Favorite>>() {
            @Override
            public List<Favorite> call() throws Exception {
                return mAppDatabase.favoriteDao().loadAll();
            }
        });
    }

    @Override
    public Observable<Boolean> insertFavorite(Favorite favorite) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.favoriteDao().insert(favorite);
                return true;
            }
        });
    }*/


    @Override
    public Observable<Boolean> isOptionEmpty() {
        return mAppDatabase.optionDao().loadAll()
                .flatMapObservable(options -> Observable.just(options.isEmpty()));
    }

    @Override
    public Observable<Boolean> isQuestionEmpty() {
        return mAppDatabase.questionDao().loadAll()
                .flatMapObservable(questions -> Observable.just(questions.isEmpty()));

    }

    @Override
    public Observable<Boolean> saveOption(final Option option) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.optionDao().insert(option);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveOptionList(final List<Option> optionList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.optionDao().insertAll(optionList);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveQuestion(final Question question) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.questionDao().insert(question);
                return true;
            }
        });
    }

    @Override
    public Observable<Boolean> saveQuestionList(final List<Question> questionList) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.questionDao().insertAll(questionList);
                return true;
            }
        });
    }
}
