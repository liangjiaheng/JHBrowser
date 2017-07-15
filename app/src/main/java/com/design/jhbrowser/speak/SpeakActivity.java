package com.design.jhbrowser.speak;

import android.os.Bundle;
import android.view.View;

import com.design.jhbrowser.BaseActivity;
import com.design.jhbrowser.R;
import com.design.jhbrowser.fragment.bean.Cw;
import com.design.jhbrowser.fragment.bean.Sentence;
import com.design.jhbrowser.fragment.bean.Ws;
import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.ArrayList;
import java.util.List;

public class SpeakActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59190a3c");
    }

    public void beginIoc(View view) {
        RecognizerDialog mDialog = new RecognizerDialog(this, new InitListener() {
            @Override
            public void onInit(int i) {

            }
        });

        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");

        mDialog.setListener(new RecognizerDialogListener() {

            List<String> chineseWordList = new ArrayList<String>();
            String Str = "";//最终形成的句子
            String str2 = "分词结果：";//要做的分词效果

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                String jsonObg = recognizerResult.getResultString();
                Gson gson = new Gson();
                Sentence sentence = gson.fromJson(jsonObg, new Sentence().getClass());
                List<Ws> wsList = sentence.getWs();

                for (Ws w : wsList) {
                    List<Cw> cwList = w.getCw();
                    for (Cw c : cwList) {
                        String chineseWord = c.getW();
                        chineseWordList.add(chineseWord);
                        Str += chineseWord;
                        str2 += chineseWord + ",";
                    }
                }
            }

            @Override
            public void onError(SpeechError speechError) {

            }
        });

        mDialog.show();
    }


}
