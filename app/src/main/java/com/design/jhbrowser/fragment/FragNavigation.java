package com.design.jhbrowser.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.design.jhbrowser.R;
import com.design.jhbrowser.fragment.bean.Cw;
import com.design.jhbrowser.fragment.bean.Sentence;
import com.design.jhbrowser.fragment.bean.Ws;
import com.design.jhbrowser.qrcode.zxing.CaptureActivity;
import com.design.jhbrowser.bean.Navigation;
import com.design.jhbrowser.fragment.adapter.FullyLinearLayoutManager;
import com.design.jhbrowser.fragment.bean.RecyclerBean;
import com.design.jhbrowser.fragment.navigation.MyPageIndicator;
import com.design.jhbrowser.fragment.navigation.PageGridView;
import com.design.jhbrowser.speak.SpeakActivity;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.DataResourcesUtils;
import com.design.jhbrowser.utils.KYStringUtils;
import com.design.jhbrowser.utils.LayoutParamsUtils;
import com.design.jhbrowser.utils.LogUtils;
import com.design.jhbrowser.utils.OkHttpUtils;
import com.design.jhbrowser.utils.PullNavigationParser;
import com.design.jhbrowser.utils.Urls;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.widget.ColorLinearLayout;
import com.design.jhbrowser.utils.widget.ColorScrollView;
import com.design.jhbrowser.utils.widget.ColorTextView;
import com.design.jhbrowser.weather.WeatherJsonUtils;
import com.design.jhbrowser.weather.bean.LocationBean;
import com.design.jhbrowser.weather.bean.WeatherBean;
import com.design.jhbrowser.weather.listener.MyLocationListener;
import com.design.jhbrowser.weather.widget.WeatherActivity;
import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by AdamL on 2017/4/29.
 */

public class FragNavigation extends Fragment implements View.OnClickListener {

    private View view;
    private Context mContext;
    private ImageView imgSearch;
    private ImageView imgScan;
    private ImageView imgSpeak;

    private int width;
    private int screenWidth;
    private MyPageIndicator pageIndicator;
    private List<Navigation> data;
    private PageGridView pageGridView;

    int[] res = DataResourcesUtils.img_res;
    private ColorTextView tvCentigrade;
    private ColorTextView tvRegion;
    private ColorTextView tvWind;
    private MyLocationListener listener;
    private LocationClient mLocationClient;
    private LocalBroadcastManager broadcastManager;
    private IntentFilter intentFilter;
    private BroadcastReceiver receiver;

    private RecyclerView recycle;
    private List<RecyclerBean> list;
    private RecyclerBean recyclerBean;
    private FullyLinearLayoutManager manager;
    private ColorScrollView scrollView;
    private ColorTextView search;


    public final static FragNavigation getInstance(Context context) {
        FragNavigation frag = new FragNavigation();
        frag.mContext = context;
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, null);
        SpeechUtility.createUtility(getActivity(), SpeechConstant.APPID + "=59190a3c");
        //初始化控件
        initView();
        //设置recycle Navigation显示菜单
        setNavigationSetting();
        //定位当前城市信息
        setLocationCityInfo();
        //设置底部菜单栏
        aboutRecyclerSetting();
        return view;
    }

    /**
     * 设置底部菜单栏
     */
    private void aboutRecyclerSetting() {
        recycle = ViewFindUtils.find(view, R.id.recycleView);
        new FragRecycleView(recycle, mContext);
    }


    private void setLocationCityInfo() {
        listener = new MyLocationListener(mContext);
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(listener);
        setLocationOption();//定义setLocationOption方法
        mLocationClient.start();
        //设置广播接收者
        broadcastReceiverShow();
    }

    /**
     * 设置广播接收者
     */
    private void broadcastReceiverShow() {

        broadcastManager = LocalBroadcastManager.getInstance(mContext);
        intentFilter = new IntentFilter();
        intentFilter.addAction(DataResourcesUtils.FRAG_NAVIGATION);
        receiver = new BroadcastReceiver() {

            private List<LocationBean> locationBeens;

            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
                locationBeens = (List<LocationBean>) intent.getSerializableExtra("location");
                String flag = bundle.getString("flag");
                //通过flag判断
                setJudgeFlagEvent(flag, locationBeens);
            }
        };
        broadcastManager.registerReceiver(receiver, intentFilter);
    }

    /**
     * 通过Flag判断事件
     *
     * @param flag
     * @param locationBeens
     */
    private void setJudgeFlagEvent(String flag, List<LocationBean> locationBeens) {
        if (flag.equals("navigation")) {
            String city = locationBeens.get(0).getCity();
            //设置天气控件
            if (city == null) {
                setWeatherNavigation("哈尔滨市");
            } else {
                setWeatherNavigation(city);
            }
        }
    }

    /**
     * 定位信息
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开GPS
        option.setAddrType("all");//返回定位结果包含地址信息
        option.setPriority(LocationClientOption.NetWorkFirst);//设置网络优先
        option.setPriority(LocationClientOption.GpsFirst);//GPS
        option.disableCache(true);//禁止启用缓存定位
        mLocationClient.setLocOption(option);
    }

    /**
     * 设置天气控件
     */
    private void setWeatherNavigation(final String cityName) {
        try {
            String url = Urls.WEATHER + URLEncoder.encode(cityName, "utf-8");
            OkHttpUtils.ResultCallback<String> callback = new OkHttpUtils.ResultCallback<String>() {
                @Override
                public void onSuccess(String response) {
                    List<WeatherBean> lists = WeatherJsonUtils.getWeatherInfo(response);

                    LogUtils.i("json", lists.size() + "");
                    if (lists.size() != 0) {
                        WeatherBean weatherBean = lists.get(0);
                        String temperature = weatherBean.getTemperature();
                        //获取温度字符串
                        String temp = temperature.substring(temperature.lastIndexOf("高温") + 2, temperature.lastIndexOf("低温"));
                        tvCentigrade.setText(temp);
                        tvWind.setText(weatherBean.getWeather() + "  " + weatherBean.getWind());
                        tvRegion.setText(cityName);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    LogUtils.i("FAILURE", "navigation callback failure!!");
                }
            };
            OkHttpUtils.get(url, callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * //设置recycle Navigation显示菜单
     */
    private void setNavigationSetting() {
        pageIndicator = ViewFindUtils.find(view, R.id.pageindicator);
        //获取数据
        setDataResources();
        width = getResources().getDisplayMetrics().widthPixels / 5;
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        pageGridView = ViewFindUtils.find(view, R.id.pagingGridView);
        MyAdapter adapter = new MyAdapter();
        pageGridView.setAdapter(adapter);
        pageGridView.setOnItemClickListener(adapter);
        pageGridView.setPageIndicator(pageIndicator);
        pageGridView.setHorizontalFadingEdgeEnabled(false);
        tvCentigrade.setOnClickListener(this);
    }

    /**
     * 获取数据
     */
    private void setDataResources() {
        try {
            InputStream is = mContext.getAssets().open("navigation.xml");
            PullNavigationParser parser = new PullNavigationParser();
            data = parser.parse(is);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_centigrade:
//                Intent intent = new Intent(mContext, WeatherActivity.class);
//                startActivity(intent);
                break;
            case R.id.img_scan:
                Intent openCameraIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.search_to_activity:
                sendBroadcastInfo("search", "search");
                break;
            case R.id.img_speak:
//                startActivity(new Intent(mContext, SpeakActivity.class));
                beginioc();
                break;
        }
    }

    private void beginioc() {
        RecognizerDialog mDialog = new RecognizerDialog(getActivity(), new InitListener() {
            @Override
            public void onInit(int i) {

            }
        });

        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        mDialog.setListener(new RecognizerDialogListener() {

            List<String> chineseWordList = new ArrayList<String>();
            String Str = "";//最终形成的句子
            String str2 = "分词结果：";//要做的分词效果

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                String jsonObg = recognizerResult.getResultString();
                Gson gson = new Gson();
                Sentence sentence = gson.fromJson(jsonObg, new Sentence().getClass());
                List<Ws> wsList = sentence.getWs();

                for (Ws w : wsList) {
                    List<Cw> cwList = w.getCw();
                    for (Cw c : cwList) {
                        String chineseWord = c.getW();
                        chineseWordList.add(chineseWord);
                        Str += chineseWord;
                        str2 += chineseWord + ",";
                    }
                }
//                CustomToastUtils.showToast(getActivity(), Str + "\n" + str2);
                sendBroadcastInfo("speak", Str);
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });
        mDialog.show();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        scrollView = ViewFindUtils.find(view, R.id.scroll_frag);
        scrollView.setVerticalFadingEdgeEnabled(false);
        scrollView.setHorizontalFadingEdgeEnabled(false);
        //获取图片资源
        imgSearch = ViewFindUtils.find(view, R.id.img_search);
        imgScan = ViewFindUtils.find(view, R.id.img_scan);
        imgSpeak = ViewFindUtils.find(view, R.id.img_speak);
        //获取天气记录
        tvCentigrade = ViewFindUtils.find(view, R.id.tv_centigrade);
        tvRegion = ViewFindUtils.find(view, R.id.tv_region);
        tvWind = ViewFindUtils.find(view, R.id.tv_wind);
        //定义图片大小
        ColorLinearLayout.LayoutParams params = LayoutParamsUtils.setLayoutParams(getActivity(), imgSearch);
        imgSearch.setLayoutParams(params);
        imgScan.setLayoutParams(params);
        imgSpeak.setLayoutParams(params);

        search = ViewFindUtils.find(view, R.id.search_to_activity);
        search.setOnClickListener(this);
        imgScan.setOnClickListener(this);
        imgSpeak.setOnClickListener(this);
    }

    /**
     * 设置适配器
     */
    public class MyAdapter extends PageGridView.PagingAdapter<MyVH> implements PageGridView.OnItemClickListener {

        @Override
        public MyVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
            ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = width;
            params.width = width;
            view.setLayoutParams(params);
            return new MyVH(view);
        }

        @Override
        public void onBindViewHolder(MyVH holder, int position) {
            if (TextUtils.isEmpty(data.get(position).getTitle())) {
                holder.icon.setVisibility(view.GONE);
            } else {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), res[position]);
                holder.icon.setImageBitmap(bitmap);
                holder.icon.setVisibility(View.VISIBLE);
            }
            holder.tv_title.setText(data.get(position).getTitle());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public List getData() {
            return data;
        }

        @Override
        public Object getEmpty() {
            return "";
        }

        @Override
        public void onItemClick(PageGridView pageGridView, int position) {
            if (KYStringUtils.isTrue(position)) {
                String[] urls = DataResourcesUtils.url_res;
                String url = urls[position];
                sendBroadcastInfo("navItem", url);
                CustomToastUtils.showToast(mContext, position + "" + url);
            }
        }
    }

    private void sendBroadcastInfo(String flag, String url) {
        Intent intent = new Intent();
        intent.putExtra("flag", flag);
        intent.putExtra("key", url);
        intent.setAction(DataResourcesUtils.MAIN_ACTIVITY);
        LocalBroadcastManager.getInstance(mContext.getApplicationContext())
                .sendBroadcast(intent);
    }

    /**
     * 设置Recycle的Viewholder
     */
    public static class MyVH extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public ImageView icon;

        public MyVH(View itemView) {
            super(itemView);
            tv_title = ViewFindUtils.find(itemView, R.id.tv_title);
            icon = ViewFindUtils.find(itemView, R.id.icon);
        }
    }

    @Override
    public void onDestroy() {
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(listener);
        broadcastManager.unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            CustomToastUtils.showToast(mContext, scanResult);
        }
    }

}
