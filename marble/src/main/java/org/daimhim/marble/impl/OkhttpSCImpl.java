package org.daimhim.marble.impl;

import org.daimhim.marble.inner.IStoneCore;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.Response;

/**
 * author : Zhangx
 * date : 2021/6/3 17:44
 * description :
 */
public class OkhttpSCImpl extends IStoneCore {
    private Response response;

    public OkhttpSCImpl(Response response) {
        this.response = response;
    }

    @Override
    public String string() {
        if (response == null){
            return null;
        }
        try {
             if (response.body() != null){
                 return response.body().string();
             }
        } catch (IOException e) {
            e.printStackTrace();
            this.e = e;
        }
        return null;
    }

    @Override
    public InputStream byteStream() {
        if (response == null){
            return null;
        }
        if (response.body() != null){
            return response.body().byteStream();
        }
        return null;
    }

    @Override
    public void disconnect() {
        if (response == null){
            return;
        }
        response.close();
    }

    @Override
    public List<String> headers(String name) {
        return response.headers(name);
    }

    @Override
    public String header(String name) {
        return header(name, null);
    }

    @Override
    public String header(String name, String defaultValue) {
        String result = response.header(name);
        return result != null ? result : defaultValue;
    }

    @Override
    public Map<String, List<String>> headers() {
        return response.headers().toMultimap();
    }
}
