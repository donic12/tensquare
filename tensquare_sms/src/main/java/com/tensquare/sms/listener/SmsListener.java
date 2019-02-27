package com.tensquare.sms.listener;


import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SMSUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 短信监听类
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    private SMSUtil smsUtil;

    /**
     * 发送短信
     *
     * @param map
     */
    @RabbitHandler
    public void sendSms(Map<String, String> map) throws ClientException {
        SendSmsResponse response = smsUtil.sendSms(
                map.get("mobile"),
                map.get("code"));
        System.err.println("---invoke sms start---");
        System.err.println("Code=" + response.getCode());
        System.err.println("Message=" + response.getMessage());
        System.err.println("RequestId=" + response.getRequestId());
        System.err.println("BizId=" + response.getBizId());
        System.err.println("---      end       ---");
    }

}
