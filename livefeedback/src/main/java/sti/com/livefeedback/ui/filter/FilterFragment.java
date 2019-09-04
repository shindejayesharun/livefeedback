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

package sti.com.livefeedback.ui.filter;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.databinding.FragmentAboutBinding;
import sti.com.livefeedback.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 09/07/17.
 */

public class FilterFragment extends BaseFragment<FragmentAboutBinding, FilterViewModel> implements FilterNavigator {

    public static final String TAG = FilterFragment.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    private FilterViewModel mFilterViewModel;

    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    public FilterViewModel getViewModel() {
        mFilterViewModel = ViewModelProviders.of(this,factory).get(FilterViewModel.class);
        return mFilterViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterViewModel.setNavigator(this);
    }
}
