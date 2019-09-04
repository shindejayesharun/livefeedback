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

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import sti.com.livefeedback.data.model.api.StoreResponse;
import sti.com.livefeedback.databinding.ItemStoreEmptyViewBinding;
import sti.com.livefeedback.databinding.ItemStoreViewBinding;
import sti.com.livefeedback.ui.base.BaseViewHolder;
import sti.com.livefeedback.ui.storedetails.StoreDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amitshekhar on 10/07/17.
 */

public class StoreAdapter extends RecyclerView.Adapter<BaseViewHolder> implements Filterable {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<StoreResponse.Blog> mStoreResponseList;
    private List<StoreResponse.Blog> mStoreResponseParticularList;

    private StoreAdapterListener mListener;

    public StoreAdapter(List<StoreResponse.Blog> blogResponseList) {
        this.mStoreResponseList = blogResponseList;
        this.mStoreResponseParticularList= blogResponseList;
    }

    public StoreAdapter(LiveData<List<StoreResponse.Blog>> storeData) {
        this.mStoreResponseList = storeData.getValue();
        this.mStoreResponseParticularList= storeData.getValue();
    }

    @Override
    public int getItemCount() {
        if (mStoreResponseParticularList != null && mStoreResponseParticularList.size() > 0) {
            return mStoreResponseParticularList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mStoreResponseList != null && !mStoreResponseList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemStoreViewBinding blogViewBinding = ItemStoreViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new StoreViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemStoreEmptyViewBinding emptyViewBinding = ItemStoreEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<StoreResponse.Blog> blogList) {
        mStoreResponseList.addAll(blogList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mStoreResponseList.clear();
    }

    public void setListener(StoreAdapterListener listener) {
        this.mListener = listener;
    }

    public interface StoreAdapterListener {

        void onRetryClick();
    }

    public class StoreViewHolder extends BaseViewHolder implements StoreItemViewModel.StoreItemViewModelListener {

        private ItemStoreViewBinding mBinding;

        private StoreItemViewModel mStoreItemViewModel;

        public StoreViewHolder(ItemStoreViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final StoreResponse.Blog blog = mStoreResponseParticularList.get(position);
            mStoreItemViewModel = new StoreItemViewModel(blog, this);
            mBinding.setViewModel(mStoreItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String storeId) {
            Intent intent = StoreDetailsActivity.newIntent(itemView.getContext());
            intent.putExtra("storeId",storeId);
            itemView.getContext().startActivity(intent);
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements StoreEmptyItemViewModel.StoreEmptyItemViewModelListener {

        private ItemStoreEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemStoreEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            StoreEmptyItemViewModel emptyItemViewModel = new StoreEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @NonNull
            @Override
            protected FilterResults performFiltering(@NonNull CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mStoreResponseParticularList = mStoreResponseList;
                } else {
                    List<StoreResponse.Blog> filteredList = new ArrayList<>();
                    for (StoreResponse.Blog row : mStoreResponseList) {
                        if (row.getCategory().toLowerCase().equals(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    mStoreResponseParticularList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mStoreResponseParticularList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, @NonNull FilterResults filterResults) {
                mStoreResponseParticularList = (ArrayList<StoreResponse.Blog>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}