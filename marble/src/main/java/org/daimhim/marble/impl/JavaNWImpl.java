package org.daimhim.marble.impl;

import org.daimhim.marble.Compressor;
import org.daimhim.marble.Marble;
import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;
import org.daimhim.marble.exc.JavaNetException;
import org.daimhim.marble.inner.INetWork;
import org.daimhim.marble.inner.PebblesCall;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author : Zhangx
 * date : 2021/6/3 11:35
 * description :
 */
public class JavaNWImpl implements INetWork {
    private final static ExecutorService fixedThreadPool  = Executors.newCachedThreadPool();
    private SandySoil request;
    @Override
    public SandySoil request() {
        return request;
    }

    @Override
    public Pebbles execute() {
        Pebbles pebbles = new Pebbles();
        StringBuffer stringBuffer = new StringBuffer(checkUrl(request.getUrl()));
        if (!request.getUrlParameter().isEmpty()){
            stringBuffer
                    .append("?");
        }
        for (Map.Entry<String,String > entry : request.getUrlParameter().entrySet()) {
            stringBuffer
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (!request.getUrlParameter().isEmpty() && stringBuffer.length() != 0){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        URL url = null;
        try {
            url = new URL(stringBuffer.toString());
            URLConnection openConnection = url.openConnection();
            openConnection.setRequestProperty("Accept-Charset", "utf-8");
            openConnection.setRequestProperty("Content-Type", request.getContentType());
            if (openConnection instanceof HttpURLConnection) {
                for (Map.Entry<String, String> entry :
                        request.getHeaders().entrySet()) {
                    openConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                HttpURLConnection httpURLConnection = ((HttpURLConnection) openConnection);
                httpURLConnection.setRequestMethod(request.getMethod());
                // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
                httpURLConnection.setInstanceFollowRedirects(true);
                // 设置超时时间
                httpURLConnection.setConnectTimeout(3000);
                // 设置是否输出
                httpURLConnection.setDoOutput(true);
                // 设置是否读入
                httpURLConnection.setDoInput(true);
                //连接
                openConnection.connect();
                if (Compressor.JSON.equals(request.getContentType())) {
                    httpURLConnection.getOutputStream().write(Marble.getConfig().getGson().toJson(request.getForm()).getBytes());
                    httpURLConnection.getOutputStream().flush();
                    httpURLConnection.getOutputStream().close();
                } else if (Compressor.FORM.equals(request.getContentType())) {
                    stringBuffer.setLength(0);
                    for (Map.Entry<String, Object> entry :
                            request.getForm().entrySet()) {
                        stringBuffer
                                .append(entry.getKey())
                                .append("=")
                                .append(entry.getValue())
                                .append("&");
                    }
                    if (stringBuffer.length() != 0) {
                        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    }
                    httpURLConnection.getOutputStream().write(stringBuffer.toString().getBytes());
                    httpURLConnection.getOutputStream().flush();
                    httpURLConnection.getOutputStream().close();
                }
                JavaSCImpl stoneCore = new JavaSCImpl(httpURLConnection);
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    pebbles = new Pebbles(stoneCore);
                    return pebbles;
                }
                stoneCore.setE(new JavaNetException(String.format("%s %s %s",responseCode,httpURLConnection.getResponseMessage(),url.getPath())));
                pebbles = new Pebbles(stoneCore);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pebbles;
    }

    @Override
    public void execute(final PebblesCall pebblesCall) {
        fixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Pebbles execute = execute();
                pebblesCall.call(execute);
            }
        });
    }

    @Override
    public INetWork newCall(SandySoil sandySoil) {
        request = sandySoil;
        return this;
    }

    @Override
    public boolean cancel() {
        return false;
    }

    protected  String checkUrl(String url) {
        if (url.contains(":")) {
            return url;
        }
        return String.format("%s%s",Marble.getConfig().getDefaultUrl(),url);
    }
}
