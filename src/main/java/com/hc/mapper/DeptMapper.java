package com.hc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hc.bean.Dept;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 梁云亮
 * @Date 2020/4/30 23:56
 * @Description:
 */   
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {
}