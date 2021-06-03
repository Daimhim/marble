package org.daimhim.marble.inner;

import java.io.InputStream;

public abstract class IStoneCore {
    private Exception e;
    public abstract  String string();
    public abstract InputStream byteStream();
    public abstract void disconnect();

    public Exception error() {
        return e;
    }

    public void setE(Exception e) {
        this.e = e;
    }
}
