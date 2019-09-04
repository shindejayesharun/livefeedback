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

package sti.com.livefeedback.ui.main.stores;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import sti.com.livefeedback.data.DataManager;
import sti.com.livefeedback.data.model.api.StoreResponse;
import sti.com.livefeedback.ui.base.BaseViewModel;
import sti.com.livefeedback.utils.rx.SchedulerProvider;

import java.util.List;



public class StoreViewModel extends BaseViewModel<StoreNavigator> {

    private final MutableLiveData<List<StoreResponse.Blog>> storeListLiveData;

    public StoreViewModel(DataManager dataManager,
                          SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        storeListLiveData = new MutableLiveData<>();
    }

    public void fetchStores(String latlong) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getStoreApiCall(latlong)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(blogResponse -> {
                    if (blogResponse != null && blogResponse.getData() != null) {
                        storeListLiveData.setValue(blogResponse.getData());
                        getNavigator().setCategory();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    public LiveData<List<StoreResponse.Blog>> getStoreListLiveData() {
        return storeListLiveData;
    }

  /*  public void onNavFilterClick() {
        getNavigator().goFilter();
        Log.d("Clicked on filter","");
    }*/



}
