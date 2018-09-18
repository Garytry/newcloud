package com.garytry.provider.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class App implements Serializable {
    private String appid;

    private String clientid;

    private String secret;

    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getClientid() {
        return this.clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("appid",appid);
        json.put("clientid",this.clientid);
        json.put("secret",secret);
        return json.toString();
    }
}
