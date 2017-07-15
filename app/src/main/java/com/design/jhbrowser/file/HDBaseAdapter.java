package com.design.jhbrowser.file;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.design.jhbrowser.R;
import com.design.jhbrowser.utils.ConstantUtils;
import com.design.jhbrowser.utils.util.SharedPreferencesMgr;

/**
 * ClassName:HDBaseAdapter Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 *
 * @author snowdream
 * @Date 2011-11-30 下午11:06:18
 * @see
 * @since Ver 1.1
 */
public class HDBaseAdapter extends BaseAdapter {
    // viewmode
    public static final int VIEWMODE_LIST = 0;
    public static final int VIEWMODE_ICON = 1;

    private Context mcontext = null;
    private List<File> mfiles = null;

    private int mViewMode = VIEWMODE_ICON;

    public HDBaseAdapter(Context context, List<File> files) {
        mcontext = context;
        mfiles = files;
    }

    @Override
    public int getCount() {
        int msize = 0;

        if (mfiles != null)
            msize = mfiles.size();

        return msize;
    }

    @Override
    public File getItem(int position) {

        if ((position >= 0) && (position < this.getCount()))
            return mfiles.get(position);

        return null;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public void notifyDataSetChanged() {

        super.notifyDataSetChanged();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListHolder mListHolder = null;
        // GridHolder mGridHolder = null;

        switch (mViewMode) {
            case VIEWMODE_LIST: {
                if (convertView == null) {
                    convertView = LayoutInflater.from(mcontext).inflate(
                            R.layout.item_listview, null);
                    mListHolder = new ListHolder();
                    mListHolder.mfileIcon = (ImageView) convertView
                            .findViewById(R.id.listview_fileicon);
                    mListHolder.mfileName = (TextView) convertView
                            .findViewById(R.id.listview_filename);
                    mListHolder.mfileSize = (TextView) convertView
                            .findViewById(R.id.listview_filesize);
                    mListHolder.mfileTime = (TextView) convertView
                            .findViewById(R.id.listview_filetime);
                    convertView.setTag(mListHolder);
                } else {
                    mListHolder = (ListHolder) convertView.getTag();
                }

                // update the holder
                File f = this.getItem(position);
                if (f != null) {
                    int icon = this.getFileIcon(f);
                    if (icon == -1) {
                        Drawable drawable = this.getApkIcon(f.getAbsolutePath());
                        if (drawable != null) {
                            mListHolder.mfileIcon.setImageDrawable(drawable);
                        } else {
                            mListHolder.mfileIcon
                                    .setImageResource(R.mipmap.icon_file);
                        }
                    } else {
                        mListHolder.mfileIcon.setImageResource(icon);
                    }
                    mListHolder.mfileName.setText(f.getName());
                    if (f.isFile()) {
                        mListHolder.mfileSize.setText(this.getFileSize(f.length()));
                    } else {
                        mListHolder.mfileSize.setText("");

                    }
                    mListHolder.mfileTime
                            .setText(this.getFileTime(f.lastModified()));

                    if (SharedPreferencesMgr.getInt("theme", 0) == 1) {
                        mListHolder.mfileName.setTextColor(mcontext.getResources().getColor(R.color.textcolor_main_dark));
                        mListHolder.mfileSize.setTextColor(mcontext.getResources().getColor(R.color.textcolor_main_dark));
                        mListHolder.mfileTime.setTextColor(mcontext.getResources().getColor(R.color.textcolor_main_dark));
                    } else {
                        mListHolder.mfileName.setTextColor(mcontext.getResources().getColor(R.color.textcolor_main_normal));
                        mListHolder.mfileSize.setTextColor(mcontext.getResources().getColor(R.color.textcolor_main_normal));
                        mListHolder.mfileTime.setTextColor(mcontext.getResources().getColor(R.color.textcolor_main_normal));
                    }
                }
            }
            break;
            default:
                break;
        }

        return convertView;
    }

    public int getFileIcon(File f) {
        int icon = 0;

        String str = ConstantUtils.getFileIcon(f);
        if (str == null) {
            icon = -1;
        } else {
            Resources res = mcontext.getResources();
            icon = res
                    .getIdentifier(str, "mipmap", mcontext.getPackageName());

            if (icon <= 0)
                icon = R.mipmap.icon_file;
        }

        return icon;
    }

    public Drawable getApkIcon(String path) {
        PackageManager pm = mcontext.getPackageManager();
        PackageInfo info = pm.getPackageArchiveInfo(path,
                PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;

            if (Build.VERSION.SDK_INT >= 8) {
                appInfo.sourceDir = path;
                appInfo.publicSourceDir = path;
            }

            return appInfo.loadIcon(pm);
        }
        return null;
    }

    public String getFileTime(long filetime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String ftime = formatter.format(new Date(filetime));
        return ftime;
    }

    public String getFileSize(long filesize) {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuffer mstrbuf = new StringBuffer();

        if (filesize < 1024) {
            mstrbuf.append(filesize);
            mstrbuf.append(" B");
        } else if (filesize < 1048576) {
            mstrbuf.append(df.format((double) filesize / 1024));
            mstrbuf.append(" K");
        } else if (filesize < 1073741824) {
            mstrbuf.append(df.format((double) filesize / 1048576));
            mstrbuf.append(" M");
        } else {
            mstrbuf.append(df.format((double) filesize / 1073741824));
            mstrbuf.append(" G");
        }

        df = null;

        return mstrbuf.toString();
    }

    public int getViewMode() {
        return mViewMode;
    }

    public void setViewMode(int ViewMode) {
        mViewMode = ViewMode;
    }

    static class ListHolder {
        ImageView mfileIcon;
        TextView mfileName;
        TextView mfileSize;
        TextView mfileTime;
    }

}
