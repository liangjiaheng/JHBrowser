package com.design.jhbrowser.utils;

import com.google.gson.Gson;

import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;

/**
 * Created by AdamL on 2017/5/21.
 */

public class JsonResponsePaserUtils implements ResponseParser {
    @Override
    public void checkResponse(UriRequest request) throws Throwable {

    }

    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        if (resultType != null) {
            return new Gson().fromJson(result, resultType);
        } else {
            return new Gson().fromJson(result, resultClass);
        }
    }
}
