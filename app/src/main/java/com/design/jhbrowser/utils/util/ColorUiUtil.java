package com.design.jhbrowser.utils.util;

import android.content.res.Resources;
import android.icu.text.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.design.jhbrowser.utils.ColorUiInterface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by AdamL on 2017/4/24.
 */

public class ColorUiUtil {
    public static void changeTheme(View rootView, Resources.Theme theme) {
        if (rootView instanceof ColorUiInterface) {
            ((ColorUiInterface) rootView).setTheme(theme);
            if (rootView instanceof ViewGroup) {
                int count = ((ViewGroup) rootView).getChildCount();
                for (int i = 0; i < count; i++) {
                    changeTheme(((ViewGroup) rootView).getChildAt(i), theme);
                }
            }
            if (rootView instanceof AbsListView) {
                try {
                    Field localField = AbsListView.class.getDeclaredField("mRecycler");
                    localField.setAccessible(true);
                    Method localMethod = Class.forName("android.widget.AbsListView$RecycleBin").getDeclaredMethod("clear", new Class[0]);
                    localMethod.setAccessible(true);
                    localMethod.invoke(localField.get(rootView), new Object[0]);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e2) {
                    e2.printStackTrace();
                } catch (IllegalAccessException e3) {
                    e3.printStackTrace();
                } catch (InvocationTargetException e4) {
                    e4.printStackTrace();
                } catch (ClassNotFoundException e5) {
                    e5.printStackTrace();
                }
            }
        } else {
            if (rootView instanceof ViewGroup) {
                int count = ((ViewGroup) rootView).getChildCount();
                for (int i = 0; i < count; i++) {
                    changeTheme(((ViewGroup) rootView).getChildAt(i), theme);
                }
            }

            if (rootView instanceof AbsListView){
                try {
                    Field localField = AbsListView.class.getDeclaredField("mRecycler");
                    localField.setAccessible(true);
                    Method loaclMethod = Class.forName("android.widget.AbsListView$RecycleBin").getDeclaredMethod("clear",new Class[0]);
                    loaclMethod.setAccessible(true);
                    loaclMethod.invoke(localField.get(rootView), new Object[0]);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
