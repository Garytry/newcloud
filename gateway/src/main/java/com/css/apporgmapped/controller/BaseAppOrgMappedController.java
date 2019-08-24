package com.css.apporgmapped.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.css.apporgan.service.BaseAppOrganService;
import com.css.apporgmapped.entity.BaseAppOrgMapped;
import com.css.apporgmapped.service.BaseAppOrgMappedService;
import com.css.apptype.entity.AppLevel;
import com.css.apptype.service.AppLevelService;
import com.css.base.utils.PageUtils;
import com.css.base.utils.Response;
import com.css.base.utils.StringUtils;
import com.github.pagehelper.PageHelper;

@RestController
@RequestMapping("/baseAppOrgMapped")
public class BaseAppOrgMappedController {

	@Autowired
	private BaseAppOrgMappedService baseAppOrgMappedServiceImpl;

	@Autowired
	private BaseAppOrganService baseAppOrganServiceImpl;

	@Autowired
	private AppLevelService appLevelServiceImpl;

	// 是否显示分支功能
	@Value("${appBranch.ifshow}")
	private Boolean ifshow;

	@Value("${appBranch.value}")
	private String appBranch;

	/**
	 * 查询配置列表
	 * 
	 * @param page
	 * @param rows
	 * @param appLevel
	 * @param orgId
	 * @param searchValue
	 */
	@RequestMapping("/list")
	public void list(Integer page, Integer rows, String appLevel, String orgId, String appBranch,String searchValue) {
		// 设置查询条件
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(appLevel)) {
			map.put("appLevel", appLevel);
		}
		if (StringUtils.isNotEmpty(orgId)) {
			map.put("orgId", orgId);
		}
		if (StringUtils.isNotBlank(appBranch)) {
			map.put("appBranch", appBranch);
		}
		if (StringUtils.isNotEmpty(searchValue)) {
			map.put("searchValue", "%" + searchValue + "%");
		}
		PageHelper.startPage(page, rows);
		// 查询列表数据
		List<BaseAppOrgMapped> list = baseAppOrgMappedServiceImpl.queryList(map);
		PageUtils pageUtil = new PageUtils(list);
		Response.json(pageUtil.getPageResult());
	}

	/**
	 * 编辑查询数据
	 */
	@RequestMapping("/info")
	public void info(String appId) {
		// 查询数据
		if (StringUtils.isNotBlank(appId)) {
			BaseAppOrgMapped baseAppOrgMapped = baseAppOrgMappedServiceImpl.queryObject(appId);
			Response.json(baseAppOrgMapped);
		} else {
			Response.json("nodata");
		}

	}

	/**
	 * 保存数据
	 */
	@RequestMapping("/save")
	public void list(@RequestBody BaseAppOrgMapped baseAppOrgMapped) {
		BaseAppOrgMapped baseAppOrgMappedTemp = baseAppOrgMappedServiceImpl.queryObject(baseAppOrgMapped.getAppId());
		if (baseAppOrgMappedTemp != null) {
			baseAppOrgMappedServiceImpl.update(baseAppOrgMapped);
			Response.json("success");
			return;
		}
		baseAppOrgMappedServiceImpl.save(baseAppOrgMapped);
		Response.json("success");
	}

	/**
	 * 查询配置列表
	 */
	@RequestMapping("/getMenu")
	public void getMenu() {
		// 获取部门信息
		JSONArray orgs = baseAppOrganServiceImpl.findByParentId("root");
		List<AppLevel> appLevels = appLevelServiceImpl.queryList(null);
		JSONArray jsons = new JSONArray();
		for (AppLevel appLevel : appLevels) {
			JSONObject json = new JSONObject();
			json.put("appLevel", appLevel.getAppLevel());
			json.put("LevelName", appLevel.getAppLevelName());
			if (StringUtils.equals(appLevel.getAppLevel(), "1")) {
				json.put("orgs", orgs);
			}
			jsons.add(json);
		}
		Response.json(jsons);
	}

	/**
	 * 获取应用分支信息
	 */
	@RequestMapping("/getAppBranch")
	public void getAppBranch() {
		// 获取部门信息
		JSONObject json = new JSONObject();
		json.put("ifshow", ifshow);
		if (StringUtils.isNotBlank(appBranch)) {
			json.put("value", appBranch.split(","));
		}
		Response.json(json);
	}

	/**
	 * 删除数据
	 */
	@RequestMapping("/delete")
	public void delete(String appIds) {
		if (StringUtils.isNotBlank(appIds)) {
			String[] appIdArray = appIds.split(",");
			baseAppOrgMappedServiceImpl.deleteBatch(appIdArray);
		}
		Response.json("success");
	}

}
