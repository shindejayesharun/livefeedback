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

package sti.com.livefeedback.ui.storedetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import sti.com.livefeedback.data.DataManager;
import sti.com.livefeedback.data.model.api.storedetails.StoreDetailsResponse;
import sti.com.livefeedback.data.model.db.Cart;
import sti.com.livefeedback.ui.base.BaseViewModel;
import sti.com.livefeedback.utils.rx.SchedulerProvider;

import java.util.List;


public class StoreDetailsViewModel extends BaseViewModel<StoreDetailsNavigator> {


    private  MutableLiveData<StoreDetailsResponse> storeListLiveData = null;
    public StoreDetailsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        storeListLiveData = new MutableLiveData<>();

    }


    public void fetchStoreDetails(String storeId) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getStoreDetailsApiCall(storeId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(storeDetailResponse -> {
                    if (storeDetailResponse != null && storeDetailResponse != null) {
                        storeListLiveData.setValue(storeDetailResponse);
                        getNavigator().setStoreInformation(storeDetailResponse);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));

    }


/*
    public Observable<List<Favorite>> getAllfavorites() {
        Observable<List<Favorite>> data=getDataManager().getAllFavorite();
        return data;
    }

    public Observable<Boolean> addFavorite(){
        Favorite favorite=new Favorite("123","");
        Observable<Boolean> insertStatus= getDataManager().insertFavorite(favorite);
        return insertStatus;
    }
*/

    public LiveData<StoreDetailsResponse> getStoreListLiveData() {
        return storeListLiveData;
    }

    public void onItemClickGallery(){
        getNavigator().openImageGallery();
    }

    public void addToCart(Cart cart) {
        //getDataManager().addCart(cart);
        //List<Carts> carts = getDataManager().getAllCart();
    }
}
