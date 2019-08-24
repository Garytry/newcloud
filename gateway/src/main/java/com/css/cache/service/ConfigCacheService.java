package com.css.cache.service;

/**
 * 配置缓存表
 * 
 * @author gengdesheng
 * @email
 * @date 2019-04-25 15:10:13
 */
public interface ConfigCacheService {

	/**
	 * 清空url对应服务的应用配置缓存
	 * 
	 * @param url
	 * @return
	 */
	public boolean clearCache(String url);

	/**
	 * 清空url对应服务的应用配置缓存异常熔断方法
	 * 
	 * @param url
	 * @return
	 */
	public boolean clearCacheException(String url);
}
