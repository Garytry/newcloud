package com.garytry.gateway.base.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author 中软信息系统工程有限公司
 * @date 2016年9月18日 上午9:31:36
 */
public interface BaseDao<T> {
	void save(T t);

	void save(Map<String, Object> map);

	int update(T t);

	int update(Map<String, Object> map);

	int delete(Object id);
	
	int deleteBatch(Object[] id);

	T queryObject(Object id);
	
	T queryFileInfoObject(Object id, Object userId);

	List<T> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
}
