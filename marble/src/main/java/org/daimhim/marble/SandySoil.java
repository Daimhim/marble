package org.daimhim.marble;

import java.util.Map;

/**
 * 请求体
 */
public class SandySoil {
    /**
     * 请求方式
     */
    protected String method;
    /**
     * 请求url
     */
    protected String url;
    /**
     * 请求标记
     */
    protected String tag;
    /**
     * 内容类型
     */
    protected String contentType;
    /**
     * 读取超时
     */
    protected int readTimeout;
    /**
     * 连接超时
     */
    protected int connectTimeout;
    /**
     * head
     */
    protected Map<String,String> headers;
    /**
     * 请求参数
     */
    protected Map<String,String> urlParameter;
    /**
     * 表单
     */
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
