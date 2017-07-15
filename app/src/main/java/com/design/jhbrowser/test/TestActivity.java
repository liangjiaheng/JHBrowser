package com.design.jhbrowser.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.design.jhbrowser.R;
import com.design.jhbrowser.test.interfaces.HttpCallback;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.JsonResponsePaserUtils;
import com.design.jhbrowser.utils.XUtils;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        String url = "https://open.seriousapps.cn/4/tab/home_page.json?tab_id=0&city_id=140&lat=39.90796&lng=116.40195&page=1";
        XUtils.Get(url, null, new HttpCallback<String>() {
            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);

                JsonResponsePaserUtils paser = new JsonResponsePaserUtils();

                try {
                    List<ShopEntry> shopEntrys = (List<ShopEntry>) paser.parse(new TypeToken<List<ShopEntry>>() {
                    }.getType(), null, result);

                    ShopEntry shopEntry = shopEntrys.get(0);
                    ShopEntry.DataBean data = shopEntry.getData();
                    List<ShopEntry.DataBean.ListBean> list = data.getList();

                    CustomToastUtils.showToast(TestActivity.this, "" + list.get(2).getEnjoy_url());


                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }
}
