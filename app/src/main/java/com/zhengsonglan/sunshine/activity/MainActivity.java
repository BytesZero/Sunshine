package com.zhengsonglan.sunshine.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
                ImageView iv_shuaxin= (ImageView) view.findViewById(R.id.detail_iv_shuaxin);
                iv_shuaxin.setTag(position);
                iv_shuaxin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position= (int) v.getTag();
                        String selected_address=locations[position];
                        GetWeatherData(selected_address,position);
                    }
                });
                String selected_address=locations[position];
                address.setText(selected_address);
                GetWeatherData(selected_address,position);
                //防止错乱
                view.setTag(position);
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
    private void GetWeatherData(String cityName, final int position) {
        //聚合天气数据
        Parameters params = new Parameters();
        params.add("cityname", cityName);
        params.add("key", "a1a0b4fe7f37a3e6af72714926d9b33c");
        JuheData.executeWithAPI(1, "http://v.juhe.cn/weather/index", JuheData.GET, params, new DataCallBack() {
            @Override
            public void resultLoaded(int err, String reason, String result) {
                Log.e(LOG_TAG, "err:" + err + "result:" + result + "reason:" + reason);
                if (err == 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        JSONObject resultObject = jsonObject.getJSONObject("result");
                        View view=vp_today.findViewWithTag(position);
                        //sk
                        JSONObject skObject = resultObject.getJSONObject("sk");
                        //当前温度
                        TextView tv_temp= (TextView) view.findViewById(R.id.detail_tv_temp);
                        //发布时间
                        TextView tv_time= (TextView) view.findViewById(R.id.detail_tv_time);
                        String temp=skObject.getString("temp");
                        String time=skObject.getString("time");
                        tv_temp.setText(temp+"°");
                        tv_time.setText(time+" 发布");


                        //today
                        JSONObject todayObject = resultObject.getJSONObject("today");
                        String city = todayObject.getString("city");
                        String wind = todayObject.getString("wind");
                        String dressing_index = todayObject.getString("dressing_index");
                        String temperature = todayObject.getString("temperature");
                        String weather = todayObject.getString("weather");
                        String week = todayObject.getString("week");

                        JSONObject weather_id=todayObject.getJSONObject("weather_id");
                        String fa=weather_id.getString("fa");
                        //今日温度
                        TextView tv_temperature= (TextView) view.findViewById(R.id.detail_tv_temperature);
                        tv_temperature.setText(temperature);
                        //星期
                        TextView tv_week= (TextView) view.findViewById(R.id.detail_tv_week);
                        tv_week.setText(week);
                        //风向风级
                        TextView tv_wind= (TextView) view.findViewById(R.id.detail_tv_wind);
                        tv_wind.setText(wind);
                        //今日天气
                        TextView tv_weather= (TextView) view.findViewById(R.id.detail_tv_weather);
                        tv_weather.setText(weather);

                        ImageView iv_weather= (ImageView) view.findViewById(R.id.detail_iv_weather);
                        iv_weather.setImageResource(getWeatherIcon(fa));

                        Log.e(LOG_TAG,temp+":"+time+":"+fa);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    private int getWeatherIcon(String fa){
        if ("00".equals(fa)){
            return R.drawable.img_tianqi_qing;
        }else if("01".equals(fa)){
            return R.drawable.img_tianqi_duoyun;
        }else{
            return R.drawable.img_tianqi_qing;
        }
    }
}
