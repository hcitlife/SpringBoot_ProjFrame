package com.hc.comm.utils;

import com.hc.comm.utils.RedisUtil;
import com.hc.bean.Dept;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 梁云亮
 * @Date 2020/4/30 14:35
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class RedisUtilTest {

    @Resource
    private RedisUtil redisUtil;


    @Test
    public void fun() {
        System.out.println(redisUtil);
    }

    //////////////////////////////////// 普通 /////////////////////////////////////

    @Test
    void keys(){
        Set<Object> keys = redisUtil.keys();
        keys.forEach(System.out::println);
    }

    @Test
    void expire() {
        boolean res = redisUtil.expire("k2", 400);
        System.out.println(res);
    }

    @Test
    void getExpire() {
        long res = redisUtil.getExpire("k2");
        System.out.println(res);
    }

    @Test
    void exists() {
        boolean res = redisUtil.exists("k2");
        System.out.println(res);
    }

    @Test
    void del() {
        redisUtil.del("name", "age");
    }

    //////////////////////////////////// string /////////////////////////////////////
    @Test
    void set() {
        Dept dept = new Dept(10, "sales", "chicago");
        redisUtil.set("dept", dept);
    }

    @Test
    void set2() {
        boolean res = redisUtil.set("k1", "key1111", 120);
        System.out.println(res);
    }

    @Test
    void multiSet() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 18);
        map.put("birth", "1999-09-21");
        boolean res = redisUtil.multiSet(map);
        System.out.println(res);
    }

    @Test
    void get() {
        Object k2 = redisUtil.get("dept");
        System.out.println(k2);
    }

    @Test
    void get2() {//这个方法想要测试成功，需要set时，设置编码为JsonUtil.convertObj2String(value);
        Dept dept = redisUtil.get("dept", Dept.class);
        System.out.println(dept.getDname());
    }


    @Test
    void incr() {
        long res = redisUtil.incr("k1", 8);
        System.out.println(res);
    }

    @Test
    void decr() {
        long res = redisUtil.decr("k1", 4);
        System.out.println(res);
    }

    //////////////////////////////////// map/hash /////////////////////////////////////
    @Test
    void hset() {
        boolean res = redisUtil.hset("p", "name", "zhangsan");
        System.out.println(res);
    }


    @Test
    void testHset() {
        boolean res = redisUtil.hset("p", "age", 18, 120);
        System.out.println(res);
    }

    @Test
    void hmset() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 18);
        map.put("birth", "1999-09-21");
        boolean res = redisUtil.hmset("p2", map);
        System.out.println(res);
    }

    @Test
    void testHmset() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 18);
        map.put("birth", "1999-09-21");
        boolean res = redisUtil.hmset("p", map, 120);
        System.out.println(res);
    }

    @Test
    void hget() {
        Object res = redisUtil.hget("p2", "name");
        System.out.println(res);
    }

    @Test
    void hmget() {
        Map<Object, Object> res = redisUtil.hmget("p2");
        res.forEach((k,v)-> System.out.println(k+"\t"+v));
    }

    @Test
    void hdel() {
        redisUtil.hdel("p2","age");
    }

    @Test
    void hHasKey() {
        boolean res = redisUtil.hHasKey("p2", "name");
        System.out.println(res);
    }

    @Test
    void hincr() {
        double res = redisUtil.hincr("p", "age", 20);
        System.out.println(res);
    }

    @Test
    void hdecr() {
        double res = redisUtil.hdecr("p", "age", 30);
        System.out.println(res);
    }

    //////////////////////////////////// map/hash /////////////////////////////////////
    @Test
    void sSet() {
        long res = redisUtil.sSet("s1", "aa", "bb", 33, 11);
        System.out.println(res);
    }

    @Test
    void sSetWithExpire() {
        long res = redisUtil.sSetWithExpire("s2", 120, "abc", 321);
        System.out.println(res);
    }

    @Test
    void sGet() {
        Set<Object> res = redisUtil.sGet("s2");
        res.forEach(System.out::println);
    }

    @Test
    void sHasKey() {
        boolean res = redisUtil.sHasKey("s2", "abc");
        System.out.println(res);
    }

    @Test
    void sGetSetSize() {
        long res = redisUtil.sGetSetSize("s1");
        System.out.println(res);
    }

    @Test
    void setRemove() {
        long res = redisUtil.setRemove("s1", "aa", 22);
        System.out.println(res);
    }

    //////////////////////////////////// list /////////////////////////////////////

    @Test
    void lSet() {
        boolean res = redisUtil.lSet("list1", "aa");
        System.out.println(res);
    }

    @Test
    void testLSet() {
        boolean res = redisUtil.lSet("list1", "cc",240);
        System.out.println(res);
    }

    @Test
    void testLSets() {
        List<Object> list = new ArrayList<>();
        list.add("ww");
        list.add("ee");
        list.add("oo");
        boolean res = redisUtil.lSets("list1", list);
        System.out.println(res);
    }

    @Test
    void testLSets1() {
        List<Object> list = new ArrayList<>();
        list.add("ww");
        list.add("ee");
        list.add("oo");
        boolean res = redisUtil.lSets("list1", list,300);
        System.out.println(res);
    }

    @Test
    void lGet() {
        List<Object> res = redisUtil.lGet("list1", 0, -1);
        System.out.println(res);
    }
    @Test
    void lGetListSize() {
        long res = redisUtil.lGetListSize("list1");
        System.out.println(res);
    }

    @Test
    void lGetIndex() {
        Object res = redisUtil.lGetIndex("list1", 3);
        System.out.println(res);
    }


    @Test
    void lUpdateIndex() {
        boolean res = redisUtil.lUpdateIndex("list1", 3, "asedf");
        System.out.println(res);
    }

    @Test
    void lRemove() {
        long res = redisUtil.lRemove("list1", 2, "oo");
        System.out.println(res);
    }
}