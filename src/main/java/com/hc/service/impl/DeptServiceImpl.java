package com.hc.service.impl;

import com.hc.bean.Dept;
import com.hc.mapper.DeptMapper;
import com.hc.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;

/**
 * @Author: 梁云亮
 * @Date 2020/4/30 23:56
 * @Description:
 */
@Slf4j
@Service
@CacheConfig(cacheManager = "cacheManager", cacheNames = "dept")
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    @Cacheable(keyGenerator = "myKeyGen")
    public Dept findDeptByDeptno(Integer deptno) {
        log.info("DeptService findDeptByDeptno ...");
        Dept dept = deptMapper.selectById(deptno);
        return dept;
    }

    @CachePut(keyGenerator = "updateByIdKeyGen")
    @Override
    public Dept updateDept(Dept dept) {
        log.info("DeptService updateDept ...");
        int res = deptMapper.updateById(dept);
        return dept;
    }

    @CacheEvict(keyGenerator = "deleteByIdKeyGen")
    @Override
    public int deleteDeptByDeptno(Integer deptno) {
        log.info("DeptService deleteDeptByDeptno ...");
        int res = deptMapper.deleteById(deptno);
        return res;
    }
}