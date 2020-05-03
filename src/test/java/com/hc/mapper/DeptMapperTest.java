package com.hc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.bean.Dept;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 梁云亮
 * @Date 2020/5/1 0:01
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class DeptMapperTest {

    @Resource
    private DeptMapper deptMapper;

    @Test
    public void fun() {
        System.out.println(deptMapper);
    }

    @Test
    public void selectList() {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        List<Dept> deptList = deptMapper.selectList(queryWrapper);
        deptList.forEach(System.out::println);
    }
}