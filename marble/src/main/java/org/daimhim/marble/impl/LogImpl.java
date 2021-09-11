package org.daimhim.marble.impl;

import static okhttp3.internal.platform.Platform.INFO;

import org.daimhim.marble.JSONUtil;
import org.daimhim.marble.Marble;
import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;
import org.daimhim.marble.inner.INetWork;
import org.daimhim.marble.inner.PebblesCall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

public class LogImpl implements INetWork {

    private INetWork iNetWork;

    public LogImpl(INetWork netWork) {
        iNetWork = netWork;
    }

    @Override
    public Pebbles execute() {
        Pebbles execute = iNetWork.execute();
        onLog(execute);
        return execute;
    }

    @Override
    public void execute(final PebblesCall pebblesCall) {
        iNetWork.execute(new PebblesCall() {
            @Override
            public void call(Pebbles pebbles) {
                onLog(pebbles);
                pebblesCall.call(pebbles);
            }
        });
    }

    @Override
    public INetWork newCall(SandySoil sandySoil) {
        iNetWork.newCall(sandySoil);
        return this;
    }

    @Override
    public boolean cancel(String tag) {
        return iNetWork.cancel(tag);
    }


    public void onLog(Pebbles pebbles) {
        if (!Marble.getConfig().isDebug()){
            return;
        }
        SandySoil sandySoil = pebbles.sandySoil();
        log(String.format("--> %s %s", sandySoil.getMethod(), sandySoil.getUrl()));
        log(String.format("--> Headers: %s", JSONUtil.toJson(sandySoil.getHeaders())));
        log(String.format("--> ContentType: %s", sandySoil.getContentType()));
        log(String.format("--> UrlParameter: %s", sandySoil.getUrlParameter()));
        log(String.format("--> Form: %s", sandySoil.getForm()));

        log(String.format("<-- Headers: %s",JSONUtil.toJson(pebbles.headers())));
        if (pebbles.isSuccessful()) {
            if (bodyHasEncoding(pebbles)){
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(pebbles.byteStream()));
                    StringWriter out = new StringWriter();
                    int DEFAULT_BUFFER_SIZE = 4 * 1024;
                    char[] buffer = new char[DEFAULT_BUFFER_SIZE];
                    int chars = reader.read(buffer);
                    while (chars >= 0) {
                        out.write(buffer, 0, chars);
                        chars = reader.read(buffer);
                    }
                    log(String.format("<-- Body: %s", out.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else {
                log("<-- Body: encoded body omitted");
            }
        }else if (!pebbles.isSuccessful()){
            log(String.format("<-- Error: %s", pebbles.error() == null ? pebbles.code() : pebbles.error().getMessage()));
        }
        log(String.format("<-- %s %s", sandySoil.getMethod(), sandySoil.getUrl()));
    }

    public void log(String log) {
        Platform.get().log(INFO, String.format("Marble: %s",log), null);
    }

    private static boolean bodyHasUnknownEncoding(Pebbles pebbles) {
        String contentEncoding = pebbles.header("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }
    private static boolean bodyHasEncoding(Pebbles pebbles) {
        String contentEncoding = pebbles.header("Content-Type");
        if (contentEncoding == null){
            contentEncoding = pebbles.header("Content-Encoding");
        }
        if (contentEncoding == null){
            return false;
        }
        return  contentEncoding.equalsIgnoreCase("application/json")
                || contentEncoding.equalsIgnoreCase("application/json;charset=UTF-8");
    }
}
