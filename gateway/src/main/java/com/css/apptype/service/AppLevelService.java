package com.css.apptype.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.css.apptype.entity.AppLevel;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-11-29 15:10:13
 */
public interface AppLevelService {

	/**
	 * 查询应用类型列表
	 * 
	 * @return
	 */
	List<AppLevel> queryList(Map<String, Object> map);

	/**
	 * 获取应用级别json
	 * @return
	 */
	public JSONArray getJSONArray();
}
