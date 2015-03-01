package com.zhengsonglan.sunshine.activity;

import android.os.Bundle;

import com.thinkland.sdk.android.SDKInitializer;
import com.zhengsonglan.sunshine.R;


public class DetailActivity extends BaseActivity {

    public static String LOG_TAG = DetailActivity.class.getSimpleName();

    /**
     * view
     */

    /**
     * data
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_detail);
        initView();
        initData();
    }

    /**
     * 初始化视图
     */

    private void initView() {

    }

    /**
     * 初始化数据
     */

    private void initData() {

    }



}
