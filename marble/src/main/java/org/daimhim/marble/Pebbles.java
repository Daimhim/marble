package org.daimhim.marble;

import org.daimhim.marble.inner.IStoneCore;

import java.io.*;

/**
 * author : Zhangx
 * date : 2021/6/3 10:29
 * description :
 */
public class Pebbles {
    private IStoneCore stoneCore;

    public Pebbles() {
    }

    public Pebbles(IStoneCore stoneCore) {
        this.stoneCore = stoneCore;
    }

    public void write(File file,int byteSize) {
        InputStream inputStream = byteStream();
        if (byteSize == -1){
            byteSize = 1024;
        }
        int index;
        byte[] bytes = new byte[byteSize];
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            while ((index = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, index);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            inputStream.close();
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            stoneCore.setE(e);
        }
    }

    public String string() {
        return stoneCore.string();
    }

    public InputStream byteStream() {
        return stoneCore.byteStream();
    }

    public Exception error() {
        return stoneCore.error();
    }

    public boolean isSuccessful() {
        if (stoneCore == null) {
            return false;
        }
        if (stoneCore.error() == null) {
            return true;
        }
        return false;
    }

    public void disconnect() {
        stoneCore.disconnect();
    }
}
