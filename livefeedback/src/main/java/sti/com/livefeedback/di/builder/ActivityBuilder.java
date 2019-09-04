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

package sti.com.livefeedback.di.builder;

import sti.com.livefeedback.ui.about.AboutFragmentProvider;
import sti.com.livefeedback.ui.feed.FeedActivity;
import sti.com.livefeedback.ui.feed.FeedActivityModule;
import sti.com.livefeedback.ui.feed.blogs.BlogFragmentProvider;
import sti.com.livefeedback.ui.feed.opensource.OpenSourceFragmentProvider;
import sti.com.livefeedback.ui.filter.FilterFragmentProvider;
import sti.com.livefeedback.ui.login.LoginActivity;
import sti.com.livefeedback.ui.main.MainActivity;
import sti.com.livefeedback.ui.main.rating.RateUsDialogProvider;
import sti.com.livefeedback.ui.main.stores.StoreFragmentProvider;
import sti.com.livefeedback.ui.productdetails.ProductDetailsActivity;
import sti.com.livefeedback.ui.profile.ProfileFragmentProvider;
import sti.com.livefeedback.ui.register.RegisterActivity;
import sti.com.livefeedback.ui.splash.SplashActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import sti.com.livefeedback.ui.storedetails.StoreDetailsActivity;


@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {
            FeedActivityModule.class,
            BlogFragmentProvider.class,
            OpenSourceFragmentProvider.class})
    abstract FeedActivity bindFeedActivity();

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract RegisterActivity bindRegisterActivity();

    @ContributesAndroidInjector
    abstract StoreDetailsActivity bindStoreDetailsActivity();

    @ContributesAndroidInjector
    abstract ProductDetailsActivity bindProductDetailsActivity();

    @ContributesAndroidInjector(modules = {
            AboutFragmentProvider.class,
            RateUsDialogProvider.class,
            StoreFragmentProvider.class,
            FilterFragmentProvider.class,
            ProfileFragmentProvider.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();
}
