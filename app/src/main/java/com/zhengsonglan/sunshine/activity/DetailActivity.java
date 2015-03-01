package com.zhengsonglan.sunshine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkland.sdk.android.SDKInitializer;
import com.zhengsonglan.sunshine.R;


public class DetailActivity extends BaseActivity {

    public static String LOG_TAG = DetailActivity.class.getSimpleName();
    /**
     * title
     */
    ImageView title_iv_left;
    TextView title_tv_left;
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
        setContentView(R.layout.activity_detail);
        initView();
        initTitle();
        initData();
    }

    /**
     * 初始化Title
     */
    private void initTitle() {
        title_iv_left= (ImageView) findViewById(R.id.title_left_img);
        title_iv_left.setImageResource(R.drawable.img_back_selected);
        title_iv_left.setVisibility(View.VISIBLE);
        title_iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        title_tv_left= (TextView) findViewById(R.id.title_left_tv);
        title_tv_left.setVisibility(View.VISIBLE);
        title_tv_left.setText(R.string.action_back);
        title_tv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title_iv_left.performClick();
            }
        });
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
