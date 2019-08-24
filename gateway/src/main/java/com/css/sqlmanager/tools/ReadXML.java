package com.css.sqlmanager.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.css.base.utils.StringUtils;

/**
 * 解析sqlXML文件
 * 
 * @author gengds
 */
public class ReadXML {

	/**
	 * 获取SQL解析集合
	 * 
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public Map<String, Map<String, String>> getSqlMap(String fileName) {
		Map<String, Map<String, String>> sqlMap = new HashMap<String, Map<String, String>>();
		sqlMap.put(ElementType.CREATE, getElememtTypeMap(ElementType.CREATE, fileName));
		sqlMap.put(ElementType.COMMENT, getElememtTypeMap(ElementType.COMMENT, fileName));
		sqlMap.put(ElementType.INSERT, getElememtTypeMap(ElementType.INSERT, fileName));
		return sqlMap;
	}

	/**
	 * 根据操作类型分类
	 * 
	 * @param ElememtType
	 *            操作类型
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public Map<String, String> getElememtTypeMap(String ElememtType, String fileName) {
		Map<String, String> xmlMap = new ReadXML().getXmlMap(fileName);
		Map<String, String> elememtTypeMap = new HashMap<String, String>();
		for (Map.Entry<String, String> entry : xmlMap.entrySet()) {
			if (StringUtils.contains(entry.getKey(), ElememtType)) {
				elememtTypeMap.put(entry.getKey(), entry.getValue());
			}
		}
		return elememtTypeMap;
	}

	/**
	 * 获取XML解析结果
	 * 
	 * @param fileName
	 *            解析文件名称
	 * @return 解析结果集合(key:操作类型+操作表,value:sql语句)
	 */
	public Map<String, String> getXmlMap(String fileName) {
		URL resource = ReadXML.class.getClassLoader().getResource(fileName);
		System.out.println(resource.toString());
		InputStream is = null;
		try {
			is = resource.openStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Map<String, String> xmlMap = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element element = document.getRootElement();
		Iterator<Element> sqls = element.elementIterator();
		while (sqls.hasNext()) {
			Element sql = sqls.next();
			if (xmlMap.containsKey(sql.getName() + ":" + sql.attributeValue("tableName"))) {
				System.out.println("操作类型+操作表名称重复");
			} else {
				xmlMap.put(sql.getName() + ":" + sql.attributeValue("tableName"), (String) sql.getData());
			}
		}
		return xmlMap;
	}

}
