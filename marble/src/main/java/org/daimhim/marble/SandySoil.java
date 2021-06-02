package org.daimhim.marble;

import java.util.HashMap;
import java.util.Map;

public class SandySoil {
    private String method = "GET";
    private String url = "";
    private String tag = "default";
    private Map<String,String> headers = new HashMap<>();
    private Map<String,String> urlParameter = new HashMap<>();
    private Map<String,Object> form = new HashMap<>();
    private String contentType = Compressor.FORM;
    private var iNetWork: INetWork;
}
