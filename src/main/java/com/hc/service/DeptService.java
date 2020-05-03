package com.hc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hc.bean.Dept;

/**
 * @Author: 梁云亮
 * @Date 2020/4/30 23:56
 * @Description:
 */
public interface DeptService extends IService<Dept> {
    Dept findDeptByDeptno(Integer deptno);

    Dept updateDept(Dept dept);

    int deleteDeptByDeptno(Integer deptno);
}
