package network.base.com.basenetwork.http.httplib;

import org.json.JSONObject;

import java.util.Map;

/**
 * volley response数据封装
 * @author cuu
 */
public class LibVolleyResponseModel {
    //response头
    public Map<String, String> headers;
    //response status
    public int status;
    //response 数据
    public JSONObject data;
}
