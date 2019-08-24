package com.garytry.gateway.cache.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garytry.gateway.apporgmapped.controller.APIBaseAppOrgMappedController;
import com.garytry.gateway.apporgmapped.entity.BaseAppOrgMapped;
import com.garytry.gateway.apporgmapped.service.BaseAppOrgMappedService;
import com.garytry.gateway.base.utils.Response;
import com.garytry.gateway.cache.service.ConfigCacheService;

@RestController
@RequestMapping("/config")
public class ConfigCacheController {

	@Autowired
	private ConfigCacheService configCacheServiceImpl;

	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedServiceImpl;

	//应用网关地址
	@Value("${appgatewayUrl}")
	private String appgatewayUrl;

	/**
	 * 清除应用配置缓存
	 */
	@RequestMapping("clearcache")
	public void clearcache() {
		System.out.println("清除缓存数量:"+APIBaseAppOrgMappedController.baseAppOrgMappedMap.size());
		APIBaseAppOrgMappedController.baseAppOrgMappedMap.clear();
		List<BaseAppOrgMapped> baseAppOrgMappes = baseAppOrgMappedServiceImpl.queryUrlList();
		StringBuilder restult = new StringBuilder();
		for (BaseAppOrgMapped baseAppOrgMapped : baseAppOrgMappes) {
			if (StringUtils.equals(baseAppOrgMapped.getGateway(), "true")) {
				appgatewayUrl = appgatewayUrl+(appgatewayUrl.endsWith("/")?"":"/");
				baseAppOrgMapped.setUrl(appgatewayUrl+ baseAppOrgMapped.getUrl());
			}
			try {
				configCacheServiceImpl.clearCache(baseAppOrgMapped.getUrl());
				restult.append(baseAppOrgMapped.getUrl()+";");
			} catch(Exception e) {
				configCacheServiceImpl.clearCacheException(baseAppOrgMapped.getUrl());
				continue;
			}
		}
		restult.append("清除缓存成功");
		Response.json(restult);
	}
}
