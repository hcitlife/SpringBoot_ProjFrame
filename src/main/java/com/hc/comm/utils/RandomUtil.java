package com.hc.comm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Author: 梁云亮
 * @Date 2020/5/1 15:31
 * @Description:
 */
public class RandomUtil {

    /**
     * Date转默认时区的LocalDate
     */
    private static LocalDate date2LocalDate(Date date) {
        if (null == date) {
            return null;
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 生成指定范围内的随机日期
     *
     * @param min
     * @param max
     * @return
     */
    public static LocalDate randomLocalDate(String min, String max) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateMin;
        Date dateMax;
        try {
            dateMin = sdf.parse(min);
            dateMax = sdf.parse(max);
            long timeMin = dateMin.getTime();// 获取日期所对应的数字
            long timeMax = dateMax.getTime();

            double random = Math.random(); // [0,1)
            long digit = (long) (random * (timeMax - timeMin + 1) + timeMin);
            Date date = new Date(digit);
            LocalDate res = date2LocalDate(date);
            return res;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
//            LocalDate localDate = randomLocalDate("1999-9-21", "2019-9-21");
//            System.out.println(localDate);
            System.out.println(genString(8));
        }
    }

    /**
     * 产生[min,max]之间的随机数
     * @param min
     * @param max
     * @return
     */
    public static int genDigit(int min, int max) {
        // Math.random()产生随机[m,n]整数的公式：Math.random()*(n-m+1)+m
        Random random = new Random();
        int digit = random.nextInt(max-min+1) + min;
        return digit;
    }

    /**
     * 产生指定长度的字符串，要求第一个是字母
     *
     * @param length
     * @return
     */
    public static String genString(int length) {    //产生随机字符串
        char[] source = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int index = random.nextInt(52) + 10;
        sb.append(source[index]);

        for (int i = 0; i < length - 1; i++) {
            index = random.nextInt(62);
            sb.append(source[index]);
        }

        return sb.toString();
    }

    /**
     * 生成随机的名字
     *
     * @return
     */
    public static String genName() {
        Random random = new Random();
        String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
                "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
                "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
                "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
                "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季"};
        String girl = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽";
        String boy = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";

        int index = random.nextInt(Surname.length - 1);
        String name = Surname[index]; //获得一个随机的姓氏

        int i = random.nextInt(3);//可以根据这个数设置产生的男女比例
        if (i == 2) {
            int j = random.nextInt(girl.length() - 2);
            if (j % 2 == 0) {
                name = name + girl.substring(j, j + 2);
            } else {
                name = name + girl.substring(j, j + 1);
            }
        } else {
            int j = random.nextInt(girl.length() - 2);
            if (j % 2 == 0) {
                name = name + boy.substring(j, j + 2);
            } else {
                name = name + boy.substring(j, j + 1);
            }

        }
        return name;
    }

    /**
     * 产生随机的电话号码
     *
     * @return
     */
    public static String genPhoneNum() {
        long[] prefix = {13400000000L, 13500000000L, 13600000000L, 13700000000L,
                13800000000L, 13900000000L, 15000000000L, 15100000000L, 15200000000L,
                15700000000L, 15800000000L, 15900000000L, 13000000000L, 13100000000L,
                13200000000L, 15500000000L, 15600000000L, 13300000000L, 15300000000L};

        long suffix = (long) (Math.random() * 99999999);
        Random random = new Random();
        int index = random.nextInt(prefix.length);
        return prefix[index] + suffix + "";
    }


}
