package org.daimhim.marble.inner;

import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;

/**
 * author : Zhangx
 * date : 2021/6/3 10:34
 * description :
 */
public interface INetWork {
    /**
     * 获取请求参数
     * @return
     */
    SandySoil request();

    /**
     * 同步请求
     * @return
     */
    Pebbles execute();

    /**
     * 异步请求
     * @param pebblesCall
     */
    void execute(PebblesCall pebblesCall);

    /**
     * 创建一个请求环境
     * @param sandySoil
     * @return
     */
    INetWork newCall(SandySoil sandySoil);

    /**
     * 取消
     * @return
     */
    boolean cancel();

}
