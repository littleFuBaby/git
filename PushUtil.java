package com.jfbank.qualitymall.base.util;

import com.jfbank.push.rpc.IPushRpc;
import com.jfbank.push.rpc.dto.req.RpcAndroidAliasPushReqDto;
import com.jfbank.push.rpc.dto.req.RpcIOSAliasPushReqDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cuihl
 * @Description: 推送工具类
 * Copyright: Copyright (c) 2016
 * @date 2016/12/06 10:01
 */
@Component
public class PushUtil {
    @Autowired
    private IPushRpc pushRpc;
    private Logger logger = Logger.getLogger(this.getClass());
    private static PushUtil pushUtil;
    @PostConstruct
    public void init() {
        pushUtil=this;
        pushUtil.pushRpc=this.pushRpc;
    }
    public PushUtil(){
    }

    public static void send(){
        //推送消息给接收人
        new PushUtil().new SendCustomizedcast("alert",1,new HashMap<String, String>(),new ArrayList<String>()).sendIosUser();
        new PushUtil().new SendCustomizedcast("alert","title",new HashMap<String, String>(),new ArrayList<String>()).sendAndroidUser();
    }


    public class  SendCustomizedcast{
        private String alert;
        private int badge;
        private boolean testMode=true;
        private Map<String,String> customizedField;
        private List<String> alias;
        private String title;
        public SendCustomizedcast(String alert,int badge,Map<String,String> customizedField,List<String> alias){
            this.alert=alert;
            this.badge=badge;
            this.customizedField=customizedField;
            this.alias=alias;
        }
        public SendCustomizedcast(String alert,String title,Map<String,String> customizedField,List<String> alias){
            this.alert=alert;
            this.title=title;
            this.customizedField=customizedField;
            this.alias=alias;
        }
        public void sendIosUser(){
            try {
                RpcIOSAliasPushReqDto req=getRpcIOSPushReqDto();
                logger.info("iOS消息推送："+req.toString());
                PushUtil.pushUtil.pushRpc.sendIOSAlias(req);
            } catch (Exception e) {
                logger.error("推送消息异常：",e);
            }
        }
        public void sendAndroidUser(){
            try {
                RpcAndroidAliasPushReqDto req=getRpcAndroidPushReqDto();
                PushUtil.pushUtil.pushRpc.sendAndroidAlias(req);
                logger.info("Android消息推送："+req.toString());
            } catch (Exception e) {
                logger.error("推送消息异常：",e);
            }
        }
        public RpcIOSAliasPushReqDto getRpcIOSPushReqDto(){
            RpcIOSAliasPushReqDto req=new RpcIOSAliasPushReqDto();
            req.setAliases(alias);
            req.setAlert(alert);
            req.setBadge(badge);
            req.setTestMode(testMode);
            req.setCustomizedField(customizedField);
            return req;
        }
        public RpcAndroidAliasPushReqDto getRpcAndroidPushReqDto(){
            RpcAndroidAliasPushReqDto req=new RpcAndroidAliasPushReqDto();
            req.setAliases(alias);
            req.setAlert(alert);
            req.setTitle(title);
            req.setCustomizedField(customizedField);
            return req;
        }
    }
}
