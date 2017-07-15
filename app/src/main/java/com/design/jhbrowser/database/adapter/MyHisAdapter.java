package com.design.jhbrowser.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.design.jhbrowser.R;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

import java.util.List;

/**
 * Created by AdamL on 2017/5/19.
 */

public class MyHisAdapter extends BaseAdapter {

    private Context mContext;
    private List<HistoryBean> mData;
    private LayoutInflater mInflater = null;

    public MyHisAdapter(Context context, List<HistoryBean> list) {
        this.mContext = context;
        this.mData = list;
    }

    @Override
    public int getCount() {
        return mData.isEmpty() ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
//            convertView = mInflater.inflate(R.layout.item_frag_history, null);
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_frag_history, null);
            holder.imgLogo = ViewFindUtils.find(convertView, R.id.img_logo);
            holder.tvTitle = ViewFindUtils.find(convertView, R.id.tv_his_title);
            holder.tvUrl = ViewFindUtils.find(convertView, R.id.tv_his_url);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mData.get(position).getImgUrl()).into(holder.imgLogo);
        holder.tvTitle.setText(mData.get(position).getTitle());
        holder.tvUrl.setText(mData.get(position).getUrl());

        if (SharedPreferencesMgr.getInt("theme", 0) == 0) {
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.textcolor_main_normal));
            holder.tvUrl.setTextColor(mContext.getResources().getColor(R.color.textcolor_main_normal));
        } else {

        }

        return convertView;
    }

    public class ViewHolder {
        private ImageView imgLogo;
        private TextView tvTitle;
        private TextView tvUrl;
    }

}
