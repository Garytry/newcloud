package com.css.apporgmapped.service;

import java.util.List;
import java.util.Map;

import com.css.apporgmapped.entity.BaseAppOrgMapped;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email
 * @date 2017-11-29 15:10:13
 */
public interface BaseAppOrgMappedService {

	BaseAppOrgMapped queryObject(String id);

	List<BaseAppOrgMapped> queryList(Map<String, Object> map);

	void save(BaseAppOrgMapped baseAppOrgMapped);

	int update(BaseAppOrgMapped baseAppOrgMapped);

	int delete(String id);

	int deleteBatch(String[] id);

	/**
	 * 获取url列表
	 * 
	 * @return
	 */
	List<BaseAppOrgMapped> queryUrlList();

	/**
	 * 根据appids获取配置信息
	 * @param appBranch
	 * @param appids
	 * @return
	 */
	List<BaseAppOrgMapped> queryListByAppids(String appBranch,String appids);

	/**
	 * 根据应用分支,应用级别,部门ID,应用类型,部门名称查询信息
	 * 
	 * @param appBranch
	 *            应用分支
	 * @param appLevel
	 *            应用级别(0:部级,1:局级,2:通用)
	 * @param orgId
	 *            部门ID
	 * @param type
	 *            应用类型
	 * @param orgName
	 *            部门名称
	 * @return
	 */
	List<BaseAppOrgMapped> orgMapped(String appBranch, String appLevel, String orgId, String type, String orgName);

}
