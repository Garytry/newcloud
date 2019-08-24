package com.garytry.gateway.apptype.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garytry.gateway.apptype.dao.AppTypeDao;
import com.garytry.gateway.apptype.entity.AppType;
import com.garytry.gateway.apptype.service.AppTypeService;

@Service
public class AppTypeServiceImpl implements AppTypeService {

	@Autowired
	private AppTypeDao appTypeDao;

	@Override
	public List<AppType> queryList(Map<String, Object> map) {
		return appTypeDao.queryList(map);
	}

	@Override
	public String getWebUri(String type) {
		AppType appType = appTypeDao.queryObject(type);
		if (appType != null) {
			return appType.getWebUri();
		}
		return null;
	}
}
