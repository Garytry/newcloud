package com.garytry.gateway.apporgan.service;

import com.garytry.gateway.apporgan.entity.Organ;

/**
 * 核心服务组织机构接口
 * 
 * @author gengds
 */
public interface OrgService {

	/**
	 * 根据部门Id获取部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public Organ getOrgan(String id);

	/**
	 * 根据部门Id获取子部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public Organ[] getSubOrg(String id);

	/**
	 * 根据部门Id获取子部门信息 包含无效数据：invalid 包含全部子级：sublevel
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	public Organ[] getSubOrg(String id, boolean invalid, boolean sublevel);

	/**
	 * 根据应用的ID，获取token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return token
	 */
	public String getToken();

	/**
	 * 递归保存组织机构信息
	 */
	public void importOrg(String organId);

	/**
	 * 将微服务组织结构数据保存到本地库中
	 * 
	 * @param organ
	 */
	public void saveOrgan(Organ organ);

}
