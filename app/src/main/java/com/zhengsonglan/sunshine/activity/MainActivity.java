package com.zhengsonglan.sunshine.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.thinkland.sdk.android.SDKInitializer;
import com.zhengsonglan.sunshine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends BaseActivity {

    public static String LOG_TAG = MainActivity.class.getSimpleName();
    /**
     * title
     */
    ImageView iv_title_right;
    /**
     * content
     */
    ListView listView;

    /**
     * data
     */
    List<String> weekForeCast;
    ArrayAdapter<String> dataAdapter;


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
        iv_title_right= (ImageView) findViewById(R.id.title_right_img);
        iv_title_right.setVisibility(View.VISIBLE);
        iv_title_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
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
        weekForeCast = new ArrayList<String>(Arrays.asList(forecasts));
        dataAdapter = new ArrayAdapter<String>(this, R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForeCast);
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });
        GetWeatherData();

    }

    /**
     * 初始化视图
     */

    private void initView() {
        listView = (ListView) findViewById(R.id.listView_forecast);
    }

    /**
     * 显示弹出悬浮菜单
     * @param view
     */
    @SuppressLint("NewApi")
    private void showPopup(View view)
    {
        PopupMenu popupMenu=new PopupMenu(this,view);
        MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_main,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.e(LOG_TAG,"getItemId:"+item.getItemId()+"getOrder:"+item.getOrder());
                if (item.getOrder()==1){
                    GetWeatherData();
                }
                return true;
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
