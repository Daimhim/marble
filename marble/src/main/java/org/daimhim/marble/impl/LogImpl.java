package org.daimhim.marble.impl;

import org.daimhim.marble.Pebbles;
import org.daimhim.marble.SandySoil;
import org.daimhim.marble.inner.INetWork;
import org.daimhim.marble.inner.PebblesCall;

public class LogImpl implements INetWork {
    private INetWork iNetWork;
    public LogImpl(INetWork netWork) {
        iNetWork = netWork;
    }

    @Override
    public SandySoil request() {
        return iNetWork.request();
    }

    @Override
    public Pebbles execute() {
        return iNetWork.execute();
    }

    @Override
    public void execute(PebblesCall pebblesCall) {
        iNetWork.execute(pebblesCall);
    }

    @Override
    public INetWork newCall(SandySoil sandySoil) {
        return iNetWork.newCall(sandySoil);
    }

    @Override
    public boolean cancel() {
        return iNetWork.cancel();
    }
}
