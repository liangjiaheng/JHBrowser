package com.design.jhbrowser.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;

public class SharedPreferencesCenterUtils {
	SharedPreferences sp;

	public SharedPreferencesCenterUtils(SharedPreferences sp) {
		this.sp = sp;
	}

	public void write(String key, boolean isCheck) {
		SharedPreferences.Editor editor = sp.edit();

		editor.putBoolean(key, isCheck);

		editor.commit();
	}

	public Map read(String key) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, sp.getBoolean(key, false));

		return map;
	}

}
