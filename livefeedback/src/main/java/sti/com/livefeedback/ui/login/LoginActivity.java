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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProviders;
import com.androidnetworking.error.ANError;
import com.bumptech.glide.Glide;
import okhttp3.ResponseBody;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.data.model.api.LocationLatLong;
import sti.com.livefeedback.databinding.ActivityLoginBinding;
import sti.com.livefeedback.ui.base.BaseActivity;
import sti.com.livefeedback.ui.home.HomeActivity;
import sti.com.livefeedback.ui.main.MainActivity;
import sti.com.livefeedback.ui.register.RegisterActivity;
import sti.com.livefeedback.utils.BindingUtils;
import sti.com.livefeedback.utils.CommonUtils;
import sti.com.livefeedback.utils.locationTracker.GPSTracker;

import javax.inject.Inject;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginNavigator {

    @Inject
    ViewModelProviderFactory factory;
    private LoginViewModel mLoginViewModel;
    private ActivityLoginBinding mActivityLoginBinding;
    private String TAG = "LOGIN ACTIVITY";

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = ViewModelProviders.of(this,factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(this, "Login Error "+ CommonUtils.networkError(TAG,throwable), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login() {
        String mobile = mActivityLoginBinding.etMobile.getText().toString();
        String password = mActivityLoginBinding.etPassword.getText().toString();
        if (mLoginViewModel.isEmailAndPasswordValid(mobile, password)) {
            hideKeyboard();
            mLoginViewModel.login(mobile, password,true);
        } else {
            Toast.makeText(this, getString(R.string.invalid_mobile_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(LoginActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void openRegisterActivity() {
        Intent intent = RegisterActivity.newIntent(LoginActivity.this);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = getViewDataBinding();
        mLoginViewModel.setNavigator(this);
        //BindingUtils.setImageUrl(mActivityLoginBinding.imgTest,"5d4441fe55f0f3091ce2aa0b");

    }


}
