package com.css.apporgmapped.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.css.apporgan.entity.BaseAppOrgan;
import com.css.apporgan.service.BaseAppOrganService;
import com.css.apporgmapped.dao.BaseAppOrgMappedDao;
import com.css.apporgmapped.entity.BaseAppOrgMapped;
import com.css.apporgmapped.service.BaseAppOrgMappedService;
import com.css.apptype.service.AppTypeService;
import com.css.base.utils.StringUtils;

@Service
public class BaseAppOrgMappedServiceImpl implements BaseAppOrgMappedService {

	// 应用网关地址
	@Value("${appgatewayUrl}")
	private String appgatewayUrl;
	
	@Autowired
	private BaseAppOrgMappedDao baseAppOrgMappedDao;

	@Autowired
	private BaseAppOrganService baseAppOrganServiceImpl;

	@Autowired
	private AppTypeService appTypeServiceImpl;
	
	// 日志记录
	private static Logger logger = LoggerFactory.getLogger(BaseAppOrgMappedServiceImpl.class);

	@Override
	public BaseAppOrgMapped queryObject(String id) {
		return baseAppOrgMappedDao.queryObject(id);
	}

	@Override
	public List<BaseAppOrgMapped> queryList(Map<String, Object> map) {
		return baseAppOrgMappedDao.queryList(map);
	}

	@Override
	public void save(BaseAppOrgMapped baseAppOrgMapped) {
		// 设置应用类型
		if (StringUtils.isNotBlank(baseAppOrgMapped.getType())) {
			String type = baseAppOrgMapped.getType().split(":")[1];
			baseAppOrgMapped.setAppType(baseAppOrgMapped.getType().split(":")[0]);
			baseAppOrgMapped.setType(type);
			baseAppOrgMapped.setWebUri(appTypeServiceImpl.getWebUri(type));
		}
		// 根据部门ID设置部门名称
		BaseAppOrgan baseAppOrgan = baseAppOrganServiceImpl.queryObject(baseAppOrgMapped.getOrgId());
		if (null != baseAppOrgan) {
			baseAppOrgMapped.setOrgName(baseAppOrgan.getName());
		}
		// appLevel不为1的应用类型,清空组织结构
		if (!StringUtils.equals("1", baseAppOrgMapped.getAppLevel())) {
			baseAppOrgMapped.setOrgId(null);
			baseAppOrgMapped.setOrgName(null);
		}
		baseAppOrgMappedDao.save(baseAppOrgMapped);
	}

	@Override
	public int update(BaseAppOrgMapped baseAppOrgMapped) {
		// 设置应用类型
		if (StringUtils.isNotBlank(baseAppOrgMapped.getType())) {
			String type = baseAppOrgMapped.getType().split(":")[1];
			baseAppOrgMapped.setAppType(baseAppOrgMapped.getType().split(":")[0]);
			baseAppOrgMapped.setType(type);
			baseAppOrgMapped.setWebUri(appTypeServiceImpl.getWebUri(type));
		}
		// 根据部门ID设置部门名称
		BaseAppOrgan baseAppOrgan = baseAppOrganServiceImpl.queryObject(baseAppOrgMapped.getOrgId());
		if (null != baseAppOrgan) {
			baseAppOrgMapped.setOrgName(baseAppOrgan.getName());
		}
		// appLevel不为1的应用类型,清空组织结构
		if (!StringUtils.equals("1", baseAppOrgMapped.getAppLevel())) {
			baseAppOrgMapped.setOrgId(null);
			baseAppOrgMapped.setOrgName(null);
		}
		return baseAppOrgMappedDao.update(baseAppOrgMapped);
	}
	
	@Override
	public List<BaseAppOrgMapped> queryListByAppids(String appBranch,String appids) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(appBranch)) {
			map.put("appBranch", appBranch);
		}
		if (StringUtils.isNotBlank(appids) && !StringUtils.equals(appids, "all")) {
			map.put("appids",appids);
			map.put("appidsAddr",appids.split(","));
		}
		// 查询列表数据
		List<BaseAppOrgMapped> list = baseAppOrgMappedDao.queryListByAppids(map);
		for (BaseAppOrgMapped baseAppOrgMapped : list) {
			if (StringUtils.equals(baseAppOrgMapped.getGateway(), "true")) {
				appgatewayUrl = appgatewayUrl + (appgatewayUrl.endsWith("/") ? "" : "/");
				baseAppOrgMapped.setUrl(appgatewayUrl + baseAppOrgMapped.getUrl());
			}
		}
		if (null != list && list.size() > 0) {
			logger.info("appBranch={},appids={}的查询果===>{}", appBranch, appids,
					JSONArray.toJSONString(list));
		} else {
			logger.info("appBranch={},appids={}的查询果===>未查到数据", appBranch, appids);
		}
		return list;
	}

	public List<BaseAppOrgMapped> orgMapped(String appBranch,String appLevel, String orgId, String type, String orgName) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(appBranch)) {
			map.put("appBranch", appBranch);
		}
		if (StringUtils.isNotBlank(appLevel)) {
			map.put("appLevel", appLevel);
		}
		if (StringUtils.isNotBlank(orgId)) {
			map.put("orgId", orgId);
		}
		if (StringUtils.isNotBlank(type)) {
			map.put("type", type);
		}
		if (StringUtils.isNotBlank(orgName)) {
			map.put("orgName", orgName);
		}
		// 查询列表数据
		List<BaseAppOrgMapped> list = baseAppOrgMappedDao.queryList(map);
		for (BaseAppOrgMapped baseAppOrgMapped : list) {
			if (StringUtils.equals(baseAppOrgMapped.getGateway(), "true")) {
				appgatewayUrl = appgatewayUrl + (appgatewayUrl.endsWith("/") ? "" : "/");
				baseAppOrgMapped.setUrl(appgatewayUrl + baseAppOrgMapped.getUrl());
			}
		}
		if (null != list && list.size() > 0) {
			logger.info("appLevel={},orgId={},type={},orgname={}的查询果===>{}", appLevel, orgId, type,orgName,JSONArray.toJSONString(list));
		} else {
			logger.info("appLevel={},orgId={},type={},orgname={}的查询果===>未查到数据", appLevel, orgId, type,orgName);
		}
		return list;
	}

	@Override
	public int delete(String id) {
		return baseAppOrgMappedDao.delete(id);
	}

	@Override
	public int deleteBatch(String[] id) {
		return baseAppOrgMappedDao.deleteBatch(id);
	}

	@Override
	public List<BaseAppOrgMapped> queryUrlList() {
		return baseAppOrgMappedDao.queryUrlList();
	}

}
