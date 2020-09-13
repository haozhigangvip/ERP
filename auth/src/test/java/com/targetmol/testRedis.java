package com.targetmol;

import com.alibaba.fastjson.JSON;

import com.targetmol.UcenterAuthApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = UcenterAuthApplication.class)
@RunWith(SpringRunner.class)
public class testRedis {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void test_Redis(){
        //定义KEY
        String key="user_token:223bbaec-2faa-4728-abb6-9ee48ac11433";
        //定义Value
        Map<String,String> value=new HashMap<>();
        value.put("access_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VycGljIjpudWxsLCJ1c2VyX25hbWUiOiJpdGNhc3QiLCJzY29wZSI6WyJhcHAiXSwibmFtZSI6bnVsbCwidXR5cGUiOm51bGwsImlkIjpudWxsLCJleHAiOjE1OTk4NzkxOTEsImp0aSI6IjIyM2JiYWVjLTJmYWEtNDcyOC1hYmI2LTllZTQ4YWMxMTQzMyIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.YmCZg6Hk-uLssciYIqnM3xeUcN-dhXejpIY-irLsL5sBjKu4EoX6C3cO8fKbesZdYxGcOp_HWU2_qWGZVgei51mV40l9gjdXlULtkEy-IcZ9yyufd01Y3KWX0zh4tR4ZekazHPsXrqwhyj4Wvr6z6aSkHQ2GtL3slMpOhGZi6-p4g53EDXGBRJgjrWsdR7WOjZ1FWOR4XHSR-_8oCL-7h59MkZhxbFXiSyYH0_WuDAKq8ESyratHwRlEH9D8HnyiOOxs1S220rGsYPXMTVx0K8a3yheAY2YquVlBrhb2u_WFHpTCN-Mz97lIq3nlTgCqNh9YqU3JwG-g2NtZzwWqHg");
        value.put("refresh_token","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VycGljIjpudWxsLCJ1c2VyX25hbWUiOiJpdGNhc3QiLCJzY29wZSI6WyJhcHAiXSwiYXRpIjoiMjIzYmJhZWMtMmZhYS00NzI4LWFiYjYtOWVlNDhhYzExNDMzIiwibmFtZSI6bnVsbCwidXR5cGUiOm51bGwsImlkIjpudWxsLCJleHAiOjE1OTk4NzkxOTEsImp0aSI6ImUxMTVmYjkzLTE5N2UtNGRhMi05ZTM1LTYzMmVjN2FhNDQ4MyIsImNsaWVudF9pZCI6IlhjV2ViQXBwIn0.b2WqbJ3kkSPL56_AFdrSIQocsGFyukaSoOwIZtDdYifEqEhipd0FRTe30m5-oqnDlosklWeJi3wW74eb_IohXDfLaEZbjNOPjm8NAkwzIKEsYu6xNCQ3Q9BtSFtzjKwCDxjJl6S1FLIj_DvSx-mg2ROT6-Qs0dSEccp7AARuDLD8EoQft_whD572p2ZwH9zv5yZDpZOSeZo8K2z3-08dKOnu-EBXh0EYssEQdMD6EvP8mSO6ti4tS1FWk9Enlta2EH0jTb94GyIqXUoPvFIhXeP4461sDjnaKN1icZCIK4w_KOodmeVhwsspToUnhXbLY4zSjWAzsmULn3BrJcfkFg");
        String jsonString=JSON.toJSONString(value);
        //存储数据,保留60秒
        stringRedisTemplate.boundValueOps(key).set(jsonString,60, TimeUnit.SECONDS);
        //校验Key是否存在,不存在返回-2
        Long expire=stringRedisTemplate.getExpire(key,TimeUnit.SECONDS);
        System.out.println("剩余过期时间："+expire);

        //读取数据
        String resvalue=stringRedisTemplate.opsForValue().get(key);
        System.out.println(resvalue);

    }
}
