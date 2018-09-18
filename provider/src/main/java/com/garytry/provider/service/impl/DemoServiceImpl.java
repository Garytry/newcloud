package com.garytry.provider.service.impl;

import com.garytry.provider.dao.AppDao;
import com.garytry.provider.model.App;
import com.garytry.provider.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private AppDao appDao;

    @Override
    public List<App> listApp (){
        return appDao.findAll();
    }

    @Override
    public App getApp(String appid) {
        return appDao.findByID(appid);
    }

    @Override
    public void save(App app) {
        appDao.save(app);
    }
}
