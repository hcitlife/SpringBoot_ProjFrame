package com.hc.comm.bean;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
    /**
     * 每页显示的条数
     */
    private long size = 10;
    /**
     * 当前的页码
     */
    private long current;
    /**
     * 一共有多少条记录
     */
    private long total;
    /**
     * 一共有多少页
     */
    private long pages;
    /**
     * 每一页所显示的数据
     */
    private List<T> records;

    /**
     * 分页后页面，JQuery拼出来部分请求的url路径
     */
    private List<String> urls;

    /**
     * 将MyBatisPlus返回的IPage数据封装为自定义的PageBean
     * @param page
     * @param <T>
     * @return
     */
    public static <T> PageBean<T> init(IPage<T> page) {
        PageBean<T> pageBean = new PageBean<>();
        pageBean.setCurrent(page.getCurrent());
        pageBean.setPages(page.getPages());
        pageBean.setSize(page.getSize());
        pageBean.setTotal(page.getTotal());
        pageBean.setRecords(page.getRecords());
        return pageBean;
    }
}