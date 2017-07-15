package com.design.jhbrowser.personalcenter.myitem.notes;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.MainActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.personalcenter.myitem.calendar.CalendarActivity;
import com.design.jhbrowser.personalcenter.myitem.notes.db.DBManager;
import com.design.jhbrowser.personalcenter.myitem.notes.model.Note;
import com.design.jhbrowser.utils.ViewFindUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by AdamL on 2017/5/30.
 */

public class EditNoteActivity extends BaseActivity implements View.OnClickListener {

    private EditText titleEt;
    private EditText contentEt;
    private FloatingActionButton saveBtn;
    private int noteID = -1;
    private DBManager dbManager;
    private View view;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        initToolbar();

        init();

    }

    private void initToolbar() {
        view = getWindow().getDecorView();
        toolbar = ViewFindUtils.find(view, R.id.toolbar_edit);
        toolbar.setTitle("书写内容");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    private void init() {
        dbManager = new DBManager(this);
        titleEt = (EditText) findViewById(R.id.note_title);
        contentEt = (EditText) findViewById(R.id.note_content);
        saveBtn = (FloatingActionButton) findViewById(R.id.save);
        saveBtn.setOnClickListener(this);
        noteID = getIntent().getIntExtra("id", -1);
        if (noteID != -1) {
            showNoteData(noteID);
        }
        setStausBarColor();
    }

    /**
     * 设置状态栏同色
     */
    private void setStausBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#ff6cb506"));
    }

    /**
     * 显示更新的数据
     *
     * @param id
     */
    private void showNoteData(int id) {
        Note note = dbManager.readData(id);
        titleEt.setText(note.getTitle());
        contentEt.setText(note.getContent());
        //控制光标
        Spannable spannable = titleEt.getText();
        Selection.setSelection(spannable, titleEt.getText().length());
    }

    @Override
    public void onClick(View v) {
        String title = titleEt.getText().toString();
        String content = contentEt.getText().toString();
        String time = getTime();

        if (noteID == -1) {
            //默认添加
            dbManager.addToDB(title, content, time);
        } else {
            //更新
            dbManager.updateNote(noteID, title, content, time);
        }
        Intent i = new Intent(EditNoteActivity.this, NotesActivity.class);
        startActivity(i);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.finish();
    }

    private String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm E");
        Date curDate = new Date();
        String str = format.format(curDate);
        return str;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EditNoteActivity.this.finish();
                break;
            case R.id.action_about:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditNoteActivity.this, NotesActivity.class);
        startActivity(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.finish();
    }
}
