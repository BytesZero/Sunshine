package com.zhengsonglan.sunshine.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.thinkland.sdk.android.SDKInitializer;
import com.zhengsonglan.sunshine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    public static String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * viewpager1
     */
    ViewPager vp_today;
    /**
     * viewpager2
     */

    /**
     * data
     */
    String [] locations={"北京","西安"};
    List<View> viewList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化视图
     */

    private void initView() {
        vp_today= (ViewPager) findViewById(R.id.main_viewpager);

    }

    /**
     * 初始化数据
     */

    private void initData() {
        vp_today.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                LayoutInflater inflater=getLayoutInflater();
                View view=View.inflate(getApplicationContext(),R.layout.activity_detail,null);
                TextView address= (TextView) view.findViewById(R.id.detail_tv_address);
                address.setText(locations[position]);
                container.addView(view);
                viewList.add(view);
                return view;
            }

            @Override
            public int getCount() {
                return locations.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

        });

    }

    /**
     * 获取天气的信息
     *
     */
    private void GetWeatherData() {
        //聚合天气数据
        Parameters params = new Parameters();
        params.add("ip", "123.117.82.104");
        params.add("key", "a1a0b4fe7f37a3e6af72714926d9b33c");
        JuheData.executeWithAPI(1, "http://v.juhe.cn/weather/ip", JuheData.GET, params, new DataCallBack() {
            @Override
            public void resultLoaded(int err, String reason, String result) {
                Log.e(LOG_TAG, "err:" + err + "result:" + result + "reason:" + reason);
                if (err == 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject resultObject = jsonObject.getJSONObject("result");
                        JSONObject todayObject = resultObject.getJSONObject("today");
                        /*"temperature": "-2℃~3℃",
                                "weather": "小雪转晴",
                                "weather_id": {
                            "fa": "14",
                                    "fb": "00"
                        },
                        "wind": "微风",
                                "week": "星期六",
                                "city": "北京",
                                "date_y": "2015年02月28日",
                                "dressing_index": "冷",
                                "dressing_advice": "天气冷，建议着棉服、羽绒服、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣、冬大衣或厚羽绒服。",
                                "uv_index": "最弱",
                                "comfort_index": "",
                                "wash_index": "不宜",
                                "travel_index": "",
                                "exercise_index": "较不宜",
                                "drying_index": ""*/
                        String city = todayObject.getString("city");
                        String wind = todayObject.getString("wind");
                        String dressing_index = todayObject.getString("dressing_index");
                        String temperature = todayObject.getString("temperature");
                        String weather = todayObject.getString("weather");



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
