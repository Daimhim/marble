package org.daimhim.marble.impl;

import static okhttp3.internal.platform.Platform.INFO;

import org.daimhim.marble.JSONUtil;
import org.daimhim.marble.Marble;
import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;
import org.daimhim.marble.inner.INetWork;
import org.daimhim.marble.inner.PebblesCall;

import java.nio.charset.Charset;
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
        log(String.format("Headers: %s", JSONUtil.toJson(sandySoil.getHeaders())));
        log(String.format("ContentType: %s", sandySoil.getContentType()));
        log(String.format("UrlParameter: %s", sandySoil.getUrlParameter()));
        log(String.format("Form: %s", sandySoil.getForm()));
        if (pebbles.isSuccessful()) {
            if (bodyHasUnknownEncoding(pebbles)){
                log("Body:encoded body omitted");
            }/*else {
//                pebbles.byteStream()
//                log(String.format("Body:%s", pebbles.code()));
            }*/
        }else if (!pebbles.isSuccessful()){
            log(String.format("Body:%s", pebbles.error() == null ? pebbles.code() : pebbles.error().getMessage()));
        }
        log(String.format("<-- %s %s", sandySoil.getMethod(), sandySoil.getUrl()));
    }

    public void log(String log) {
        Platform.get().log(INFO, log, null);
    }

    private static boolean bodyHasUnknownEncoding(Pebbles pebbles) {
        String contentEncoding = pebbles.header("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }
}
