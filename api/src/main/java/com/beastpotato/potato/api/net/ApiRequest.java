package com.beastpotato.potato.api.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ApiRequest<T> extends com.android.volley.Request<T> {
    private final Gson gson = new Gson();
    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final RequestCompletion<T> listener;

    public ApiRequest(int method, String url, Class<T> clazz, final RequestCompletion<T> completionListener) {
        super(method, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                completionListener.onError(error);
            }
        });
        this.clazz = clazz;
        this.headers = new HashMap<>();
        this.listener = completionListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public interface RequestCompletion<T> {
        void onResponse(T data);

        void onError(VolleyError error);
    }
}
