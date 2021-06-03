package org.daimhim.marble;

import org.daimhim.marble.inner.IStoneCore;

import java.io.InputStream;

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

    public String string(){
        return stoneCore.string();
    }

    public InputStream byteStream(){
        return stoneCore.byteStream();
    }

    public Exception error(){
        return stoneCore.error();
    }

    public boolean isSuccessful()  {
        if (stoneCore == null){
            return false;
        }
        if (stoneCore.error() == null){
            return true;
        }
        return false;
    }
}
