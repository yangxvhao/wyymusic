package com.yangxvhao.proxy.model;

import lombok.Data;

/**
 * @author yangxvhao
 * @date 17-12-28.
 */
@Data
public class HttpProxy {

    private String host;

    private String port;

    public HttpProxy(){}

    public HttpProxy(HttpProxy httpProxy){
        this.host = httpProxy.host;
        this.port = httpProxy.port;
    }

    public String getKey(){
        return String.format("%s:$s", host, port);
    }
}
