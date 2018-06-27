package org.fuys.owncomponent.jpush.util;

import org.fuys.owncomponent.jpush.entity.JPushes;
import org.fuys.owncomponent.util.PropertyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class JPushUtil {
	
	private static Logger LOG = LoggerFactory.getLogger(JPushUtil.class);

	private static String MASTER_SECRET = null;
	private static String APP_KEY = null;
	private static JPushClient jpushClient = null;

	static {
		MASTER_SECRET = PropertyUtil.getValue("jpush", "org.fuys.jpush.MasterSecret");
		APP_KEY = PropertyUtil.getValue("jpush", "org.fuys.jpush.AppKey");
		jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
	}
	
	public static JPushClient getJPushClient() {
		if(jpushClient==null) {
			jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());
		}
		return jpushClient;
	}
	
	public static void closeJPushClient() {
		if(jpushClient!=null) {
			jpushClient.close();
		}
	}
	
	public static Object jpushAlert(String alert) {
		return jpushAlert(jpushClient,alert);
	}
	
	public static Object jpushAlert(JPushClient jpushClient,String alert) {
		if(jpushClient==null) {
			jpushClient = getJPushClient();
		}
		PushPayload payload = JPushes.buildPushObject_all_all_alert(alert);
		try {
			LOG.info("start");
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error, should retry later", e);
		} catch (APIRequestException e) {
			LOG.error("Should review the error, and fix the request", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}
		return null;
	}

}
