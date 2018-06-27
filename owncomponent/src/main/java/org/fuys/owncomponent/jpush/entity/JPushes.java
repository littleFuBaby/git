package org.fuys.owncomponent.jpush.entity;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 构建推送对象工具类
 * @author waterelephant andy
 *
 */
public class JPushes {
	
	/**
	 * 构建推送对象
	 * @param alert
	 * @return
	 */
	public static PushPayload buildPushObjectAll(String alert) {
		return PushPayload.alertAll(alert);
	}
	
	/**
	 * 构建推送对象<br>
	 * 针对所有平台，所有设备<br>
	 * 
	 * @param alert
	 *            通知内容<br>
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.alertAll(alert);
	}

	/**
	 * 构建推送对象<br>
	 * 所有平台，推送目标是别名为alias，通知内容为alert<br>
	 * 
	 * @param alias
	 *            推送目标别名<br>
	 * @param alert
	 *            通知内容<br>
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alias, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(alert)).build();
	}

	/**
	 * 构建推送对象<br>
	 * 平台是 Android，目标是tag的设备，内容通知为alert，并且标题为title<br>
	 * 
	 * @param tag
	 *            目标设备<br>
	 * @param alert
	 *            通知内容<br>
	 * @param title
	 *            标题<br>
	 * @return
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String alert, String title) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag(tag))
				.setNotification(Notification.android(alert, title, null)).build();
	}

	/**
	 * 构建推送对象<br>
	 * 平台是为iOS，推送目标是多个标签的交集，推送内容同时包括通知与消息，通知信息是alert<br>
	 * 角标数字为badge，通知声音为sound，并且附加字段 from = "JPush"<br>
	 * 消息内容是 msg_content,通知是APNs推送通道的，消息是JPush应用内消息通道的。<br>
	 * APNs的推送环境是“生产”（如果不显式设置的话，Library 会默认指定为开发）
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String alert, int badge,
			String sound, String key, String value, String msg_content, String... tags) {
		return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag_and(tags))
				.setNotification(Notification.newBuilder()
						.addPlatformNotification(IosNotification.newBuilder().setAlert(alert).setBadge(badge)
								.setSound(sound).addExtra(key, value).build())
						.build())
				.setMessage(Message.content(msg_content))
				.setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}

	/**
	 * 构建推送对象<br>
	 * 平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）交（"alias1" 与 "alias2" 的并集）<br>
	 * 推送内容是 - 内容为 MSG_CONTENT 的消息，并且附加字段 from = JPush<br>
	 * @param key
	 * @param value
	 * @param msg_content
	 * @param alias
	 * @param tags
	 * @return
	 */
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String key, String value,
			String msg_content, String[] alias, String... tags) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios())
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tags))
						.addAudienceTarget(AudienceTarget.alias(alias)).build())
				.setMessage(Message.newBuilder().setMsgContent(msg_content).addExtra(key, value).build()).build();
	}

}
