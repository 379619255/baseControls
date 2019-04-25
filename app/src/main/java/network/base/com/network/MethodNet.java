package network.base.com.network;

import com.alibaba.fastjson.JSONObject;
import com.netease.it.utils.EncryptAlgorithm;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MethodNet {
    /**
     * 登陆数据封装
     *
     * @return
     */
    public static Map<String, String> loginData(final String userName, final String data, final String loginWay, final String password) {
        Map<String, Object> dataMap = new HashMap<String, Object>() {{
            put("encyData", data);
            put("loginWay", loginWay);
            put("userName",userName);
            put("password",password);
        }};
        Date date = new Date();
        String sign = null;
        try {
            sign = EncryptAlgorithm.hexMD5(App.APP_ID + App.SECRET + JSONObject.toJSONString(dataMap) + date.getTime()).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, String> paramSys = new HashMap<>();
        paramSys.put("appId", App.APP_ID);
        paramSys.put("sign", sign);
        paramSys.put("timestamp", date.getTime() + "");
        paramSys.put("version", "1.0");
        paramSys.put("bizContent", JSONObject.toJSONString(dataMap));
        paramSys.put("mapping", "toLogin.du");


        return paramSys;
    }
}
