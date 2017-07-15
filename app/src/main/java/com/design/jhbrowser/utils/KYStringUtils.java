package com.design.jhbrowser.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.widget.Toast;

public class KYStringUtils {

    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 获取当前年月日，时分秒
     *
     * @return
     */
    public static String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = calendar.get(Calendar.MONTH) + 1 + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";
        String second = calendar.get(Calendar.SECOND) + "";
        StringBuffer buffer = new StringBuffer();

        buffer.append(year).append("-").append(month).append("-")
                .append(day).append(" ").append(hour).append(":")
                .append(minute).append(":").append(second);

        return buffer.toString();
    }


    public static String getImgUrl(String url) {

        String str = url.substring(0, url.indexOf("com/") + 3);

        return str + "/favicon.ico";
    }

    public static boolean isTrue(int i) {
        if (i == 16 || i == 17 || i == 19 || 1 == 20 || i == 22 || i == 23 || i == 25 || i == 26 || i == 28 || i == 29) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * 获得YYYY-MM-DD格式的日期字符串
     *
     * @return {String}
     */
    public static String getYMDDateString(String oldDate) {
        String newDate = oldDate.replaceAll("年", "-");
        newDate = newDate.replaceAll("月", "-");
        newDate = newDate.replaceAll("日", "");
        return newDate.trim();
    }

    /**
     * 通过反射，获取表名
     *
     * @param clazz 将要被创建的类
     * @return
     */
    public static String getDropSQL(Class clazz) {
        StringBuffer sb = new StringBuffer();
        String tableName = clazz.getSimpleName();
        tableName = "t_" + tableName.toLowerCase();
        sb.append("drop table if exists ").append(tableName);
        return sb.toString();
    }

    public static String getDeleteSQL(Class clazz) {
        StringBuffer sb = new StringBuffer();
        String tableName = clazz.getSimpleName();
        tableName = "t_" + tableName.toLowerCase();
        sb.append("delete from ").append(tableName);
        if (tableName.equals("t_ldry") || tableName.equals("t_sxet")
                || tableName.equals("t_fwxxfh") || tableName.equals("t_cqrdlr")) {
            sb.append(" where ywlx='已上传'");
        }
        return sb.toString();
    }

    /**
     * 通过反射获取数据库表名
     *
     * @param clazz
     * @return
     */
    public static String getTableName(Class clazz) {
        String tableName = clazz.getSimpleName();
        tableName = "t_" + tableName.toLowerCase();
        return tableName;
    }


    /**
     * 通过反射，建立表结构SQL代码
     *
     * @param clazz 将要被创建的类
     *              主键
     * @return
     */
    public static String getCreateSQL(Class clazz) {
        StringBuffer sb = new StringBuffer();
        String tableName = clazz.getSimpleName();
        tableName = "t_" + tableName.toLowerCase();
        sb.append("create table ").append(tableName).append(" (");
        String pk = FileUpdaterUtils.FileUpdaterPK.get(clazz);
        if (pk == null) {
            sb.append("_id integer primary key,");
        }
        Field[] fields = clazz.getDeclaredFields();
        for (Field fItem : fields) {
            sb.append(fItem.getName());
            if (fItem.getType().equals(Date.class)) {
                sb.append(" date");
            } else if (fItem.getType().toString().equals("int")) {
                sb.append(" int");
            } else {
                sb.append(" text");
            }
            if (pk != null && fItem.getName().equals(pk)) {
                sb.append(" primary key");
            }
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(");");
        return sb.toString();
    }

}
