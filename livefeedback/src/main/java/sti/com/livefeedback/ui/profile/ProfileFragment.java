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

package sti.com.livefeedback.ui.profile;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.databinding.FragmentAboutBinding;
import sti.com.livefeedback.databinding.FragmentBlogBinding;
import sti.com.livefeedback.databinding.FragmentProfileBinding;
import sti.com.livefeedback.ui.base.BaseFragment;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 09/07/17.
 */

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileViewModel> implements ProfileNavigator {

    public static final String TAG = ProfileFragment.class.getSimpleName();
    @Inject
    ViewModelProviderFactory factory;
    private ProfileViewModel mProfileViewModel;
    TextView tvName,tvMobile,tvEmail,tvAddress;
    FragmentProfileBinding mFragmentProfileBinding;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        mProfileViewModel = ViewModelProviders.of(this,factory).get(ProfileViewModel.class);
        return mProfileViewModel;
    }

    @Override
    public void goBack() {
        getBaseActivity().onFragmentDetached(TAG);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProfileViewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentProfileBinding = getViewDataBinding();
        mFragmentProfileBinding.tvEmail.setText("");
        mFragmentProfileBinding.tvAddress.setText("");
        mFragmentProfileBinding.tvMobile.setText("");


    }
}
