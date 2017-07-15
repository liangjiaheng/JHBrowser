package com.design.jhbrowser.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.fragment.bean.RecyclerBean;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.widget.ColorTextView;

import java.util.List;

/**
 * Created by AdamL on 2017/5/8.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private List<RecyclerBean> list;

    public MyRecyclerAdapter(List<RecyclerBean> recyclerBeen) {
        this.list = recyclerBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, null);
        ViewHolder vh = new ViewHolder(view);
        vh.img_recycle = ViewFindUtils.find(view, R.id.img_recycle);
        vh.tv_recycle = ViewFindUtils.find(view, R.id.tv_recycle);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerBean recyclerBean = list.get(position);
        holder.img_recycle.setImageResource(recyclerBean.getImgId());
        holder.tv_recycle.setText(recyclerBean.getTvtitle());
    }

    @Override
    public int getItemCount() {
        return list.isEmpty() ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_recycle;
        private ColorTextView tv_recycle;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
