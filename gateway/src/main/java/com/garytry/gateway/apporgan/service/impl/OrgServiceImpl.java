package com.garytry.gateway.apporgan.service.impl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.garytry.gateway.apporgan.entity.BaseAppOrgan;
import com.garytry.gateway.apporgan.entity.Organ;
import com.garytry.gateway.apporgan.service.BaseAppOrganService;
import com.garytry.gateway.apporgan.service.OrgService;

/**
 * 核心服务组织机构接口
 * 
 * @author gengds
 */
@Service
public class OrgServiceImpl implements OrgService{

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BaseAppOrganService baseAppOrganService;

	// 微服务网关地址
	@Value("${csse.mircoservice.zuul}")
	private  String zuul;
	// 微服务组织机构服务调整地址
	@Value("${csse.mircoservice.org}")
	private  String org;
	// 默认配置(application.yml中配置的token认证服务地址)
	@Value("${csse.mircoservice.oauth2.client.accessTokenUri}")
	private  String accessTokenUri;
	// 默认配置(application.yml中配置的appId)
	@Value("${csse.mircoservice.oauth2.client.clientId}")
	private  String clientId;
	// 默认配置(application.yml中配置的appSecret)
	@Value("${csse.mircoservice.oauth2.client.clientSecret}")
	private  String clientSecret;
	// 授权类型
	private static final String GRANT_TYPE = "client_credentials";

	// 日志记录
	private static Logger logger = LoggerFactory.getLogger(OrgService.class);

	/**
	 * 根据部门Id获取部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	@Override
	public Organ getOrgan(String id) {
		String url = zuul + org + "/department/" + id + "?access_token=" + getToken();
		return (Organ) restTemplate.getForObject(url, Organ.class, new Object[0]);

	}

	/**
	 * 根据部门Id获取子部门信息
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	@Override
	public Organ[] getSubOrg(String id) {
		return getSubOrg(id, false, false);
	}

	/**
	 * 根据部门Id获取子部门信息 包含无效数据：invalid 包含全部子级：sublevel
	 * 
	 * @date 2017年7月21日
	 * @author gengds
	 */
	@Override
	public Organ[] getSubOrg(String id, boolean invalid, boolean sublevel) {
		StringBuffer url = new StringBuffer(zuul + org + "/department/" + id + "/subdepartments");
		if ((invalid) || (sublevel)) {
			url.append("?");
			if (invalid) {
				url.append("invalid&");
			}
			if (sublevel) {
				url.append("sublevel&");
			}
			url.append("access_token=" + getToken());
		} else {
			url.append("?access_token=" + getToken());
		}
		return (Organ[]) restTemplate.getForObject(url.toString(), Organ[].class, new Object[0]);

	}

	/**
	 * 根据应用的ID，获取token
	 * 
	 * @param appId
	 * @param appSecret
	 * @return token
	 */
	@Override
	public String getToken() {
		// 获取token
		String accessTokenUrl = accessTokenUri + "?client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&grant_type=" + GRANT_TYPE;
		logger.info("token请求路径:{}", accessTokenUrl);
		try {
			RestTemplate restTemplate = new RestTemplate();
			JSONObject access_token = restTemplate.postForObject(accessTokenUrl, null, JSONObject.class);
			String token = access_token.getString("access_token");
			logger.info("appId:{}-->appSecret:{}-->{}token{}:{}", clientId, clientSecret, "获取", "成功", token);
			return token;
		} catch (HttpClientErrorException e) {
			// 获取失败，返回null
			logger.info("appId:{}-->appSecret:{}-->{}token{}", clientId, clientSecret, "获取", "失败");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 递归保存组织机构信息
	 */
	@Override
	public void importOrg(String organId) {
		Organ organ = getOrgan(organId);
		saveOrgan(organ);
		Organ[] organs = getSubOrg(organId);
		for (Organ sysOrgan : organs) {
			importOrg(sysOrgan.getOrganId());
		}
	}

    /**
     * 将微服务组织结构数据保存到本地库中
     * @param organ
     */
	@Override
	public void saveOrgan(Organ organ) {
		BaseAppOrgan baseAppOrgan = new BaseAppOrgan();
		baseAppOrgan.setId(organ.getOrganId());
		baseAppOrgan.setName(organ.getOrganName());
		baseAppOrgan.setParentId(organ.getFatherId());
		baseAppOrgan.setTreePath(organ.getP());
		baseAppOrgan.setSort(organ.getOrderId());
		baseAppOrgan.setIsdelete(organ.getIsDelete());
		baseAppOrganService.save(baseAppOrgan);
		logger.info("{}:{}==>导入成功", organ.getOrganId(), organ.getOrganName());
	}

}
