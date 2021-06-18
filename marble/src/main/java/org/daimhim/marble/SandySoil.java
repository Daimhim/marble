package org.daimhim.marble;

import org.daimhim.marble.inner.INetWork;

import java.util.HashMap;
import java.util.Map;

public class SandySoil {
    protected String method;
    protected String url;
    protected String tag;
    protected String contentType;
    protected int readTimeout;
    protected int connectTimeout;
    protected INetWork netWork;
    protected Map<String,String> headers;
    protected Map<String,String> urlParameter;
    protected Map<String,Object> form;

    protected SandySoil() {
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getTag() {
        return tag;
    }

    public String getContentType() {
        return contentType;
    }

    public INetWork getNetWork() {
        return netWork;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getUrlParameter() {
        return urlParameter;
    }

    public Map<String, Object> getForm() {
        return form;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }
}
