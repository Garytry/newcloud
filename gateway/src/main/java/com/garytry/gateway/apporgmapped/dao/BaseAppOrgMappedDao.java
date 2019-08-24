package com.garytry.gateway.apporgmapped.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.garytry.gateway.apporgmapped.entity.BaseAppOrgMapped;
import com.garytry.gateway.base.dao.BaseDao;

/**
 * 部门表
 * 
 * @author 中软信息系统工程有限公司
 * @email 
 * @date 2017-11-29 15:10:13
 */
@Mapper
public interface BaseAppOrgMappedDao extends BaseDao<BaseAppOrgMapped> {
	
	/**
	 * 获取url列表
	 * @return
	 */
	List<BaseAppOrgMapped> queryUrlList();
	
	/**
	 * 根据appids获取配置信息
	 * @return
	 */
	List<BaseAppOrgMapped> queryListByAppids(Map<String, Object> map);
}
