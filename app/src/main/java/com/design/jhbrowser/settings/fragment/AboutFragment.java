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

public class AboutFragment extends Fragment {

    private Context mContext;
    private View view;

    public final static AboutFragment getInstance(Context context) {
        AboutFragment fragment = new AboutFragment();
        fragment.mContext = context;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_back_about, null);

        return view;


    }
}
