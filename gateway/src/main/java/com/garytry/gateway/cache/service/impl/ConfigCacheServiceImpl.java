package com.garytry.gateway.cache.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.garytry.gateway.cache.service.ConfigCacheService;

/**
 * 配置缓存表
 * 
 * @author gengdesheng
 * @email
 * @date 2019-04-25 15:10:13
 */
@Service
public class ConfigCacheServiceImpl implements ConfigCacheService {

	// 日志记录
	private static Logger logger = LoggerFactory.getLogger(ConfigCacheServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	String clearcacheUri = "app/config/clearcache.htm";

	//@HystrixCommand(fallbackMethod = "clearCacheException")
	public boolean clearCache(String url) {
		String baseUrl = url + (url.endsWith("/") ? "" : "/");
		restTemplate.getForObject(baseUrl + clearcacheUri, String.class);
		logger.info(baseUrl + clearcacheUri + ":清除缓存成功===========================success;");
		return true;
	}
	public boolean clearCacheException(String url) {
		String baseUrl = url + (url.endsWith("/") ? "" : "/");
		logger.info(baseUrl + clearcacheUri + ":清除缓存失败;");
		return false;
	}
}
