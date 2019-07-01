package com.lovisgod.paystackpractice;

import android.app.Application;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PaystackSdk.initialize(getApplicationContext());
    }
}
