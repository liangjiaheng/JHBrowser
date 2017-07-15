package com.design.jhbrowser.utils;


import com.design.jhbrowser.bean.Navigation;

import java.io.InputStream;
import java.util.List;

/**
 * Created by AdamL on 2017/5/2.
 */

public interface NavigationParser {

    /**
     * 解析输入流 得到Navigation对象集合
     *
     * @param is
     * @return
     * @throws Exception
     */
    public List<Navigation> parse(InputStream is) throws Exception;

    /**
     * 序列化Navigation对象集合 得到XML格式的字符串
     *
     * @param navigations
     * @return
     * @throws Exception
     */
    public String serialize(List<Navigation> navigations) throws Exception;

}
