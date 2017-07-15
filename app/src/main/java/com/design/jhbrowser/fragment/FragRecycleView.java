package com.design.jhbrowser.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.design.jhbrowser.R;
import com.design.jhbrowser.fragment.adapter.FullyLinearLayoutManager;
import com.design.jhbrowser.fragment.adapter.MyRecyclerAdapter;
import com.design.jhbrowser.fragment.bean.RecyclerBean;
import com.design.jhbrowser.fragment.listener.RecyclerItemClickListener;
import com.design.jhbrowser.utils.CustomToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/8.
 */

public class FragRecycleView {

    private Context context;
    private RecyclerView recycle;
    private List<RecyclerBean> list;
    private RecyclerBean recyclerBean;
    private String[] title;

    public FragRecycleView(RecyclerView recycle, Context context) {
        this.context = context;
        this.recycle = recycle;
        aboutRecyclerSetting();
    }

    private void aboutRecyclerSetting() {
        //设置数据源
        initData();
        //设置布局管理器
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(manager);
        //设置适配器
        MyRecyclerAdapter adapter = new MyRecyclerAdapter(list);
        recycle.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //设置监听器
        setRecyclerItemListener();

    }

    private void setRecyclerItemListener() {
        recycle.addOnItemTouchListener(new RecyclerItemClickListener(context, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CustomToastUtils.showToast(context, title[position] + "");
            }
        }));

    }

    private void initData() {
        list = new ArrayList<>();
        title = new String[]{"手机酷站", "热点新闻", "搞笑视频", "明星资讯"};
        for (int i = 0; i < 4; i++) {
            recyclerBean = new RecyclerBean();
            recyclerBean.setImgId(R.mipmap.img_search_);
            recyclerBean.setTvtitle(title[i]);
            list.add(recyclerBean);
        }
    }
}
