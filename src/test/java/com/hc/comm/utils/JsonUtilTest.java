package com.hc.comm.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hc.bean.Dept;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: 梁云亮
 * @Date 2020/5/2 17:31
 * @Description:
 */
class JsonUtilTest {

    @Test
    void obj2String() {
        Dept dept = new Dept(10, "SALES", "CHICAGO");
        String res = JsonUtil.obj2String(dept);
        System.out.println(res);

        List<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        String res1 = JsonUtil.obj2String(list);
        System.out.println(res1);

        ArrayList<Dept> depts = new ArrayList<>();
        depts.add(new Dept(10,"ACCOUNTING","NEWYORK"));
        depts.add(new Dept(20,"RESEARCH","DALLAS"));
        depts.add(new Dept(30,"SALES","CHICAGO"));
        depts.add(new Dept(40,"OPERATIONS","BOSTON"));
        String res2 = JsonUtil.obj2String(depts);
        System.out.println(res2);
    }

    @Test
    void obj2StringPretty() {
        Dept dept = new Dept(10, "SALES", "CHICAGO");
        String res = JsonUtil.obj2StringPretty(dept);
        System.out.println(res);
    }

    @Test
    void string2Obj() {
        String json = "{\"deptno\":10,\"dname\":\"SALES\",\"loc\":\"CHICAGO\"}";
        Dept dept = JsonUtil.string2Obj(json, Dept.class);
        System.out.println(dept.getDname());
    }

    @Test
    void testString2Obj() {
        String json1="[\"aaa\",\"bbb\",\"ccc\"]";
        List<String> list1 = JsonUtil.string2Obj(json1,new TypeReference<List<String>>() {});
        System.out.println(list1.get(1));

        String json2 ="[{\"deptno\":10,\"dname\":\"ACCOUNTING\",\"loc\":\"NEWYORK\"},{\"deptno\":20,\"dname\":\"RESEARCH\",\"loc\":\"DALLAS\"},{\"deptno\":30,\"dname\":\"SALES\",\"loc\":\"CHICAGO\"},{\"deptno\":40,\"dname\":\"OPERATIONS\",\"loc\":\"BOSTON\"}]";
        List<Dept> list2 = JsonUtil.string2Obj(json2,new TypeReference<List<Dept>>() {});
        System.out.println(list2.get(2).getDname());
    }

    @Test
    void testString2Obj1() {
        String json ="[{\"deptno\":10,\"dname\":\"ACCOUNTING\",\"loc\":\"NEWYORK\"},{\"deptno\":20,\"dname\":\"RESEARCH\",\"loc\":\"DALLAS\"},{\"deptno\":30,\"dname\":\"SALES\",\"loc\":\"CHICAGO\"},{\"deptno\":40,\"dname\":\"OPERATIONS\",\"loc\":\"BOSTON\"}]";
        List<Dept> list = JsonUtil.string2Obj(json,List.class,Dept.class);
        System.out.println(list.get(2));
    }

    @Test
    void builder() {
        String json = JsonUtil.builder()
                .put("id", 123)
                .put("name", "zhangsan")
                .put("birth", LocalDate.now())
                .put("gender", true)
                .build();
        System.out.println(json);
    }

}