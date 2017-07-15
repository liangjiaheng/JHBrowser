package com.design.jhbrowser.personalcenter.myitem.notes;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.personalcenter.myitem.notes.adapter.MyNoteAdapter;
import com.design.jhbrowser.personalcenter.myitem.notes.db.DBManager;
import com.design.jhbrowser.personalcenter.myitem.notes.model.Note;
import com.design.jhbrowser.utils.CustomToastUtils;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/30.
 */

public class NotesActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton addBtn;
    private DBManager dm;
    private List<Note> noteDataList = new ArrayList<>();
    private MyNoteAdapter adapter;
    private ListView listView;
    private TextView emptyListTextView;
    long waitTime = 2000;
    long touchTime = 0;
    private View view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        initToolbar();
        init();
    }


    private void initToolbar() {
        view = getWindow().getDecorView();
        Toolbar toolbar = ViewFindUtils.find(view, R.id.toolbar_note);
        toolbar.setTitle("记录");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void init() {
        dm = new DBManager(this);
        dm.readFromDB(noteDataList);
        listView = (ListView) findViewById(R.id.list);
        addBtn = (FloatingActionButton) findViewById(R.id.add);
        emptyListTextView = (TextView) findViewById(R.id.empty);
        addBtn.setOnClickListener(this);
        adapter = new MyNoteAdapter(this, noteDataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new NoteClickListener());
        listView.setOnItemLongClickListener(new NoteLongClickListener());
        setStatusBarColor();
        updateView();
    }


    //空数据更新
    private void updateView() {
        if (noteDataList.isEmpty()) {
            listView.setVisibility(View.GONE);
            emptyListTextView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            emptyListTextView.setVisibility(View.GONE);
        }
    }

    //设置状态栏同色
    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#ff6cb506"));
    }

    //button单击事件
    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, EditNoteActivity.class);
        switch (view.getId()) {
            case R.id.add:
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
        }
    }

    //listView单击事件
    private class NoteClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            MyNoteAdapter.ViewHolder viewHolder = (MyNoteAdapter.ViewHolder) view.getTag();
            String noteId = viewHolder.tvId.getText().toString().trim();
            Intent intent = new Intent(NotesActivity.this, EditNoteActivity.class);
            intent.putExtra("id", Integer.parseInt(noteId));
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    //listView长按事件
    private class NoteLongClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
            final Note note = ((MyNoteAdapter) adapterView.getAdapter()).getItem(i);
            if (note == null) {
                return true;
            }
            final int id = note.getId();

            AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);

            builder.setTitle("提示")
                    .setMessage("确定删除吗？？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DBManager.getInstance(NotesActivity.this).deleteNote(id);
                            adapter.removeItem(i);
                            updateView();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NotesActivity.this.finish();
                break;
            case R.id.action_about:
                break;
            case R.id.action_clean:
                if (listView.getCount() != 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NotesActivity.this);
                    builder.setTitle("提示")
                            .setMessage("确定清空吗？？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    for (int id = 0; id < 100; id++)
                                        DBManager.getInstance(NotesActivity.this).deleteNote(id);
                                    adapter.removeAllItem();
                                    updateView();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).create().show();
                } else {
                    CustomToastUtils.showToast(this, "没有记录！！");
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
