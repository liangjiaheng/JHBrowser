package com.design.jhbrowser.login.optiondate;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;
import com.design.library.wincal.DatePickerFragment;
import com.design.library.wincal.DateSelectListener;

public class OptionDataActivity extends BaseActivity {
    private DatePickerFragment mDatePickerFragment;
    private DatePickerFragment mDatePickerDialogFragment;

    private int mSelectedMonth;
    private int mSelectedYear;
    private int mSelectedDate;
    private Toolbar toolbar;
    private View view;
    private TextView tvConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_data);

        setUpTitleBar();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mDatePickerFragment = new DatePickerFragment();
        if (savedInstanceState != null) {

            mDatePickerFragment.restoreStatesFromKey(savedInstanceState, "CALENDAR_SAVED_STATE");
        }

        transaction.replace(R.id.container, mDatePickerFragment);
        transaction.commit();


        DateSelectListener dateSelectListener = new DateSelectListener() {
            @Override
            public void onSelectDate(int date, int month, int year) {

                mSelectedDate = date;
                mSelectedMonth = month;
                mSelectedYear = year;
                String data = year + "-" + month + "-" + date;
                SharedPreferencesMgr.setString("data", data);
                SharedPreferencesMgr.setInt("year", year);
            }
        };

        mDatePickerFragment.setDateSelectListener(dateSelectListener);
    }

    private void setUpTitleBar() {
        view = getWindow().getDecorView();
        tvConfirm = ViewFindUtils.find(view, R.id.tv_confirm_option);
        toolbar = ViewFindUtils.find(view, R.id.toolbar_option);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerFragment.setClickListener();
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                OptionDataActivity.this.finish();
                break;
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (mDatePickerFragment != null) {
            mDatePickerFragment.saveStatesToKey(outState, "CALENDAR_SAVED_STATE");
        }

        if (mDatePickerDialogFragment != null) {
            mDatePickerDialogFragment.saveStatesToKey(outState,
                    "DIALOG_CALENDAR_SAVED_STATE");
        }
    }

}
