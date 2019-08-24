package com.garytry.gateway.sqlmanager.controller;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garytry.gateway.base.utils.Response;
import com.garytry.gateway.sqlmanager.tools.ElementType;
import com.garytry.gateway.sqlmanager.tools.JdbcManager;
import com.garytry.gateway.sqlmanager.tools.ReadXML;

@RestController
@RequestMapping("/sql")
/**
 * 初始化数据库sql
 * @author lenovo
 *
 */
public class sqlController {

	@Autowired
	private JdbcManager jdbcManager;

	@RequestMapping("/all")
	public void dealSQL(String version) {
		Statement statement = jdbcManager.getStatement();
		JSONObject jsonSuccess = new JSONObject();
		JSONObject jsonfail = new JSONObject();
		JSONArray restult = new JSONArray();
		Map<String, Map<String, String>> sqlMap = new ReadXML().getSqlMap("sql_0.0.0.0.xml");
		//创建表
		for (Map.Entry<String, String> entry : sqlMap.get(ElementType.CREATE).entrySet()) {
			try {
				statement.execute(entry.getValue());
				jsonSuccess.put(entry.getKey(), "success");
			} catch (SQLException e) {
				jsonfail.put(entry.getKey(), "fali");
				continue;
			}
		}
		//添加表字段注释
		for (Map.Entry<String, String> entry : sqlMap.get(ElementType.COMMENT).entrySet()) {
			try {
				String[] sqls = entry.getValue().split(";");
				for (String sql : sqls) {
					if (sql.trim().length() > 0) {
						statement.execute(sql);
					}
				}
				jsonSuccess.put(entry.getKey(), "success");
			} catch (SQLException e) {
				jsonfail.put(entry.getKey(), "fali");
				continue;
			}
		}
		//插入数据
		for (Map.Entry<String, String> entry : sqlMap.get(ElementType.INSERT).entrySet()) {
			try {
				String[] sqls = entry.getValue().split(";");
				for (String sql : sqls) {
					if (sql.trim().length() > 0) {
						statement.execute(sql);
					}
				}
				jsonSuccess.put(entry.getKey(), "success");
			} catch (SQLException e) {
				jsonfail.put(entry.getKey(), "fali");
				continue;
			}
		}
		restult.add(jsonSuccess);
		restult.add(jsonfail);
		Response.json(restult);
	}

}