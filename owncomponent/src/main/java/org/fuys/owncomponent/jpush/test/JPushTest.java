package org.fuys.owncomponent.jpush.test;

import java.util.Random;

import org.fuys.owncomponent.jpush.util.JPushUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JPushTest {

	private static Logger logger = LoggerFactory.getLogger(JPushTest.class);

	public static void main(String[] args) {

		String alert = "测试服务器推送消息至手机应用是否可达 " + new Random().nextInt(100);

		try {
			JPushUtil.jpushAlert(alert);
		} catch (Exception e) {
			logger.error("异常信息流程", e);
		}finally {
			JPushUtil.closeJPushClient();
		}
		
	}

}
