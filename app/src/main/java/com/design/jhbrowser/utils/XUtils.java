package com.design.jhbrowser.utils;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.Callback.Cancelable;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

/**
 * Created by AdamL on 2017/5/21.
 */

public class XUtils {

    /**
     * 发送Get请求
     *
     * @param url
     * @param map
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Cancelable Get(String url, Map<String, String> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }

    /**
     * 发送Post请求
     *
     * @param url
     * @param map
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Cancelable post(String url, Map<String, Object> map, CommonCallback<T> callback) {

        RequestParams params = new RequestParams(url);
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }

    /**
     * 上传文件
     *
     * @param url
     * @param map
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Cancelable UpLoadFile(String url, Map<String, Object> map, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


    /**
     * 下载文件
     *
     * @param url
     * @param filePath
     * @param callback
     * @param <T>
     * @return
     */
    public static <T> Cancelable DownLoadFile(String url, String filePath, CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoRename(true);
        params.setSaveFilePath(filePath);
        Cancelable cancelable = x.http().get(params, callback);
        return cancelable;
    }


}
