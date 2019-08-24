package com.garytry.gateway.apporgan.service;

import com.alibaba.fastjson.JSONArray;
import com.garytry.gateway.apporgan.entity.BaseAppOrgan;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppOrganService {

	/**
	 * 保存数据
	 * 
	 * @param baseAppOrgan
	 */
	void save(BaseAppOrgan baseAppOrgan);
	
	BaseAppOrgan queryObject(String id);

	/**
	 * 根据父Id获取部门信息
	 * 
	 * @author gengds
	 * @date 2017年11月29日
	 */
	JSONArray findByParentId(String parentId);

	/**
	 * 清空组织机构
	 * 
	 * @author gengds
	 */
	void clearOrgan();
}
