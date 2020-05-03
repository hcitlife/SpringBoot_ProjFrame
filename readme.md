# 集成模块

## 工具类说明
### Base64Util：加密解密工具类
### JsonUtil：jackson工具类，参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/105893158">Jackson工具类</a>
### RedisUtil：Redis操作工具类
### RandomUtil:生成随机数、随机字符串、随机日期、随机电话号码、随机汉字姓名
### DateTimeUtils：日期时间工具类
### MD5Util：String进行MD5加密
### DESUtil：DES加解密工具类
### IOUtil：字符串和InputStream转换

## PageBean：对分页查询数据库返回的数据进行封装

## 需要Redis序列化的实体类的toString()方法要求
需要和Redis打交道的实体类的toString()方法要求输出格式为JSON字符串，  
具体操作参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/102981295">Intellij 自定义toString方法输出Json格式字符串</a>

## 集成commons-codec，加解密用
相关文件：Base64Util

## 集成druid
- 参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/105411113">SpringBoot整合Druid</a>
- Druid测试网址：http://localhost/pf/druid/index.html 用户名：druid  密码：1234

## 集成Logback
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/104152898">SpringBoot整合Logback</a>

注：测试阶段可以将logback-spring.xml中root标签内的内容注释起来

## 整合Jackson
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/102587931">Json 数据处理技术 之 Jackson</a>

## 返回统一格式的数据
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/103160934">服务器端返回统一格式的数据</a>

## 统一异常处理和全局异常捕获：@ControllerAdvice + @ExceptionHandler
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/103081763">SpringBoot统一异常处理</a>

## 整合Redis
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/105863342">SpringBoot整合Redis</a>

实现功能：Redis增删改查操作、缓存操作

注：com.hc.config.redisConfig中最上方定义的几个常量需要根据项目进行调整

## 整合MyBatisPlus及分页插件
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/105656440">SpringBoot整合MyBatisPlus</a>
### 集成分页功能


## 整合Hibernate Validator
参考博客：<a href="https://blog.csdn.net/lianghecai52171314/article/details/105851223">SpringBoot整合hibernate-validator进行参数校验</a>


## 整合Swagger2


## 整合Quartz


## 整合Solar


## 前后端分离+JWT验证
参考博客：<a href=""></a>


