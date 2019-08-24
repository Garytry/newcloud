package com.garytry.gateway.apptype.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.garytry.gateway.apptype.entity.AppType;
import com.garytry.gateway.apptype.service.AppTypeService;
import com.garytry.gateway.base.utils.Response;
import com.garytry.gateway.base.utils.StringUtils;

@RestController
@RequestMapping("/apptype")
public class AppTypeController {

	@Autowired
	private AppTypeService appTypeServiceImpl;

	/**
	 * 查询应用类型列表
	 * 
	 * @param searchValue
	 */
	@RequestMapping("/list")
	public void list() {
		JSONObject result = new JSONObject();
		List<AppType> appTypes = appTypeServiceImpl.queryList(null);
		List<String> oneTypes = new ArrayList<String>();
		List<String> manyTypes = new ArrayList<String>();
		for (AppType appType : appTypes) {
			if (StringUtils.equals("one", appType.getAppFlag())) {
				oneTypes.add(appType.getType());
			} else if (StringUtils.equals("many", appType.getAppFlag())) {
				manyTypes.add(appType.getType());
			} else {
				oneTypes.add(appType.getType());
				manyTypes.add(appType.getType());
			}
		}
		result.put("one", oneTypes);
		result.put("many", manyTypes);
		Response.json(result);
	}

}
