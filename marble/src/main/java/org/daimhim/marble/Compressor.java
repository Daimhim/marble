package org.daimhim.marble;

public class Compressor {
    public static String JSON = "application/json;charset=utf-8";
    public static String FORM = "application/x-www-form-urlencoded";

    public static Compressor obtain(INetWork iNetWork) {
        return obtain(SandySoil(),iNetWork);
    }
    public static Compressor obtain(SandySoil sandySoil, iNetWork: INetWork){
        return
    }
    public static Compressor obtain(SandySoil sandySoil){
        return Compressor().also {
            it.sandySoil = sandySoil
        }
    }
}
