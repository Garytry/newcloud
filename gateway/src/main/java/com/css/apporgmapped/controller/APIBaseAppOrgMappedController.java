package com.css.apporgmapped.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.css.apporgmapped.entity.BaseAppOrgMapped;
import com.css.apporgmapped.service.BaseAppOrgMappedService;
import com.css.base.utils.Response;

@RestController
public class APIBaseAppOrgMappedController {

	// 应用网关地址
	@Value("${appgatewayUrl}")
	private String appgatewayUrl;

	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedServiceImpl;

	// app配置缓存信息 key：appLevel:type:orgId value：app配置信息
	public static HashMap<String, List<BaseAppOrgMapped>> baseAppOrgMappedMap = new HashMap<String, List<BaseAppOrgMapped>>();

	@RequestMapping("/api/gateway/mapped/list")
	public void orgMapped(String appLevel, String orgId, String type, String orgName) {
		// 缓存配置信息cacheKey
		String cacheKey = appLevel + ":" + type + ":" + orgId + ":" + orgName;
		List<BaseAppOrgMapped> list = null;
		if (baseAppOrgMappedMap.containsKey(cacheKey)) {
			// 从缓存中读取配置信息
			list = baseAppOrgMappedMap.get(cacheKey);
			System.out.println("缓存配置信息key：" + cacheKey + "缓存信息：" + JSONArray.toJSONString(list));
		} else {
			// 查询列表数据
			list = baseAppOrgMappedServiceImpl.orgMapped(null, appLevel, orgId, type, orgName);
			// 更新缓存信息
			baseAppOrgMappedMap.put(cacheKey, list);
		}
		Response.json(list);
	}

	@RequestMapping("/{appBranch}/api/gateway/mapped/list")
	public void orgMapped(String appLevel, String orgId, String type, String orgName,
			@PathVariable("appBranch") String appBranch) {
		// 缓存配置信息cacheKey
		String cacheKey = appBranch + ":" + appLevel + ":" + type + ":" + orgId + ":" + orgName;
		List<BaseAppOrgMapped> list = null;
		if (baseAppOrgMappedMap.containsKey(cacheKey)) {
			// 从缓存中读取配置信息
			list = baseAppOrgMappedMap.get(cacheKey);
			System.out.println("缓存配置信息key：" + cacheKey + "缓存信息：" + JSONArray.toJSONString(list));
		} else {
			// 查询列表数据
			list = baseAppOrgMappedServiceImpl.orgMapped(appBranch, appLevel, orgId, type, orgName);
			// 更新缓存信息
			baseAppOrgMappedMap.put(cacheKey, list);
		}
		Response.json(list);
	}

	/**
	 * 根据appid获取配置信息
	 * 
	 * @param appBranch
	 *            应用分支
	 * @param appids
	 *            appids
	 * @param request
	 */
	@RequestMapping("/api/gateway/mapped/appids/{appids:.+}")
	public void orgMapped(@PathVariable("appids") String appids) {
		// 缓存配置信息cacheKey
		String cacheKey = appids;
		List<BaseAppOrgMapped> list = null;
		if (baseAppOrgMappedMap.containsKey(cacheKey)) {
			// 从缓存中读取配置信息
			list = baseAppOrgMappedMap.get(cacheKey);
			System.out.println("缓存配置信息key：" + cacheKey + "缓存信息：" + JSONArray.toJSONString(list));
		} else {
			// 查询列表数据
			list = baseAppOrgMappedServiceImpl.queryListByAppids(null, appids);
			// 更新缓存信息
			baseAppOrgMappedMap.put(cacheKey, list);
		}
		Response.json(list);
	}

	/**
	 * 根据appid获取配置信息
	 * 
	 * @param appBranch
	 *            应用分支
	 * @param appids
	 *            appids
	 * @param request
	 */
	@RequestMapping("/{appBranch}/api/gateway/mapped/appids/{appids:.+}")
	public void orgMapped(@PathVariable("appBranch") String appBranch, @PathVariable("appids") String appids) {
		// 缓存配置信息cacheKey
		String cacheKey = appBranch+":"+appids;
		List<BaseAppOrgMapped> list = null;
		if (baseAppOrgMappedMap.containsKey(cacheKey)) {
			// 从缓存中读取配置信息
			list = baseAppOrgMappedMap.get(cacheKey);
			System.out.println("缓存配置信息key：" + cacheKey + "缓存信息：" + JSONArray.toJSONString(list));
		} else {
			// 查询列表数据
			list = baseAppOrgMappedServiceImpl.queryListByAppids(appBranch, appids);
			// 更新缓存信息
			baseAppOrgMappedMap.put(cacheKey, list);
		}
		Response.json(list);
	}

}
