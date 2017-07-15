/*
 * Copyright (c) 2011 yang hui <yanghui1986527@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License v2 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 021110-1307, USA.
 */

package com.design.jhbrowser.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ActionBarContainer;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.file.broadcastreceiver.SystemBroadCastReceiver;
import com.design.jhbrowser.file.interfaces.ISDCardListener;
import com.design.jhbrowser.utils.ConstantUtils;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;


public class HDExplorerActivity extends BaseActivity implements
        OnItemClickListener, ISDCardListener {

    private static final String TAG = "HDExplorer";
    // Boolean Flags
    private boolean misFullScreen = false;

    // Dialogs ID
    private final int FOLDER_CREATE = 0;
    private final int FILE_RENAME = 1;
    private final int FILE_DETAILS = 2;
    private final int FILE_DELETE = 3;

    // the data source
    List<File> mfiles = null;
    List<File> mbackwardfiles = null;
    List<File> mforwardfiles = null;

    // BaseAdapter
    HDBaseAdapter madapter = null;

    // String
    private String mSDCardPath = null;
    private String mRootPath = null;

    // String
    private File mCurrentPathFile = null;
    private File mRenameFile = null;
    private File mDetailFile = null;
    private File mDeleteFile = null;

    // cut and copy
    private File mCutOrCopyFile = null;
    private int mAction = ACTION_NONE;

    private boolean misShowHiddenFiles = false;

    private static final int ACTION_NONE = 0;
    private static final int ACTION_CUT = 1;
    private static final int ACTION_COPY = 2;

    private static final int REQ_SYSTEM_SETTINGS = 0;

    private View view;

    // ListView
    ListView mListView = null;

    // TextView
    TextView mEmptyView = null;
    private TextView tvPaste;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (misFullScreen) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_explorer);

        view = getWindow().getDecorView();

        checkEnvironment();
        initUI();
        initData();

        tvPaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paste();
            }
        });

        SystemBroadCastReceiver.addListener(this);
    }

    private void initUI() {
        initToolbar();
        initMainUI();
    }

    private void initToolbar() {
        toolbar = ViewFindUtils.find(view, R.id.file_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                upward();
                break;
        }
        return true;
    }

    private void initData() {
        File file = null;
        if (mCurrentPathFile != null) {
            if (mCurrentPathFile.isDirectory()) {
                file = mCurrentPathFile;
            } else {
                file = mCurrentPathFile.getParentFile();
            }
        } else {
            file = new File(mSDCardPath);
        }
        loadSettings();
        if (file != null) {
            open(file, false);
        }
    }

    /**
     * checkEnvironment: 检查SDCard和根路径
     *
     * @param
     * @return
     * @throws
     */
    private void checkEnvironment() {

        File f = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            f = Environment.getExternalStorageDirectory();// 获取sd卡目录
            if (f != null) {
                mSDCardPath = f.getAbsolutePath();
            }

            f = Environment.getRootDirectory();// 获取根目录
            if (f != null) {
                mRootPath = f.getAbsolutePath();
            }
        }

    }


    public void setViewMode(int viewmode) {
        switch (viewmode) {
            case HDBaseAdapter.VIEWMODE_LIST:
                madapter.setViewMode(HDBaseAdapter.VIEWMODE_LIST);
                mListView.setAdapter(madapter);
                madapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

    }

    public void initMainUI() {

        mEmptyView = (TextView) findViewById(R.id.empty);

        mListView = (ListView) findViewById(R.id.listview);

        mListView.setVerticalFadingEdgeEnabled(false);

        registerForContextMenu(mListView);
        mListView.setOnItemClickListener(this);

        mListView.setEmptyView(mEmptyView);

        mfiles = new ArrayList<>();

        mbackwardfiles = new ArrayList<File>();

        mforwardfiles = new ArrayList<File>();

        madapter = new HDBaseAdapter(this, mfiles);

        mListView.setAdapter(madapter);

        setViewMode(HDBaseAdapter.VIEWMODE_LIST);

        tvPaste = ViewFindUtils.find(view, R.id.file_paste);

    }

    private void addItem(File f) {
        mfiles.add(f);
        madapter.notifyDataSetChanged();
    }

    private void deleteItem(File f) {
        mfiles.remove(f);
        madapter.notifyDataSetChanged();
    }

    private void deleteAllItems() {
        mfiles.clear();
        madapter.notifyDataSetChanged();
    }

    private void open(File f, boolean misAddToBackWardFiles) {
        if (f == null)
            return;

        if (!f.exists())
            return;

        if (!f.canRead())
            return;

        if (f.isFile()) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String type = ConstantUtils.getMIMEType(f);
                intent.setDataAndType(Uri.fromFile(f), type);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }

        } else if (f.isDirectory()) {
            deleteAllItems();

            mCurrentPathFile = f;
            setTitle(mCurrentPathFile.getAbsolutePath());

            if (misAddToBackWardFiles) {
                mbackwardfiles.add(mCurrentPathFile.getParentFile());
            }

            File[] files = f.listFiles();

            // 排序
            Arrays.sort(files, new FileComparator());

            for (File file : files) {
                if (!misShowHiddenFiles && file.isHidden()) {
                    continue;
                }
                addItem(file);
            }
        }
    }

    private void openwith(File f) {
        if (!f.exists())
            return;

        if (!f.canRead())
            return;

        if (f.isDirectory()) {
            open(f, true);
            return;
        }

        if (f.isFile()) {
            try {
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                String type = ConstantUtils.getMIMEType(f);
                intent.setDataAndType(Uri.fromFile(f), type);
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 复制文件
     **/
    public boolean copyFile(File src, File tar) throws Exception {
        if (src.isFile()) {
            InputStream is = new FileInputStream(src);
            OutputStream op = new FileOutputStream(tar);
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(op);
            byte[] bt = new byte[1024 * 8];
            int len = bis.read(bt);
            while (len != -1) {
                bos.write(bt, 0, len);
                len = bis.read(bt);
            }
            bis.close();
            bos.close();
        }
        if (src.isDirectory()) {
            File[] f = src.listFiles();
            tar.mkdir();
            for (int i = 0; i < f.length; i++) {
                copyFile(f[i].getAbsoluteFile(), new File(tar.getAbsoluteFile()
                        + File.separator + f[i].getName()));
            }
        }
        return true;
    }

    /**
     * 移动文件
     **/
    public boolean moveFile(File src, File tar) throws Exception {
        if (copyFile(src, tar)) {
            deleteFile(src);
            return true;
        }
        return false;
    }

    /**
     * 删除文件
     **/
    public void deleteFile(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null && files.length > 0) {
                for (int i = 0; i < files.length; ++i) {
                    deleteFile(files[i]);
                }
            }
        }
        f.delete();
    }

    private void copy(File f) {
        mAction = ACTION_COPY;
        mCutOrCopyFile = f;

        if (mCutOrCopyFile != null) {
            tvPaste.setVisibility(View.VISIBLE);
        }
    }

    private void cut(File f) {
        mAction = ACTION_CUT;
        mCutOrCopyFile = f;

        if (mCutOrCopyFile != null) {
            tvPaste.setVisibility(View.VISIBLE);
        }
    }

    private void paste() {
        switch (mAction) {
            case ACTION_COPY:
                if ((mCutOrCopyFile != null) && (mCurrentPathFile != null)) {
                    String destname = combineFilename(mCutOrCopyFile,
                            mCurrentPathFile);
                    File src = mCutOrCopyFile;
                    File dest = new File(destname);
                    boolean misFileExist = checkFileExist(dest);
                    if (misFileExist) {
                        CustomToastUtils.showToast(this, "该文件已存在");
                    } else {
                        try {
                            copyFile(src, dest);
                            addItem(dest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                break;
            case ACTION_CUT:
                if ((mCutOrCopyFile != null) && (mCurrentPathFile != null)) {
                    String destname = combineFilename(mCutOrCopyFile,
                            mCurrentPathFile);
                    File src = mCutOrCopyFile;
                    File dest = new File(destname);
                    boolean misFileExist = checkFileExist(dest);
                    if (misFileExist) {
                        // 该文件已经存在
                        CustomToastUtils.showToast(this, "该文件已存在");
                    } else {
                        try {
                            moveFile(src, dest);
                            addItem(dest);
                            deleteItem(src);
                            mCutOrCopyFile = null;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            default:
                break;
        }
        tvPaste.setVisibility(View.GONE);
    }

    private void rename(File f) {
        mRenameFile = f;
        showDialog(FILE_RENAME);
    }

    private void showdetails(File f) {
        mDetailFile = f;
        showDialog(FILE_DETAILS);
    }


    public void upward() {
        if ((mCurrentPathFile.getAbsolutePath()).equals(mSDCardPath)) {
            finish();
        } else {
            File f = mCurrentPathFile;
            if (!mCurrentPathFile.getAbsolutePath().equals(mRootPath)) {
                open(f.getParentFile(), true);
            }
        }
    }

//    public void backward() {
//        if (mbackwardfiles.size() > 0) {
//            File backpathFile = mbackwardfiles.get(mbackwardfiles.size() - 1);
//            open(backpathFile, false);
//            mforwardfiles.add(backpathFile);
//            mbackwardfiles.remove(mbackwardfiles.size() - 1);
//        }
//    }

//    public void forward() {
//
//        if (mforwardfiles.size() > 0) {
//            File forwardpathFile = mforwardfiles.get(mforwardfiles.size() - 1);
//            open(forwardpathFile, true);
//            mbackwardfiles.add(forwardpathFile);
//            mforwardfiles.remove(mforwardfiles.size() - 1);
//        }
//    }
//
//    public void refresh() {
//        if (mCurrentPathFile != null) {
//            open(mCurrentPathFile, false);
//        }
//    }

    private void createFolder(File newFile) {
        if (newFile.exists()) {
            Toast.makeText(this, "文件已经存在", Toast.LENGTH_SHORT).show();
        } else {
            try {
                if (newFile.mkdir()) {
                    // hander.sendEmptyMessage(0); // 成功
                    addItem(newFile);
                } else {
                    Toast.makeText(this, "文件创建失败", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(this, ex.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkFileExist(File f) {
        boolean ret = false;

        File[] files = mCurrentPathFile.listFiles();

        for (File file : files) {
            if ((f.getName()).equals(file.getName())) {
                ret = true;
                break;
            }
        }

        return ret;
    }

    public String combineFilename(File src, File dest) {
        String destname = null;

        if (src == null || dest == null || !dest.isDirectory()) {
            return destname;
        }

        destname = dest.getAbsolutePath() + "/" + src.getName();

        return destname;
    }

    /**
     * <b>loadSettings</b><br/>
     * 读取Preference值，并加载设置参数�??<br/>
     *
     * @param � ?
     * @return 设置成功，返回true，否则返回false�?
     */
    public boolean loadSettings() {
        boolean ret = true;

        try {
            String key_showhidden = getResources().getString(
                    R.string.preference_showhidden_key);
            boolean default_value_showhidden = Boolean
                    .parseBoolean(getResources().getString(
                            R.string.preference_showhidden_default_value));

            SharedPreferences settings = PreferenceManager
                    .getDefaultSharedPreferences(this);
            misShowHiddenFiles = settings.getBoolean(key_showhidden,
                    default_value_showhidden);
        } catch (NotFoundException e) {
            ret = false;
            e.printStackTrace();
        } catch (ClassCastException e) {
            ret = false;
            e.printStackTrace();
        }

        return ret;
    }

    /**
     * onActivityResult: onActivityResult
     *
     * @param requestCode The value of requestCode
     * @param resultCode  The value of resultCode
     * @param data        The data from the last Activity
     * @return
     * @throws
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_SYSTEM_SETTINGS) {
            loadSettings();
            open(mCurrentPathFile, false);
        }
    }

    /**
     * onConfigurationChanged: onConfigurationChanged
     *
     * @param newConfig Configuration
     * @return
     * @throws
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        try {
            super.onConfigurationChanged(newConfig);
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            }
        } catch (Exception e) {
        }
    }

    /**
     * onDestroy: onDestroy
     *
     * @param
     * @return
     * @throws
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SystemBroadCastReceiver.removeListener(this);
    }


    /**
     * onKeyDown: KeyEvent
     *
     * @param keyCode The value of keyCode
     * @param event   KeyEvent
     * @return boolean True if the KeyEvent has been dealt with, otherwise
     * false.
     * @throws
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                upward();
                return true;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * <b>onCreateContextMenu</b><br/>
     * 长按文件管理器中的文件，弹出上下文菜单
     *
     * @param menu     上下文菜单
     * @param v        正在被创建的菜单视图
     * @param menuInfo 上下文菜单信息
     * @return �?
     * @throws � ?
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context, menu);

        AdapterContextMenuInfo info = null;

        try {
            info = (AdapterContextMenuInfo) menuInfo;
        } catch (ClassCastException e) {
            return;
        }

        File mselectedFile = madapter.getItem(info.position);
        if (mselectedFile != null) {
            menu.setHeaderTitle(mselectedFile.getName());
        }
    }

    /**
     * <b>onContextItemSelected</b><br/>
     * 点击上下文菜单�?�项时的事件处理
     *
     * @param item 被点击的菜单�?
     * @return true, 表示事件已经处理；false，表示未处理�?
     * @throws � ?
     */
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();
        File f = madapter.getItem(info.position);
        if (f == null)
            return false;
        switch (item.getItemId()) {
            case R.id.openwith:
                openwith(f);
                return true;
            case R.id.copy:
                copy(f);
                return true;
            case R.id.cut:
                cut(f);
                return true;
            case R.id.rename:
                rename(f);
                return true;
            case R.id.delete:
                mDeleteFile = f;
                showDialog(FILE_DELETE);
                return true;
            case R.id.attribute:
                showdetails(f);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        File mselectedFile = madapter.getItem(position);
        if (mselectedFile != null)
            open(mselectedFile, true);
    }

    /**
     * onCreateDialog: Create All The Dialogs.
     *
     * @param id The id of the dialog which should be created
     * @return Dialog The dialog which has been created.
     * @throws
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        Log.i(TAG, "onCreateDialog");
        switch (id) {
            case FOLDER_CREATE:
                AlertDialog.Builder mcreatedialog = new AlertDialog.Builder(this);
                View layout = LayoutInflater.from(this).inflate(
                        R.layout.file_create, null);
                final EditText text = (EditText) layout
                        .findViewById(R.id.file_name);
                mcreatedialog.setTitle(R.string.dialog_create_folder_title);
                mcreatedialog.setView(layout);
                mcreatedialog.setPositiveButton(R.string.button_text_yes,
                        new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String newName = text.getText().toString().trim();
                                if (newName.length() == 0) {
                                    // Toast.makeText(this,
                                    // R.string.file_namecannotempty,
                                    // Toast.LENGTH_SHORT)
                                    // .show();
                                    return;
                                }
                                String fullFileName = mCurrentPathFile + "/"
                                        + newName;
                                File newFile = new File(fullFileName);
                                createFolder(newFile);
                            }
                        });
                mcreatedialog.setNegativeButton(R.string.button_text_no,
                        new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                return mcreatedialog.create();
            case FILE_RENAME:
                if (mRenameFile == null) {
                    return null;
                }

                AlertDialog.Builder mrenamedialog = new AlertDialog.Builder(this);
                View renamelayout = LayoutInflater.from(this).inflate(
                        R.layout.file_rename, null);
                final EditText renametext = (EditText) renamelayout
                        .findViewById(R.id.file_name);
                renametext.setText(mRenameFile.getName());
                mrenamedialog.setTitle(R.string.dialog_file_rename_title);
                mrenamedialog.setView(renamelayout);
                mrenamedialog.setPositiveButton(R.string.button_text_yes,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialoginterface,
                                                int i) {
                                String path = mRenameFile.getParentFile().getPath();
                                String newName = renametext.getText().toString()
                                        .trim();
                                if (newName.equalsIgnoreCase(mRenameFile.getName())) {
                                    return;
                                }
                                if (newName.length() == 0) {
                                    return;
                                }
                                String fullFileName = path + "/" + newName;

                                File newFile = new File(fullFileName);
                                if (newFile.exists()) {
                                } else {
                                    try {
                                        if (mRenameFile.renameTo(newFile)) {
                                            deleteItem(mRenameFile);
                                            addItem(newFile);
                                            mRenameFile = null;
                                        } else {
                                        }
                                    } catch (Exception ex) {
                                    }
                                }
                            }
                        }).setNegativeButton(R.string.button_text_no,
                        new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                return mrenamedialog.create();
            case FILE_DETAILS:
                if (mDetailFile == null) {
                    return null;
                }
                AlertDialog.Builder mdetaildialog = new AlertDialog.Builder(this);
                View detaillayout = LayoutInflater.from(this).inflate(
                        R.layout.file_info, null);

                ((TextView) detaillayout.findViewById(R.id.file_name))
                        .setText(mDetailFile.getName());
                ((TextView) detaillayout.findViewById(R.id.file_lastmodified))
                        .setText(getFileTime(mDetailFile.lastModified()));
                ((TextView) detaillayout.findViewById(R.id.file_size))
                        .setText(getFileSize(mDetailFile.length()));
                mdetaildialog.setTitle(R.string.dialog_file_details_title);
                mdetaildialog.setView(detaillayout);
                mdetaildialog.setPositiveButton(R.string.button_text_yes,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialoginterface,
                                                int i) {
                                mDetailFile = null;
                                dialoginterface.cancel();
                            }
                        });
                return mdetaildialog.create();
            case FILE_DELETE:
                if (mDeleteFile == null) {
                    return null;
                }

                String message = getString(R.string.dialog_file_delete_message);
                message = String.format(message, mDeleteFile.getName());

                AlertDialog.Builder mdeletedialog = new AlertDialog.Builder(this);
                mdeletedialog.setTitle(R.string.dialog_file_delete_title);
                mdeletedialog.setMessage(message);
                mdeletedialog.setPositiveButton(R.string.button_text_yes,
                        new OnClickListener() {
                            public void onClick(DialogInterface dialoginterface,
                                                int i) {
                                deleteFile(mDeleteFile);
                                deleteItem(mDeleteFile);
                                mDeleteFile = null;
                            }
                        }).setNegativeButton(R.string.button_text_no,
                        new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                return mdeletedialog.create();
            default:
                break;
        }
        return super.onCreateDialog(id);

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

    /**
     * onPrepareDialog: Update the Dialogs if needed.
     *
     * @param id The id of the dialog which should be updated.
     * @throws
     */
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        Log.i(TAG, "onPrepareDialog");
        switch (id) {
            case FOLDER_CREATE:
                break;
            case FILE_RENAME:
                if (mRenameFile == null) {
                    return;
                }
                AlertDialog mrenamedialog = (AlertDialog) dialog;

                EditText renametext = (EditText) mrenamedialog
                        .findViewById(R.id.file_name);
                renametext.setText(mRenameFile.getName());
                break;
            case FILE_DETAILS:
                if (mDetailFile == null) {
                    return;
                }
                AlertDialog mdetaildialog = (AlertDialog) dialog;

                ((TextView) mdetaildialog.findViewById(R.id.file_name))
                        .setText(mDetailFile.getName());
                ((TextView) mdetaildialog.findViewById(R.id.file_lastmodified))
                        .setText(getFileTime(mDetailFile.lastModified()));
                ((TextView) mdetaildialog.findViewById(R.id.file_size))
                        .setText(getFileSize(mDetailFile.length()));
                break;
            case FILE_DELETE:
                if (mDeleteFile == null) {
                    return;
                }

                String message = getString(R.string.dialog_file_delete_message);
                message = String.format(message, mDeleteFile.getName());

                AlertDialog mdeletedialog = (AlertDialog) dialog;
                mdeletedialog.setMessage(message);
                break;
            default:
                break;
        }
        super.onPrepareDialog(id, dialog);
    }

    /**
     *
     * onCreateOptionsMenu: Create the Menu if needed.
     *
     * @param menu
     *            The Menu which should be created.
     * @throws
     */

    /**
     * onPrepareOptionsMenu: Update the OptionsMenu if needed.
     *
     * @param menu The Menu which should be updated.
     * @throws
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.i(TAG, "onPrepareOptionsMenu");
        return true;
    }

    /**
     * onOptionsItemSelected: Update the Dialogs if needed.
     *
     * @return true if the event has been dealt with,otherwise false.
     * @throws
     */

	/*
     * (non-Javadoc)
	 * 
	 * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardBadRemoval()
	 */
    @Override
    public void onSDCardBadRemoval() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardButton()
     */
    @Override
    public void onSDCardButton() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardChecking()
     */
    @Override
    public void onSDCardChecking() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardEject()
     */
    @Override
    public void onSDCardEject() {
        deleteAllItems();
        Toast.makeText(this, R.string.toast_warn_scard_ejected,
                Toast.LENGTH_SHORT).show();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardMounted()
     */
    @Override
    public void onSDCardMounted() {
        initData();
    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardNoFS()
     */
    @Override
    public void onSDCardNoFS() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardRemoved()
     */
    @Override
    public void onSDCardRemoved() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardScannerFinshed()
     */
    @Override
    public void onSDCardScannerFinshed() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardScannerScaning()
     */
    @Override
    public void onSDCardScannerScaning() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardScannerStarted()
     */
    @Override
    public void onSDCardScannerStarted() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardShared()
     */
    @Override
    public void onSDCardShared() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardUnMountable()
     */
    @Override
    public void onSDCardUnMountable() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see com.hd.explorer.interfaces.ISDCardListener#onSDCardUnMounted()
     */
    @Override
    public void onSDCardUnMounted() {
        // TODO Auto-generated method stub

    }

}