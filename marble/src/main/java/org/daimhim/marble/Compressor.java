package org.daimhim.marble;

import org.daimhim.marble.inner.INetWork;

import java.util.HashMap;
import java.util.Map;

public class Compressor {

    public static String JSON = "application/json;charset=utf-8";
    public static String FORM = "application/x-www-form-urlencoded";

    public static Build obtain(INetWork iNetWork) {
        return new Build(iNetWork);
    }

    public static class Build{
        public Build(INetWork iNetWork) {
            this.netWork = iNetWork;
        }

        private String method = "GET";
        private String url = "";
        private String tag = "default";
        private int readTimeout = -1;
        private int connectTimeout = -1;
        private Map<String,String> headers = new HashMap<>();
        private Map<String,String> urlParameter = new HashMap<>();
        private Map<String,Object> form = new HashMap<>();
        private String contentType = Compressor.FORM;
        private INetWork netWork;

        public Build setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Build setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Build addHeaders(String key, String value)  {
            this.headers.put(key,value);
            return this;
        }

        public Build addUrlParameter(String key , String value) {
            this.urlParameter.put(key,value);
            return this;
        }

        public Build bindTag(String t) {
            this.tag = t;
            return this;
        }

        public Build addParameter(String key, Object value) {
            this.form.put(key,value);
            return this;
        }

        public Build addParameter(Map<String, Object> form) {
            this.form.putAll(form);
            return this;
        }

        public Build addMultiPart(){
            return this;
        }

        public Build form() {
            this.contentType = FORM;
            return this;
        }

        public Build json() {
            this.contentType = JSON;
            return this;
        }

        public Build multipart(String mediaType){
            this.contentType = mediaType;
            return this;
        }

        public INetWork get(String url)  {
            this.method = "GET";
            this.url = url;
            return this.netWork.newCall(build());
        }

        public INetWork post(String url) {
            this.method = "POST";
            this.url = url;
            return this.netWork.newCall(build());
        }

        public INetWork put(String url) {
            this.method = "PUT";
            this.url = url;
            return this.netWork.newCall(build());
        }

        public INetWork delete(String url)  {
            this.method = "DELETE";
            this.url = url;
            return this.netWork.newCall(build());
        }

        public INetWork patch(String url)  {
            this.method = "PATCH";
            this.url = url;
            return this.netWork.newCall(build());
        }
        private SandySoil build(){
            SandySoil sandySoil = new SandySoil();
            sandySoil.method = this.method;
            sandySoil.url = this.url;
            sandySoil.tag = this.tag;
            sandySoil.contentType = this.contentType;
            sandySoil.netWork = this.netWork;
            sandySoil.headers = this.headers;
            sandySoil.urlParameter = this.urlParameter;
            sandySoil.form = this.form;
            return sandySoil;
        }
    }


}
