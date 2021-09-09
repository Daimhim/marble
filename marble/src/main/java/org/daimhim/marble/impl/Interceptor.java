package org.daimhim.marble.impl;

import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;
import org.daimhim.marble.inner.INetWork;
import org.daimhim.marble.inner.PebblesCall;

/**
 * author : Zhangx
 * date : 2021/9/3 16:51
 * description :
 */
public class Interceptor {
    private INetWork preINetWork;

    public void setPreINetWork(INetWork preINetWork) {
        this.preINetWork = preINetWork;
    }

    /**
     * 同步请求
     * @return
     */
    public Pebbles execute(){
        return preINetWork.execute();
    }

    /**
     * 异步请求
     * @param pebblesCall
     */
    public void execute(final PebblesCall pebblesCall){
        preINetWork.execute(new PebblesCall() {
            @Override
            public void call(Pebbles pebbles) {
                pebblesCall.call(pebbles);
            }
        });
    }
}
