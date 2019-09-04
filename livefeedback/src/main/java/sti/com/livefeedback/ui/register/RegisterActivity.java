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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import sti.com.livefeedback.BR;
import sti.com.livefeedback.R;
import sti.com.livefeedback.ViewModelProviderFactory;
import sti.com.livefeedback.data.model.api.SignUpRequest;
import sti.com.livefeedback.databinding.ActivityRegisterBinding;
import sti.com.livefeedback.ui.base.BaseActivity;
import sti.com.livefeedback.ui.main.MainActivity;
import sti.com.livefeedback.utils.CommonUtils;

import javax.inject.Inject;
import java.security.SecureRandom;

/**
 * Created by amitshekhar on 08/07/17.
 */

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, RegisterViewModel> implements RegisterNavigator, View.OnClickListener {

    @Inject
    ViewModelProviderFactory factory;
    private RegisterViewModel mRegisterViewModel;
    private ActivityRegisterBinding mActivityRegisterBinding;
    private SignUpRequest signUpRequest;
    private boolean mOtpSend=false;
    private String TAG = "Register Activity";
    private String name,mobile,password,repassword;

    public static Intent newIntent(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public RegisterViewModel getViewModel() {
        mRegisterViewModel = ViewModelProviders.of(this, factory).get(RegisterViewModel.class);
        return mRegisterViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(this, "Register Error " + CommonUtils.networkError(TAG, throwable), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login() {
        String mobile = mActivityRegisterBinding.etMobile.getText().toString();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(RegisterActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegisterBinding = getViewDataBinding();
        mRegisterViewModel.setNavigator(this);
        mActivityRegisterBinding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSubmit:
                if(mOtpSend==false) {
                     name=mActivityRegisterBinding.etName.getText().toString();
                     mobile = mActivityRegisterBinding.etMobile.getText().toString();
                     password= mActivityRegisterBinding.etPassword.getText().toString();
                     repassword= mActivityRegisterBinding.etReEnterPassword.getText().toString();

                    if (validateData()) {
                        SignUpRequest signUpRequest=new SignUpRequest(name,mobile,password,"RETAIL");
                        getViewModel().signUpUser(signUpRequest);
                    }
                }else {

                }
                break;
        }
    }

    private boolean validateData() {
        if(!CommonUtils.isValidString(name)){
            Toast.makeText(this, "Enter Valid Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!CommonUtils.isMobileValid(mobile)){
            Toast.makeText(this, "Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!CommonUtils.isValidPasswords(password,repassword)){
            Toast.makeText(this, "Password Not Match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void optSendSucessfully() {
        Toast.makeText(this, "Otp Send Sucessfully", Toast.LENGTH_SHORT).show();
        mOtpSend = true;
        mActivityRegisterBinding.btnSubmit.setText("Sign Up");
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).
                    registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                Log.d("Message",message);
                /*TextView tv = (TextView) findViewById(R.id.txtview);
                tv.setText(message);*/
            }
        }
    };
}
