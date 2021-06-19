package org.daimhim.marble.impl;

import org.daimhim.marble.inner.IStoneCore;

import java.io.IOException;
import java.io.InputStream;

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
        try {
             if (response.body() != null){
                 return response.body().string();
             }
        } catch (IOException e) {
            e.printStackTrace();
            setE(e);
        }
        return null;
    }

    @Override
    public InputStream byteStream() {
        if (response.body() != null){
            return response.body().byteStream();
        }
        return null;
    }

    @Override
    public void disconnect() {
        response.close();
    }
}
