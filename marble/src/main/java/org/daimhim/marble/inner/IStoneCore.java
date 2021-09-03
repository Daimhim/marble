package org.daimhim.marble.inner;

import org.daimhim.marble.SandySoil;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public abstract class IStoneCore {
    /**
     * 请求错误时
     */
    public Exception e = null;
    public SandySoil sandySoil = null;
    public int code = -1;
    /**
     * 数据转为string
     * @return
     */
    public abstract  String string();

    /**
     * 原始的数据流
     * @return
     */
    public abstract InputStream byteStream();

    /**
     * 手动断开连接
     */
    public abstract void disconnect();

    public abstract List<String> headers(String name);

    public abstract String header(String name);

    public abstract String header(String name, String defaultValue);

    public abstract Map<String, List<String>> headers();
}
