package org.daimhim.marble.impl;

import org.daimhim.marble.inner.IStoneCore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;

/**
 * author : Zhangx
 * date : 2021/6/3 11:07
 * description :
 */
public class JavaSCImpl extends IStoneCore {
    private final HttpURLConnection httpURLConnection;

    public JavaSCImpl(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    @Override
    public String string() {
        if (httpURLConnection == null){
            return null;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(byteStream()));
            StringWriter out = new StringWriter();
            int DEFAULT_BUFFER_SIZE = 4 * 1024;
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            int chars = reader.read(buffer);
            while (chars >= 0) {
                out.write(buffer, 0, chars);
                chars = reader.read(buffer);
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
            setE(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            disconnect();
        }
        return null;
    }

    @Override
    public InputStream byteStream() {
        if (httpURLConnection == null){
            return null;
        }
        try {
            return httpURLConnection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            setE(e);
        }
        return null;
    }

    @Override
    public void disconnect() {
        if (httpURLConnection == null){
            return;
        }
        httpURLConnection.disconnect();
    }
}
