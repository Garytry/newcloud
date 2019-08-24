package com.garytry.gateway.apporgan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garytry.gateway.apporgan.dao.BaseAppOrganDao;
import com.garytry.gateway.apporgan.entity.BaseAppOrgan;
import com.garytry.gateway.apporgan.service.BaseAppOrganService;

@Service
public class BaseAppOrganServiceImpl implements BaseAppOrganService {
	@Autowired
	private BaseAppOrganDao baseAppOrganDao;

	@Override
	public void save(BaseAppOrgan baseAppOrgan) {
		baseAppOrganDao.save(baseAppOrgan);
	}

	@Override
	public JSONArray findByParentId(String parentId) {
		JSONArray jsons = new JSONArray();
		List<BaseAppOrgan> baseAppOrgans = baseAppOrganDao.findByParentId(parentId);
		for (BaseAppOrgan baseAppOrgan : baseAppOrgans) {
			JSONObject json = new JSONObject();
			json.put("orgId", baseAppOrgan.getId());
			json.put("orgName", baseAppOrgan.getName());
			jsons.add(json);
		}
		return jsons;
	}

	@Override
	public void clearOrgan() {
		baseAppOrganDao.clearOrgan();
	}

	@Override
	public BaseAppOrgan queryObject(String id) {
		return baseAppOrganDao.queryObject(id);
	}

}
