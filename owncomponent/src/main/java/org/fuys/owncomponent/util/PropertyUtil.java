package org.fuys.owncomponent.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyUtil {

	private static Logger log = LoggerFactory.getLogger(PropertyUtil.class);
	private static Map<String, Properties> propMap = new HashMap<String, Properties>();

	private static Properties loadProps(String filePath) {

		Properties props = propMap.get(filePath);
		if (props == null) {
			props = new Properties();
			InputStream in = null;
			try {
				in = PropertyUtil.class.getResourceAsStream(filePath);
				props.load(in);
			} catch (Exception e) {
				// 此处可根据你的日志框架进行记录
				System.err.println("Error reading conf properties in PropertyManager.loadProps() " + e);
				log.error("PropertyUtil loadProps Exceptoin:{}", e);
			} finally {
				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
					log.error("PropertyUtil loadProps Exceptoin:{}", e);
				}
			}
		}
		return props;
	}

	/**
	 * 根据key获取对应value
	 * 
	 * @param fileName文件名称
	 * @param key
	 * @return
	 */
	public static String getValue(String fileName, String key) {
		return getValue(fileName, key, "");
	}

	/**
	 * 根据key获取对应value，如果为空则返回默认的value
	 * @param fileName文件名称
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getValue(String fileName, String key, String defaultValue) {
		// 读取系统文件路径，以/为分割
		Properties props = loadProps("/conf/" + fileName + ".properties");
		String value = props.getProperty(key);
		return value == null ? defaultValue : value;
	}

}
