package com.hc.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: 梁云亮
 * @Date 2020/4/30 23:56
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "db_test.tb_dept")
public class Dept implements Serializable {
    private static final long serialVersionUID = -1092294594385392402L;
    /**
     * 部门编号
     */
    @TableId(value = "deptno", type = IdType.AUTO)
    private Integer deptno;

    /**
     * 部门名称
     */
    @TableField(value = "dname")
    @NotBlank(message = "部门名称不能为空")
    @Length(min = 4, max = 9, message = "部门名称的长度需要在4~9之间")
    private String dname;

    /**
     * 部门地址
     */
    @TableField(value = "loc")
    @NotNull(message = "部门编号不能为空")
    @Min(value = 0, message = "用户等级最小值为0")
    @Max(value = 6, message = "用户等级最大值为6")
    @Digits(integer = 1, fraction = 0, message = "用户等级必须为整数")
    private String loc;

    @Override
    public String toString() {
        return "{"
                + "\"deptno\":"
                + deptno
                + ",\"dname\":\""
                + dname + '\"'
                + ",\"loc\":\""
                + loc + '\"'
                + "}";
    }
}