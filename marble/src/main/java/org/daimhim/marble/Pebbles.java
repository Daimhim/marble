package org.daimhim.marble;

import org.daimhim.marble.inner.IStoneCore;

import java.io.*;
import java.util.List;
import java.util.Map;

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

    /**
     * 把数据流下载到指定文件
     * @param file  指定路径
     * @param byteSize 缓存大小
     */
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
            stoneCore.e = e;
        }
    }

    public String string() {
        return stoneCore.string();
    }

    public InputStream byteStream() {
        return stoneCore.byteStream();
    }

    public Exception error() {
        return stoneCore.e;
    }

    public boolean isSuccessful() {
        if (stoneCore == null) {
            return false;
        }
        if (stoneCore.e == null) {
            return true;
        }
        return false;
    }

    public void disconnect() {
        stoneCore.disconnect();
    }

    public int code(){
        return stoneCore.code;
    }

    /**
     * 请求体
     * @return 请求体
     */
    public SandySoil sandySoil(){
        return stoneCore.sandySoil;
    }


    public List<String> headers(String name){
        return stoneCore.headers(name);
    }

    public String header(String name){
        return stoneCore.header(name);
    }

    public String header(String name, String defaultValue){
        return stoneCore.header(name,defaultValue);
    }

    public Map<String, List<String>> headers(){
        return stoneCore.headers();
    }
}
