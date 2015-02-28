package com.zhengsonglan.sunshine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    public static String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            String[] forecasts = {
                    "Mon 6/23 - Sunny - 31/17",
                    "Tue 6/24 - Foggy - 21/8",
                    "Wed 6/25 - Cloudy - 22/17",
                    "Thurs 6/26 - Rainy - 18/11",
                    "Fri 6/27 - Foggy - 21/10",
                    "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
                    "Sun 6/29 - Sunny - 20/7"
            };
            final List<String>  weekForeCast = new ArrayList<String>(Arrays.asList(forecasts));
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, weekForeCast);
            ListView listView = (ListView) rootView.findViewById(R.id.listView_forecast);
            listView.setAdapter(dataAdapter);
            //聚合天气数据
            Parameters params = new Parameters();
            params.add("ip", "123.117.82.104");
            params.add("key", "a1a0b4fe7f37a3e6af72714926d9b33c");
            JuheData.executeWithAPI(1, "http://v.juhe.cn/weather/ip", JuheData.GET, params, new DataCallBack() {
                @Override
                public void resultLoaded(int err, String reason, String result) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "err:" + err + "result:" + result + "reason:" + reason);
                    if (err==0){
                        try {
                            JSONObject jsonObject=new JSONObject(result);
                            JSONObject resultObject=jsonObject.getJSONObject("result");
                            JSONObject todayObject=resultObject.getJSONObject("today");
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
                            String city=todayObject.getString("city");
                            String wind=todayObject.getString("wind");
                            String dressing_index=todayObject.getString("dressing_index");
                            String temperature=todayObject.getString("temperature");
                            String weather=todayObject.getString("weather");
                            weekForeCast.add(city+weather+temperature+wind+dressing_index);
                            dataAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
            return rootView;
        }
    }
}
