package org.daimhim.marble;

import com.google.gson.Gson;

import org.daimhim.marble.impl.JavaNWImpl;
import org.daimhim.marble.inner.INetWork;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Marble {

    private static Config sConfig = new Config(
            "",
            new JavaNWImpl(),
            new HashMap<String, String>(),
            JSONUtil.GSON);

    public static void init(Config sConfig) {
        Marble.sConfig = sConfig;
    }

    public static Compressor.Build with(){
        return with(sConfig.DEF_NET_WORK);
    }

    public static Compressor.Build with(INetWork netWork){
        return Compressor.obtain(netWork);
    }

    public static Config getConfig() {
        return sConfig;
    }

    public static class Config{
        private INetWork DEF_NET_WORK;
        private Map<String, String> defaultHeaders;
        private String defaultUrl = "";
        private Gson gson = JSONUtil.GSON;
        private List<INetWork> interceptor;

        public void addInterceptor(INetWork iNetWork){

        }

        public INetWork getDefNetWork() {
            return DEF_NET_WORK;
        }

        public Map<String, String> getDefaultHeaders() {
            return defaultHeaders;
        }

        public String getDefaultUrl() {
            return defaultUrl;
        }

        public Gson getGson() {
            return gson;
        }

        public Config(String defaultUrl,INetWork DEF_NET_WORK, Map<String, String> defaultHeaders,  Gson gson) {
            this.DEF_NET_WORK = DEF_NET_WORK;
            this.defaultHeaders = defaultHeaders;
            this.defaultUrl = defaultUrl;
            this.gson = gson;
        }
    }
}
