package com.garytry.provider.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.garytry.provider.model.App;
import com.garytry.provider.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private DemoService demoServiceImpl;

    @RequestMapping("listApp")
    @ResponseBody
    public JSONArray listApp(){
        List<App> apps = demoServiceImpl.listApp();
        JSONArray jsons = new JSONArray();
        for (App app : apps) {
            JSONObject json = new JSONObject();
            json.put("appid",app.getAppid());
            json.put("clientid",app.getClientid());
            json.put("secret",app.getSecret());
            jsons.add(json);
        }
        JSONObject json = new JSONObject();
        return jsons;
    }

    @RequestMapping("getApp")
    @ResponseBody
    public String getApp(@RequestParam("appid") String appid){
        return demoServiceImpl.getApp(appid).toString();
    }

    @RequestMapping("save")
    public void save(){
        App app = new App();
        app.setAppid("001");
        app.setClientid("client001");
        app.setSecret("secret001");
        demoServiceImpl.save(app);
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/auth?useUnicode\\=true&characterEncoding\\=UTF-8&useOldAliasMetadataBehavior\\=true","root","root");
        PreparedStatement preparedStatement = connection.prepareStatement("select * from SYS_APP where appid= ?");
        preparedStatement.setString(1, "app1");
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("执行了");
        while (resultSet.next()){
            System.out.println(resultSet.getString("appid"));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }
        connection.close();

    }
}
