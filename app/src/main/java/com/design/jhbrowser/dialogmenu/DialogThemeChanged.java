package com.design.jhbrowser.dialogmenu;

import android.view.View;
import android.widget.LinearLayout;

import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

/**
 * Created by AdamL on 2017/5/14.
 */

public class DialogThemeChanged {


    private static LinearLayout popLayout;
    private static View viewLayout;

    public static void changedTheme(MyDialogMenuActivity activity) {

        View view = activity.getWindow().getDecorView();

        popLayout = ViewFindUtils.find(view, R.id.pop_layout);
        viewLayout = ViewFindUtils.find(view, R.id.layout_login);

        if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
            popLayout.setBackgroundColor(activity.getResources().getColor(R.color.bg_main_dark));
            viewLayout.setBackgroundColor(activity.getResources().getColor(R.color.bg_main_dark));

        } else {
            popLayout.setBackgroundColor(activity.getResources().getColor(R.color.bg_main_normal));
            viewLayout.setBackgroundColor(activity.getResources().getColor(R.color.bg_main_normal));
        }

    }


}
