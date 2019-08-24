package com.garytry.gateway.apptype.dao;

import org.apache.ibatis.annotations.Mapper;

import com.garytry.gateway.apptype.entity.AppType;
import com.garytry.gateway.base.dao.BaseDao;

/**
 * 应用类型
 * 
 */
@Mapper
public interface AppTypeDao extends BaseDao<AppType> {

}
