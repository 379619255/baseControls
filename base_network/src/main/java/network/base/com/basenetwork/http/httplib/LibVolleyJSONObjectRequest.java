package network.base.com.basenetwork.http.httplib;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * volley
 * @author cuu
 */
public class LibVolleyJSONObjectRequest extends Request<LibVolleyResponseModel> {
    private final Response.Listener<LibVolleyResponseModel> mListener;
    private final Map<String, String> mHeaders;
    private final Map<String, String> mParams;

    private static final String PROTOCOL_CHARSET = "utf-8";

    public LibVolleyJSONObjectRequest(int method, String url, Map<String, String> headers, Map<String, String> parmas,
                                      Response.Listener<LibVolleyResponseModel> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(20), DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mListener = listener;
        mHeaders = headers;
        mParams = parmas;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mHeaders != null) {
            return mHeaders;
        } else {
            return Collections.emptyMap();
        }

    }

    @Override
    protected Response<LibVolleyResponseModel> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            //返回模型
            LibVolleyResponseModel my_response = new LibVolleyResponseModel();
            my_response.headers = response.headers;
            my_response.status = response.statusCode;
            my_response.data = new JSONObject(jsonString);

            return Response.success(my_response,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(LibVolleyResponseModel response) {
        mListener.onResponse(response);
    }
}
