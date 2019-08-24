package com.garytry.gateway.apporgan.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.garytry.gateway.apporgan.service.BaseAppOrganService;
import com.garytry.gateway.apporgan.service.OrgService;
import com.garytry.gateway.base.utils.Response;

/**
 * 组织机构增量同步接口
 * 
 * @author gengds
 */
@RestController
@RequestMapping("/organ")
public class SyncOrganController {

	@Autowired
	private BaseAppOrganService baseAppOrganService;
	@Autowired
	private OrgService orgServiceImpl;

	/**
	 * 清空组织机构
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public void deleteOrgan() {
		baseAppOrganService.clearOrgan();
		Response.json("清空组织结构成功");
	}

	/**
	 * 导入组织机构
	 */
	@ResponseBody
	@RequestMapping("/import")
	public void importOrgan() {
		baseAppOrganService.clearOrgan();
		orgServiceImpl.importOrg("root");
		Response.json("更新组织机构成功！");
	}

}
