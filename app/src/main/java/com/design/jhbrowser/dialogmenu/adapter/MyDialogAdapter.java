package com.design.jhbrowser.dialogmenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.dialogmenu.bean.DialogBean;
import com.design.jhbrowser.utils.ViewFindUtils;

import java.util.List;

/**
 * Created by AdamL on 2017/5/13.
 */

public class MyDialogAdapter extends RecyclerView.Adapter<MyDialogAdapter.MyDialogVH> {

    private List<DialogBean> mData;

    public MyDialogAdapter(List<DialogBean> list) {
        this.mData = list;
    }

    @Override
    public MyDialogVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_menu, null);
        MyDialogVH vh = new MyDialogVH(view);
        vh.img_dialog = ViewFindUtils.find(view, R.id.img_menu);
        vh.tv_dialog = ViewFindUtils.find(view, R.id.tv_menu);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyDialogVH holder, int position) {
        DialogBean dialog = mData.get(position);
        holder.img_dialog.setImageResource(dialog.getImgId());
        holder.tv_dialog.setText(dialog.getMenuName());
    }

    @Override
    public int getItemCount() {
        return mData.isEmpty() ? 0 : mData.size();
    }

    static class MyDialogVH extends RecyclerView.ViewHolder {

        private ImageView img_dialog;
        private TextView tv_dialog;

        public MyDialogVH(View itemView) {
            super(itemView);
        }
    }

}
