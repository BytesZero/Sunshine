package com.zhengsonglan.sunshine;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.thinkland.sdk.android.SDKInitializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static String LOG_TAG = MainActivity.class.getSimpleName();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */

    private void initData() {
        String[] forecasts = {
                "Mon 6/23 - Sunny - 31/17",
                "Tue 6/24 - Foggy - 21/8",
                "Wed 6/25 - Cloudy - 22/17",
                "Thurs 6/26 - Rainy - 18/11",
                "Fri 6/27 - Foggy - 21/10",
                "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                "Sun 6/29 - Sunny - 20/7"
        };
        List<String> weekForeCast = new ArrayList<String>(Arrays.asList(forecasts));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForeCast);
        listView.setAdapter(dataAdapter);
        GetWeatherData(weekForeCast, dataAdapter);

    }

    /**
     * 初始化视图
     */

    private void initView() {
        listView = (ListView) findViewById(R.id.listView_forecast);
    }

    /**
     * 获取天气的信息
     *
     * @param weekForeCast
     * @param dataAdapter
     */
    private void GetWeatherData(final List<String> weekForeCast, final ArrayAdapter<String> dataAdapter) {
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
                        weekForeCast.add(city + weather + temperature + wind + dressing_index);
                        dataAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
