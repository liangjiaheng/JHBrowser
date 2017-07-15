package com.design.jhbrowser.settings.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.lock.lockview.LockSettingsActivity;
import com.design.jhbrowser.utils.ViewFindUtils;

/**
 * Created by AdamL on 2017/5/27.
 */

public class PersonalFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private View view;
    private LinearLayout setPassword;

    public final static PersonalFragment getInstance(Context context) {
        PersonalFragment fragPersonal = new PersonalFragment();
        fragPersonal.mContext = context;
        return fragPersonal;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_back_personal, null);
        initView();
        return view;
    }

    private void initView() {
        setPassword = ViewFindUtils.find(view, R.id.set_lock_password);
        setPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_lock_password:
                startActivity(new Intent(getActivity(), LockSettingsActivity.class));
                break;
        }
    }
}
