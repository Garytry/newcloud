package com.css.apptype.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.apptype.dao.AppLevelDao;
import com.css.apptype.entity.AppLevel;
import com.css.apptype.service.AppLevelService;

@Service
public class AppLevelServiceImpl implements AppLevelService {

	@Autowired
	private AppLevelDao appLevelDao;

	@Override
	public List<AppLevel> queryList(Map<String, Object> map) {
		return appLevelDao.queryList(map);
	}

	@Override
	public JSONArray getJSONArray() {
		List<AppLevel> appLevels= queryList(null);
		JSONArray jsons = new JSONArray();
		for (AppLevel appLevel:appLevels) {
			JSONObject json = new JSONObject();
			json.put("appLevel", appLevel.getAppLevel());
			json.put("LevelName", appLevel.getAppLevelName());
			jsons.add(json);
		}
		return jsons;
	}
}
