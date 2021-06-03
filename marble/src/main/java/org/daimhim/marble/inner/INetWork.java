package org.daimhim.marble.inner;

import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;

/**
 * author : Zhangx
 * date : 2021/6/3 10:34
 * description :
 */
public interface INetWork {
    SandySoil request();
    Pebbles execute();
    void execute(PebblesCall pebblesCall);
    INetWork newCall(SandySoil sandySoil);
    boolean cancel();

}
