package org.redrock.gayligayli.service.loginAndRegister.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import net.sf.json.JSONObject;

public class SendUtil {
    private static final String ACCESS_KEY_SECRET = "qM60xBPa8B52djcW3MHHQD4Wxm1H7w";
    private static final String ACCESS_KEY_ID = "LTAI9OcpYcFmuNuO";
    private static final String SIGN_NAME = "胡仓的小站";
    private static final String TEMPLATE_CODE = "SMS_125020523";
    private static final String PRODUCT = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

    public static String sendSms(String telephone) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID,
                ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setMethod(MethodType.POST);
        request.setPhoneNumbers(telephone);
        request.setSignName(SIGN_NAME);
        request.setTemplateCode(TEMPLATE_CODE);
        JSONObject jsonObject = new JSONObject();
        String code = String.valueOf(Math.ceil(Math.random() * 1000));
        jsonObject.element("code", code);
        request.setTemplateParam(jsonObject.toString());
        SendSmsResponse response = acsClient.getAcsResponse(request);
        if (response.getCode() != null && "OK".equals(response.getCode())) {
            return code;
        }
        return null;

    }

}
