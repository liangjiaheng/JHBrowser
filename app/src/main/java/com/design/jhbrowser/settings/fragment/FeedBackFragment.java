package com.design.jhbrowser.settings.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.design.jhbrowser.R;

/**
 * Created by AdamL on 2017/5/27.
 */

public class FeedBackFragment extends Fragment {

    private Context mContext;
    private View view;

    public final static FeedBackFragment getInstance(Context context) {
        FeedBackFragment fragment = new FeedBackFragment();
        fragment.mContext = context;
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_back_feedback, null);

        return view;
    }
}
