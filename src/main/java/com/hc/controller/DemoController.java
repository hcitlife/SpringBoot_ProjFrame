package com.hc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hc.bean.Dept;
import com.hc.comm.ex.CustomException;
import com.hc.comm.res.Result;
import com.hc.comm.res.ResultCode;
import com.hc.comm.res.ResultUtil;
import com.hc.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @Author: 梁云亮
 * @Date 2020/4/30 1:41
 * @Description:
 */

@RestController
@RequestMapping("/demo")
@Slf4j
public class DemoController {
    //////////////////////////////////////// Logback测试//////////////////////////////////////////////////
    @RequestMapping("/logback")
    public void logback() {
        log.error("error");
        log.info("info");
        log.debug("debug");
        log.warn("warn");
    }
    ////////////////////////////////////////统一返回值测试//////////////////////////////////////////////////
    @RequestMapping("/res1")
    public Result res1() {
        return ResultUtil.success(ResultCode.SUCCESS);
    }

    @RequestMapping("/res2")
    public Result res2() {
        return ResultUtil.success(200, "haha");
    }

    @RequestMapping("/res3")
    public Result res3() {
        return ResultUtil.fail(11, "xixi", "{\"name\":\"zhangsan\",age:18,\"birth\":\"1999-09-21\"}");
    }

    //////////////////////////////////////// 异常处理测试//////////////////////////////////////////////////
    @RequestMapping("/ex")
    public String ex(int num, String name) throws Exception {
        System.out.println(num + "\t" + name);
        if (num == 4) {
            throw new CustomException(ResultCode.FAIL);
        }
        if (name == null) {
            throw new Exception("字段空值");
        }
        return "index";
    }
    ////////////////////////////////////////MyBatisPlus分页测试//////////////////////////////////////////////////
    @Resource
    private DeptService deptService;

    @RequestMapping("/page")
    public IPage<Dept> page(@RequestParam(value = "pageNum", defaultValue = "2") Integer pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        //需要在Config配置类中配置分页插件
        IPage<Dept> page = new Page<>(pageNum, pageSize);
        IPage<Dept> res = deptService.page(page);
        return res;
    }

    ////////////////////////////////////////Hibernate Validator//////////////////////////////////////////////////
    @RequestMapping("/add1")
    public void add1(@Valid Dept dept) {
        System.out.println(dept);
    }

    @RequestMapping("/add2")
    public void add2(@Valid Dept dept, BindingResult result) {
        StringBuffer sb = new StringBuffer();
        if (result.hasErrors()) {
            for (FieldError fieldError : result.getFieldErrors()) {
                String message = fieldError.getDefaultMessage();
                sb.append(message+"\t");
            }
            log.error(sb.toString());
        } else {
            System.out.println(dept);
        }
    }
}
