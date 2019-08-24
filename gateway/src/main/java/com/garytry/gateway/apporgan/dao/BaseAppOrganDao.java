package com.garytry.gateway.apporgan.service.impl;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.garytry.gateway.apporgan.entity.BaseAppOrgan;
import com.garytry.gateway.base.dao.BaseDao;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
@Mapper
public interface BaseAppOrganDao extends BaseDao<BaseAppOrgan> {
	
	/**
	 * 根据父Id获取部门信息
	 * @author gengds
	 */
	@Select("select * from BASE_APP_ORGAN where PARENT_ID = #{parentId} order by SORT")
	List<BaseAppOrgan> findByParentId(String parentId);
	
	/**
	 * 清空组织机构
	 * @author gengds
	 */
	@Delete("delete from BASE_APP_ORGAN")
	void clearOrgan();
	
}
