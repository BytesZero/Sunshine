package com.zhengsonglan.sunshine.activity;

import android.os.Bundle;

import com.thinkland.sdk.android.SDKInitializer;
import com.zhengsonglan.sunshine.R;


public class AAAAAActivity extends BaseActivity {

    public static String LOG_TAG = AAAAAActivity.class.getSimpleName();
    /**
     * title
     */
    
    /**
     * content
     */

    /**
     * data
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initTitle();
        initData();
    }

    /**
     * 初始化Title
     */
    private void initTitle() {

    }

    /**
     * 初始化数据
     */

    private void initData() {

    }

    /**
     * 初始化视图
     */

    private void initView() {

    }


}
