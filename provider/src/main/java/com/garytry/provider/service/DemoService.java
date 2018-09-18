package com.garytry.provider.service;

import com.garytry.provider.model.App;

import java.util.List;

public interface DemoService {

    List<App> listApp();
    App getApp(String appid);
    void save(App app);
}
