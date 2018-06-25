package org.fuys.owncomponent.jpush.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

public class JPushTest {

	private static final String MASTER_SECRET = "d96aaa8365468d5b098ff3df";
	private static final String APP_KEY = "b4c1823c2b0b51baca4b2e7e";

	private static Logger LOG = LoggerFactory.getLogger(JPushTest.class);

	public static void main(String[] args) {

		JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, null, ClientConfig.getInstance());

		String alert = "connect jpush server";
		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_all_all_alert(alert);

		try {
			LOG.info("start");
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
			jpushClient.close();
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			LOG.error("Connection error, should retry later", e);

		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			LOG.error("Should review the error, and fix the request", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
		}

	}

	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.alertAll(alert);
	}

}
