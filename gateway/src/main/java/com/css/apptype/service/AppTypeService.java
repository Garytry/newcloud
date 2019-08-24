package com.css.apptype.service;

import java.util.List;
import java.util.Map;

import com.css.apptype.entity.AppType;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-11-29 15:10:13
 */
public interface AppTypeService {

	/**
	 * 查询应用类型列表
	 */
	List<AppType> queryList(Map<String, Object> map);

	/**
	 * 查询URI的配置路径
	 */
	String getWebUri(String type);

}
