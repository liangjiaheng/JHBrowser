package com.design.jhbrowser.test.interfaces;

import org.xutils.common.Callback;

/**
 * Created by AdamL on 2017/5/21.
 */

public class HttpCallback<ResultType> implements Callback.CommonCallback<ResultType> {
    @Override
    public void onSuccess(ResultType result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
