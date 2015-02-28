package com.thinkland.juheapi.demo;

import com.thinkland.sdk.android.SDKInitializer;
import android.app.Application;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		SDKInitializer.initialize(getApplicationContext());
	}
}
