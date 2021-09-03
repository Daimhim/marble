package org.daimhim.marble.impl;

import org.daimhim.marble.Compressor;
import org.daimhim.marble.Marble;
import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;
import org.daimhim.marble.inner.INetWork;
import org.daimhim.marble.inner.PebblesCall;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author : Zhangx
 * date : 2021/6/3 17:33
 * description :
 */
public class OkhttpNWImpl implements INetWork {
    private OkHttpClient okHttpClient;
    private Request request;
    private SandySoil rb;

    public OkhttpNWImpl(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    @Override
    public Pebbles execute() {
        Pebbles pebbles = new Pebbles();
        try {
            Response execute = okHttpClient
                    .newCall(request)
                    .execute();
            pebbles = new Pebbles(new OkhttpSCImpl(execute));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pebbles;
    }

    @Override
    public void execute(final PebblesCall pebblesCall) {
        okHttpClient
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        OkhttpSCImpl okhttpSC = new OkhttpSCImpl(null);
                        okhttpSC.e = e;
                        pebblesCall.call(new Pebbles(okhttpSC));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        OkhttpSCImpl stoneCore = new OkhttpSCImpl(response);
                        stoneCore.code = response.code();
                        pebblesCall.call(new Pebbles(stoneCore));
                    }
                });
    }

    @Override
    public INetWork newCall(SandySoil sandySoil) {
        this.rb = sandySoil;
        this.request = createRequest(rb, build(rb));
        return this;

    }

    @Override
    public boolean cancel(String tag) {
        return false;
    }


    private RequestBody build(SandySoil rp) {
        RequestBody body  = null;
        if (Compressor.JSON.equals(rp.getContentType())){
            body = FormBody.create(MediaType.parse(Compressor.JSON),
                    Marble.getConfig().getGson().toJson(rp.getForm()));
        }else if (Compressor.FORM.equals(rp.getContentType())){
            FormBody.Builder builder = new FormBody
                    .Builder();
            for (Map.Entry<String ,Object> entry:
                    rp.getForm().entrySet()) {
                builder
                        .add(entry.getKey(),entry.getValue().toString());
            }
            body = builder.build();
        }
        return body;
    }

    private Request createRequest(
            SandySoil sandySoil,
            RequestBody body) {
        Request.Builder builder = new Request.Builder()
                .url(String.format("%s%s", checkUrl(sandySoil.getUrl()), mapToUrl(sandySoil.getUrlParameter())));
        for (Map.Entry<String ,String> entry:
                Marble.getConfig().getDefaultHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String ,String> entry:
                sandySoil.getHeaders().entrySet()) {
            builder.addHeader(entry.getKey(), entry.getValue());
        }
        switch (sandySoil.getMethod()){
            case "POST" : {
                builder.post(body);
            }
            case "DELETE" : {
                builder.delete(body);
            }
            case "PUT" : {
                builder.put(body);
            }
            case "PATCH" : {
                builder.patch(body);
            }
            default: {
                builder.get();
            }
        }
        builder.tag(sandySoil.getTag());
        return builder.build();
    }

    protected  String checkUrl(String url) {
        if (url.contains(":")) {
            return url;
        }
        return String.format("%s%s",Marble.getConfig().getDefaultUrl(),url);
    }

    private String mapToUrl(Map<String, String> map) {
        StringBuilder stringBuffer = new StringBuilder();
        if (!map.isEmpty()){
            stringBuffer
                    .append("?");
        }
        for (Map.Entry<String,String > entry : map.entrySet()) {
            stringBuffer
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        if (!map.isEmpty() && stringBuffer.length() != 0){
            stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
        return stringBuffer.toString();
    }
}
