package com.css.apptype.entity;

/**
 * 应用类型枚举类 单应用类型(one)：一套系统中只有一个应用，如首长办公,首长办公秘书版,公文转办,通讯录... ...
 * 多应用类型(many)：一套系统中存在多个应用(可以理解为局级应用)，如公文处理，电子保密室,数字档案室... ...
 * 
 * @author gengds
 */
public class AppType {

	// app应用类型
	private String type;
	// 应用类型(one:一包单应用，many:一包多应用)
	private String appFlag;
	//域名下URI的配置；
	private String webUri;
	//排序
	private Integer sort;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAppFlag() {
		return appFlag;
	}

	public void setAppFlag(String appFlag) {
		this.appFlag = appFlag;
	}

	public String getWebUri() {
		return webUri;
	}

	public void setWebUri(String webUri) {
		this.webUri = webUri;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
