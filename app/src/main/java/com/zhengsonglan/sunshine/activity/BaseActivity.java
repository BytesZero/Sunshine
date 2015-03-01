package com.zhengsonglan.sunshine.activity;

import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * 基础Activity
 */
public class BaseActivity extends ActionBarActivity {

    /**
     * 显示Toast
     * @param content 要显示的内容
     */
    public void showToast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
}
