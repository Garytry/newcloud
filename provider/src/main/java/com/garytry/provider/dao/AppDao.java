package com.garytry.provider.dao;

import com.garytry.provider.base.BaseDao;
import com.garytry.provider.model.App;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

@Mapper
public interface AppDao {

   //@Insert("insert into SYS_APP values( #{appid},#{clientid},#{secret})")
   void save(App app);

   @Select("select * from SYS_APP")
    List<App> findAll();

   @Select("select * from SYS_APP where appid=#{appid}")
    App findByID(String appid);

    @Select("select * from SYS_APP where clientid=#{clientid}")
    App findByClientId(String clientid);

}
